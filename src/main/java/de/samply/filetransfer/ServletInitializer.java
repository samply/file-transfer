package de.samply.filetransfer;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Servlet initializer for Spring Boot
 */
public class ServletInitializer extends SpringBootServletInitializer {

  /*
   *  ServletInitializer : this class called ServletInitializer extends the SpringBootServletInitializer and overrides the configure() method.
   */

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(FileTransferApplication.class);
  }

}
