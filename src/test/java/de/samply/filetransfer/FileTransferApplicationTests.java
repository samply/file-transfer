package de.samply.filetransfer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


class FileTransferApplicationTests {

  private static final String BK_1_PORT = "8181";
  private static final String BK_1_API_KEY = "abc123";
  private static final String BK_2_PORT = "8282";
  private static final String BK_2_API_KEY = "def456";
  private static final String TEMP_FILENAME = "testFile.txt";
  private static final String TEMP_DIRECTORY_PREFIX = "tempDir";
  private static final String DEFAULT_DIRECTORY = ".";


  // Time to sleep should be larger than the period of the cron expression in bk1.
  private static final int TIME_TO_SLEEP_IN_SECONDS = 10;
  private static final String BK_1_CRON_EXPRESION = "*/10 * * * * *";
  private static final String BK_2_CRON_EXPRESION = "-";

  private Path tempFile;
  private Path sourceDirectory;
  private Path bk1Directory;
  private Path bk2Directory;
  private Path transferredFile;
  private ConfigurableApplicationContext bk1Context;
  private ConfigurableApplicationContext bk2Context;


  private SpringApplication createClientBridgehead(Path transferFileDirectory) {

    return new SpringApplicationBuilder(FileTransferApplication.class)
        .properties(FileTransferConst.TRANSFER_FILES_DIRECTORY + "=" + transferFileDirectory,
            FileTransferConst.TRANSFER_FILES_CRON_EXPR + "=" + BK_1_CRON_EXPRESION,
            FileTransferConst.CLIENT_API_KEY + "=" + BK_1_API_KEY,
            FileTransferConst.APPLICATION_PORT + "=" + BK_1_PORT,
            FileTransferConst.TARGET_BRIDGEHEAD_URL + "=" + getBridgeheadUrl(BK_2_PORT)
                + FileTransferConst.TRANSFER_URL,
            FileTransferConst.TARGET_BRIDGEHEAD_APIKEY + "=" + BK_2_API_KEY).build();

  }

  private SpringApplication createServerBridghead(Path transferFileDirectory) {

    return new SpringApplicationBuilder(FileTransferApplication.class)
        .properties(FileTransferConst.TRANSFER_FILES_DIRECTORY + "=" + transferFileDirectory,
            FileTransferConst.TRANSFER_FILES_CRON_EXPR + "=" + BK_2_CRON_EXPRESION,
            FileTransferConst.CLIENT_API_KEY + "=" + BK_2_API_KEY,
            FileTransferConst.APPLICATION_PORT + "=" + BK_2_PORT).build();

  }

  private String getBridgeheadUrl(String port) {
    return "http://localhost:" + port;
  }

  private Path createTempDirectory() throws IOException {
    return Files.createTempDirectory(Paths.get(DEFAULT_DIRECTORY), TEMP_DIRECTORY_PREFIX);
  }

  private void deletePath(Path path) throws IOException {
    if (path != null && Files.exists(path)) {
      Files.delete(path);
    }
  }

  private Path createTempFile(Path directory) throws IOException {

    String content = "Hello World!";
    Path file = directory.resolve(TEMP_FILENAME);
    Files.write(file, content.getBytes());

    return file;

  }

  @BeforeEach
  void setUp() throws IOException {

    sourceDirectory = createTempDirectory();
    tempFile = createTempFile(sourceDirectory);

    bk1Directory = createTempDirectory();
    bk2Directory = createTempDirectory();

    bk1Context = createClientBridgehead(bk1Directory).run();
    bk2Context = createServerBridghead(bk2Directory).run();

  }

  @AfterEach
  void tearDown() throws IOException {

    bk1Context.close();
    bk2Context.close();

    deletePath(tempFile);
    deletePath(sourceDirectory);
    deletePath(bk1Directory);
    deletePath(transferredFile);
    deletePath(bk2Directory);

  }

  @Test
  void testTransferFile() throws FileTransferException, InterruptedException {

    String bk1Url = getBridgeheadUrl(BK_1_PORT) + FileTransferConst.TRANSFER_URL;
    postFileToBridgehead(tempFile, bk1Url, BK_1_API_KEY);
    Thread.sleep(1000L * TIME_TO_SLEEP_IN_SECONDS);
    transferredFile = bk2Directory.resolve(TEMP_FILENAME);

    assertTrue(Files.exists(transferredFile));

  }

  @Test
  void testInfo() throws XmlPullParserException, IOException {

    RestTemplate restTemplate = new RestTemplate();
    String infoBridgeheadUrl = getBridgeheadUrl(BK_1_PORT) + FileTransferConst.INFO_URL;
    ResponseEntity<String> response = restTemplate.getForEntity(infoBridgeheadUrl, String.class);
    String projectVersion = getProjectVersion();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(projectVersion, response.getBody());

  }

  @Test
  void testTransferFileWithWrongApiKey() {

    String bk1Url = getBridgeheadUrl(BK_1_PORT) + FileTransferConst.TRANSFER_URL;
    try {
      postFileToBridgehead(tempFile, bk1Url, "false apikey");
      fail();
    } catch (FileTransferException e) {
      assertTrue(e.getMessage().contains(String.valueOf(HttpStatus.FORBIDDEN.value())));
    }

  }

  @Test
  void testTransferFilesWithNoApiKey() {

    String bk1Url = getBridgeheadUrl(BK_1_PORT) + FileTransferConst.TRANSFER_URL;
    try {
      postFileToBridgehead(tempFile, bk1Url, null);
      fail();
    } catch (FileTransferException e) {
      assertTrue(e.getMessage().contains(String.valueOf(HttpStatus.FORBIDDEN.value())));
    }

  }

  private String getProjectVersion()
      throws IOException, XmlPullParserException {

    MavenXpp3Reader mavenXpp3Reader = new MavenXpp3Reader();
    Model model = mavenXpp3Reader.read(new FileReader("pom.xml"));

    return fetchVersion(model);

  }

  private String fetchVersion(Model model) {
    return model.getGroupId() + ':' + model.getArtifactId() + ':' + model.getVersion();
  }

  private void postFileToBridgehead(Path file, String bridgheadUrl, String apiKey)
      throws FileTransferException {

    FileTransfer fileTransfer = new FileTransfer();
    fileTransfer.setTargetBridgeheadUrl(bridgheadUrl);
    fileTransfer.setTargetBridgeheadApiKey(apiKey);

    fileTransfer.transfer(file);

  }

}
