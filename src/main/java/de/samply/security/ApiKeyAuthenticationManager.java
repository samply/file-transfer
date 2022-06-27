package de.samply.security;

import de.samply.filetransferclient.FileTransferConst;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class ApiKeyAuthenticationManager implements AuthenticationManager {

  @Value(FileTransferConst.CLIENT_API_KEY)
  private String apiKey;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    String apiKey = (String) authentication.getPrincipal();

    if (!ObjectUtils.isEmpty(this.apiKey) && (ObjectUtils.isEmpty(apiKey) || !apiKey.equals(
        this.apiKey))) {
      throw new BadCredentialsException("Incorrect API Key");
    } else {
      authentication.setAuthenticated(true);
    }

    return authentication;

  }

}
