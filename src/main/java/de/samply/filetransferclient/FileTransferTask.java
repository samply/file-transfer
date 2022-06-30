package de.samply.filetransferclient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileTransferTask {

  private final Logger logger = LoggerFactory.getLogger(FileTransferTask.class);

  @Autowired
  private FileTransfer fileTransfer;

  private Path transferFilesDirectory;

  @Autowired
  public void setTransferFilesDirectory(
      @Value(FileTransferConst.TRANSFER_DIRECTORY) String transferFilesDirectory) {
    this.transferFilesDirectory = Paths.get(transferFilesDirectory);
  }

  public void transfer() {

    logger.info("Sending files to transfer...");
    sendAndDeleteAllPaths();
    logger.info("Sending files ended");

  }

  private void sendAndDeleteAllPaths() {

    try {
      Files
          .walk(transferFilesDirectory)
          .filter(Files::isRegularFile)
          .forEach((path) -> sendAndDeletePath(path));

    } catch (IOException e) {
      logger.error(
          "Error reading files to transfer in directory " + transferFilesDirectory.toAbsolutePath(),
          e);
    }

  }

  private void sendAndDeletePath(Path path) {
    try {
      sendAndDeletePath_WithoutManagementException(path);
    } catch (FileTransferException e) {
      logger.error("Error transferring file " + path.getFileName(), e);
    } catch (IOException e) {
      logger.error("Error deleting file " + path.getFileName(), e);
    }

  }

  private void sendAndDeletePath_WithoutManagementException(Path path)
      throws FileTransferException, IOException {

    fileTransfer.transfer(path);
    Files.delete(path);

  }

}
