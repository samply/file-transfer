package de.samply.filetransferclient;

public class FileTransferException extends Exception {

  public FileTransferException(String message) {
    super(message);
  }
  public FileTransferException(Throwable cause) {
    super(cause);
  }

}
