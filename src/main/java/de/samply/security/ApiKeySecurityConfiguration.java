package de.samply.security;


import de.samply.filetransferclient.FileTransferConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Order(1)
public class ApiKeySecurityConfiguration {

  @Autowired
  private ApiKeyAuthenticationManager apiKeyAuthenticationManager;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

    httpSecurity
        .antMatcher(FileTransferConst.TRANSFER_URL)
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilter(createApiKeyFilter())
        .authorizeRequests()
        .anyRequest()
        .authenticated();

    return httpSecurity.build();

  }

  @Bean
  public ApiKeyFilter createApiKeyFilter() {

    ApiKeyFilter apiKeyFilter = new ApiKeyFilter();
    apiKeyFilter.setAuthenticationManager(apiKeyAuthenticationManager);
    return apiKeyFilter;

  }

}
