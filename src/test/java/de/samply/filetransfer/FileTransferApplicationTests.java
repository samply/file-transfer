package de.samply.filetransfer;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@Disabled
@SpringBootTest
@ComponentScan(basePackages = {"de.samply"})
class FileTransferApplicationTests {

  @Test
  void contextLoads() {
  }

}
