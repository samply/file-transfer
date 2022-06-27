package de.samply.filetransferclient;

public class FileTransferException extends Exception {

  public FileTransferException(String message) {
    super(message);
  }

  public FileTransferException(String message, Throwable cause) {
    super(message, cause);
  }

  public FileTransferException(Throwable cause) {
    super(cause);
  }

  public FileTransferException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
