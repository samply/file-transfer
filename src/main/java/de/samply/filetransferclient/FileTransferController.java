package de.samply.filetransferclient;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileTransferController {


  private Path transferFilesDirectory;

  private final String projectVersion = getProjectVersion();

  @Autowired
  public void setTransferFilesDirectory(
      @Value(FileTransferConst.TRANSFER_DIRECTORY) String transferFilesDirectory) {
    this.transferFilesDirectory = Paths.get(transferFilesDirectory);
  }

  @GetMapping(FileTransferConst.INFO_URL)
  public ResponseEntity<String> info() {
    return new ResponseEntity<>(projectVersion, HttpStatus.OK);
  }


  private String getProjectVersion(){
    try {
      return getProjectVersion_WithoutManagementException();
    } catch (IOException | XmlPullParserException e) {
      return "File Transfer";
    }
  }

  private String getProjectVersion_WithoutManagementException()
      throws IOException, XmlPullParserException {

    MavenXpp3Reader mavenXpp3Reader = new MavenXpp3Reader();
    Model model = mavenXpp3Reader.read(new FileReader("pom.xml"));

    return fetchVersion(model);

  }

  private String fetchVersion(Model model){

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(model.getGroupId());
    stringBuilder.append(':');
    stringBuilder.append(model.getArtifactId());
    stringBuilder.append(':');
    stringBuilder.append(model.getVersion());

    return stringBuilder.toString();

  }

  @PostMapping(FileTransferConst.TRANSFER_URL)
  public ResponseEntity transferFile(
      @RequestParam(FileTransferConst.TRANSFER_FILE_PARAMETER) MultipartFile multipartFile) {

    try {
      storeInTransferFileDirectory(multipartFile);
      return ResponseEntity.ok().body("File stored!");

    } catch (IOException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }

  }

  private void storeInTransferFileDirectory(MultipartFile multipartFile)
      throws IOException {
    Path localFile = transferFilesDirectory.resolve(multipartFile.getOriginalFilename());
    multipartFile.transferTo(localFile);
  }

}
