package de.samply.filetransfer;

public class FileTransferException extends Exception {

  public FileTransferException(String message) {
    super(message);
  }
  public FileTransferException(Throwable cause) {
    super(cause);
  }

}
