package de.samply.filetransfer;

/**
 * Exception for FileTransfer that encapsulates internal exceptions.
 */
public class FileTransferException extends Exception {

  public FileTransferException(String message) {
    super(message);
  }

  public FileTransferException(Throwable cause) {
    super(cause);
  }

}
