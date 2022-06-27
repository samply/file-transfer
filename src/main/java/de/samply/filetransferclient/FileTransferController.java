package de.samply.filetransferclient;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileTransferController {


  private Path transferFilesDirectory;

  @Autowired
  public void setTransferFilesDirectory(
      @Value(FileTransferConst.TRANSFER_DIRECTORY) String transferFilesDirectory) {
    this.transferFilesDirectory = Paths.get(transferFilesDirectory);
  }

  //TODO: Return project version
  @GetMapping(FileTransferConst.INFO_URL)
  public String info() {
    return "Hello World!";
  }

  @PostMapping(FileTransferConst.TRANSFER_URL)
  public ResponseEntity transferFile(
      @RequestParam(FileTransferConst.TRANSFER_FILE_PARAMETER) MultipartFile multipartFile) {

    storeInTransferFileDirectory(multipartFile);
    return ResponseEntity.ok().body("Multipart file uploaded!");

  }

  private void storeInTransferFileDirectory(MultipartFile multipartFile) {
    try {
      storeInTransferFileDirectory_withoutManagementException(multipartFile);
    } catch (IOException e) {
      e.printStackTrace();
      //TODO: do something
    }
  }

  private void storeInTransferFileDirectory_withoutManagementException(MultipartFile multipartFile)
      throws IOException {
    Path localFile = transferFilesDirectory.resolve(multipartFile.getOriginalFilename());
    multipartFile.transferTo(localFile);
  }

}
