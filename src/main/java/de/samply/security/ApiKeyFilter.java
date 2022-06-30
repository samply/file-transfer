package de.samply.security;

import de.samply.filetransfer.FileTransferConst;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * Filter for api key for Spring Boot Security.
 */
public class ApiKeyFilter extends AbstractPreAuthenticatedProcessingFilter {

  /**
   * TODO
   * @param request
   * @return
   */
  @Override
  protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
    return request.getHeader(FileTransferConst.API_KEY_HEADER);
  }

  /**
   * TODO
   * @param request
   * @return
   */
  @Override
  protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
    return null;
  }

}
