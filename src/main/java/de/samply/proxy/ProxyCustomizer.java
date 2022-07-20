package de.samply.proxy;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProxyCustomizer implements RestTemplateCustomizer {

  private String httpHost;
  private String httpPort;
  private String httpsHost;
  private String httpsPort;
  private String user;
  private String password;


  @Override
  public void customize(RestTemplate restTemplate) {

    HttpClient httpClient = HttpClientBuilder
        .create()
        .setRoutePlanner(createRoutePlanner())
        .setDefaultCredentialsProvider(createCredentialsProvider())
        .build();
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
        httpClient);

    restTemplate.setRequestFactory(requestFactory);

  }

  private HttpRoutePlanner createRoutePlanner() {
    return new ProxyRoutePlanner(createHttpProxy(), createHttpsProxy());
  }

  private HttpHost createHttpProxy() {
    return new HttpHost(httpHost, Integer.valueOf(httpPort));
  }

  private HttpHost createHttpsProxy() {
    return new HttpHost(httpsHost, Integer.valueOf(httpsPort));
  }

  private CredentialsProvider createCredentialsProvider() {

    BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();

    AuthScope httpAuthScope = new AuthScope(httpHost, Integer.valueOf(httpPort));
    AuthScope httpsAuthScope = new AuthScope(httpsHost, Integer.valueOf(httpsPort));

    UsernamePasswordCredentials usernamePasswordCredentials = new UsernamePasswordCredentials(user,
        password);

    credentialsProvider.setCredentials(httpAuthScope, usernamePasswordCredentials);
    credentialsProvider.setCredentials(httpsAuthScope, usernamePasswordCredentials);

    return credentialsProvider;

  }

  // TODO: follow https://github.com/Orange-OpenSource/spring-boot-autoconfigure-proxy
  @Autowired
  public void setHttpHost(@Value("${http.proxyHost:#{null}}") String httpHost) {
    this.httpHost = httpHost;
  }

  public void setHttpPort(String httpPort) {
    this.httpPort = httpPort;
  }

  public void setHttpsHost(String httpsHost) {
    this.httpsHost = httpsHost;
  }

  public void setHttpsPort(String httpsPort) {
    this.httpsPort = httpsPort;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
