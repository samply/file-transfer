package de.samply.filetransfer;

/**
 * Exception for FileTransfer that encapsulates internal exceptions.
 */
public class FileTransferException extends Exception {

  public FileTransferException(Throwable cause) {
    super(cause);
  }

}
