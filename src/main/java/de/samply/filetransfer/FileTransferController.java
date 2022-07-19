package de.samply.filetransfer;

import de.samply.utils.ProjectVersion;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST API of application for file transfer FileTransferController: Controller connects file
 * directory for transfer.
 */
@RestController
public class FileTransferController {

  private final String projectVersion = ProjectVersion.getProjectVersion();

  private Path transferFilesDirectory;


  @Autowired
  public void setTransferFilesDirectory(
      @Value(FileTransferConst.TRANSFER_FILES_DIRECTORY_SV) String transferFilesDirectory) {
    this.transferFilesDirectory = Paths.get(transferFilesDirectory);
  }

  /**
   * Get Mapping for receiving file in second bridgehead.
   *
   * @return Version of File Transfer.
   */
  @GetMapping(FileTransferConst.INFO_URL)
  public ResponseEntity<String> info() {
    return new ResponseEntity<>(projectVersion, HttpStatus.OK);
  }


  /**
   * Post file to bridgehead. The file will temporary stored in transfer files directory.
   *
   * @param multipartFile File to be transferred.
   * @return ResponseEntity HTTP Status.
   */
  @PostMapping(FileTransferConst.TRANSFER_URL)
  public ResponseEntity transferFile(
      @RequestParam(FileTransferConst.TRANSFER_FILE_PARAMETER) MultipartFile multipartFile) {

    try {
      storeInTransferFileDirectory(multipartFile);
      return ResponseEntity.ok().body("File stored!");
    } catch (IOException | FileTransferException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }

  }

  private void storeInTransferFileDirectory(MultipartFile multipartFile)
      throws IOException, FileTransferException {

    if (multipartFile.getOriginalFilename() == null) {
      throw new FileTransferException("Missing filename");
    }

    Path localFile = transferFilesDirectory.resolve(multipartFile.getOriginalFilename());
    multipartFile.transferTo(localFile);

  }

}
