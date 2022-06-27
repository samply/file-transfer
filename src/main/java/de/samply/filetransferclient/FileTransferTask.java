package de.samply.filetransferclient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileTransferTask {

  @Autowired
  private FileTransfer fileTransfer;

  private Path transferFilesDirectory;

  @Autowired
  public void setTransferFilesDirectory(
      @Value(FileTransferConst.TRANSFER_DIRECTORY) String transferFilesDirectory) {
    this.transferFilesDirectory = Paths.get(transferFilesDirectory);
  }

  // TODO
  public void transfer() {
    System.out.println("Scheduled Task!");
    sendAndDeleteAllPaths();
  }

  private void sendAndDeleteAllPaths() {

    try {
      Files
          .walk(transferFilesDirectory)
          .filter(Files::isRegularFile)
          .forEach((path) -> sendAndDeletePath(path));

    } catch (IOException e) {
      e.printStackTrace();
      //TODO: do something
    }

  }

  private void sendAndDeletePath(Path path) {
    try {
      sendAndDeletePath_WithoutManagementException(path);
    } catch (FileTransferException e) {
      e.printStackTrace();
      //TODO
    } catch (IOException e) {
      e.printStackTrace();
      //TODO
    }

  }

  private void sendAndDeletePath_WithoutManagementException(Path path)
      throws FileTransferException, IOException {

    fileTransfer.transfer(path);
    // TODO: Check if file was successfully transferred. If yes:
    Files.delete(path);

  }

}
