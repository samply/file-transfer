package de.samply.filetransferclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

  @Autowired
  private FileTransferTask fileTransferTask;

  @Scheduled(cron = FileTransferConst.TRANSFER_FILES_CRON_EXPR)
  public void transferFiles() {
    fileTransferTask.transfer();
  }

}
