package de.samply.filetransferclient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class FileTransfer {

  @Value(FileTransferConst.TARGET_BRIDGEHEAD_URL)
  private String targetBridgeheadUrl;

  @Value(FileTransferConst.TARGET_BRIDGEHEAD_APIKEY)
  private String targetBridgeheadApiKey;

  public void transfer(Path path) throws FileTransferException {

    RestTemplate restTemplate = new RestTemplate();
    HttpEntity httpEntity = new HttpEntity(createBody(path), createHeaders());

    //TODO: change string.class
    ResponseEntity<String> response = restTemplate.postForEntity(targetBridgeheadUrl, httpEntity,
        String.class);

  }

  @Bean
  private HttpHeaders createHeaders() {

    HttpHeaders httpHeaders = new HttpHeaders();

    httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
    httpHeaders.set(FileTransferConst.API_KEY_HEADER, targetBridgeheadApiKey);

    return httpHeaders;

  }

  private MultiValueMap<String, Object> createBody(Path path) throws FileTransferException {

    MultiValueMap<String, Object> fileMultiValueMap = new LinkedMultiValueMap<>();
    ContentDisposition contentDisposition = createContentDisposition(path);
    fileMultiValueMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());

    HttpEntity fileHttpEntity = new HttpEntity(convertToBytes(path), fileMultiValueMap);

    MultiValueMap body = new LinkedMultiValueMap();
    body.add(FileTransferConst.TRANSFER_FILE_PARAMETER, fileHttpEntity);

    return body;

  }

  private ContentDisposition createContentDisposition(Path path) {
    return ContentDisposition
        .builder("form-data")
        .name("file")
        .filename(path.getFileName().toString())
        .build();
  }

  private byte[] convertToBytes(Path path) throws FileTransferException {

    try {
      return Files.readAllBytes(path);
    } catch (IOException e) {
      throw new FileTransferException(e);
    }

  }

}
