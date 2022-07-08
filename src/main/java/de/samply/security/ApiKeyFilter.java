package de.samply.security;

import de.samply.filetransfer.FileTransferConst;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * Filter for api key for Spring Boot Security.
 */
public class ApiKeyFilter extends AbstractPreAuthenticatedProcessingFilter {

  /**
   * get HTTP API key header.
   *
   * @param request HTTP servlet request.
   * @return HTTP API key header.
   */
  @Override
  protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
    return request.getHeader(FileTransferConst.API_KEY_HEADER);
  }

  /**
   * Autentication based on API key. Credentials not needed.
   *
   * @param request HTTP servlet request.
   * @return NULL.
   */
  @Override
  protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
    return null;
  }

}
