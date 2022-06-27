package de.samply.filetransferclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"de.samply"})
@EnableScheduling
public class FileTransferClientApplication {

  public static void main(String[] args) {
    SpringApplication.run(FileTransferClientApplication.class, args);
  }

}
