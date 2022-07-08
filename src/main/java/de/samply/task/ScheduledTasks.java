package de.samply.task;

import de.samply.filetransfer.FileTransferConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduled tasks for transferring files between bridgeheads.
 */
@Component
public class ScheduledTasks {

  private FileTransferTask fileTransferTask;

  @Autowired
  public void setFileTransferTask(FileTransferTask fileTransferTask) {
    this.fileTransferTask = fileTransferTask;
  }

  @Scheduled(cron = FileTransferConst.TRANSFER_FILES_CRON_EXPR_SV)
  public void transferFiles() {
    fileTransferTask.transfer();
  }

}
