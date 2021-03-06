package de.samply.filetransfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main class for Spring Boot. FileTransferApplication: Application helps to transfer data between
 * two bridgehead with healp of REST interface
 */
@SpringBootApplication
@ComponentScan(basePackages = {"de.samply"})
@EnableScheduling
public class FileTransferApplication {

  public static void main(String[] args) {
    SpringApplication.run(FileTransferApplication.class, args);
  }

}
