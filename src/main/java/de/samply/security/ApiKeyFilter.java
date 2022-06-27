package de.samply.security;

import de.samply.filetransferclient.FileTransferConst;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class ApiKeyFilter extends AbstractPreAuthenticatedProcessingFilter {

  @Override
  protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
    return request.getHeader(FileTransferConst.API_KEY_HEADER);
  }

  @Override
  protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
    return null;
  }

}
