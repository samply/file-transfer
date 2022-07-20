package de.samply.proxy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProxyCustomizer implements RestTemplateCustomizer {

  private final static String DEFAULT_PORT = "3128";
  // HTTP
  private String httpHost;
  private String httpPort;
  private String httpUser;
  private String httpPassword;
  private Set<String> nonProxyHttpHosts;

  // HTTPS
  private String httpsHost;
  private String httpsPort;
  private String httpsUser;
  private String httpsPassword;
  private Set<String> nonProxyHttpsHosts;

  private Boolean bypassProxyInLocalNetwork;

  @Autowired
  public ProxyCustomizer(ProxyVariables proxyVariables) {

    httpHost = proxyVariables.getHttpHost();
    httpPort = (httpHost != null) ? proxyVariables.getHttpPort() : DEFAULT_PORT;
    httpUser = proxyVariables.getHttpUser();
    httpPassword = proxyVariables.getHttpPassword();
    nonProxyHttpHosts = splitNonProxyHosts(proxyVariables.getHttpNohosts());

    httpsHost = proxyVariables.getHttpsHost();
    httpsPort = (httpsHost != null) ? proxyVariables.getHttpsPort() : DEFAULT_PORT;
    httpsUser = proxyVariables.getHttpsUser();
    httpsPassword = proxyVariables.getHttpsPassword();
    nonProxyHttpsHosts = splitNonProxyHosts(proxyVariables.getHttpsNohosts());

    bypassProxyInLocalNetwork = proxyVariables.getBypassProxyInLocalNetwork();

  }

  private Set<String> splitNonProxyHosts(String nonProxyHosts) {
    return (nonProxyHosts != null && nonProxyHosts.length() > 0) ?
        new HashSet<>(Arrays.asList(nonProxyHosts.split(","))) : new HashSet<>();
  }

  @Override
  public void customize(RestTemplate restTemplate) {

    if (httpHost != null || httpsHost != null) {

      HttpClient httpClient = HttpClientBuilder
          .create()
          .setRoutePlanner(createRoutePlanner())
          .setDefaultCredentialsProvider(createCredentialsProvider())
          .build();
      HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
          httpClient);

      restTemplate.setRequestFactory(requestFactory);

    }

  }

  private HttpRoutePlanner createRoutePlanner() {

    ProxyRoutePlanner proxyRoutePlanner = new ProxyRoutePlanner(createHttpProxy(),
        createHttpsProxy());

    if (nonProxyHttpHosts != null) {
      proxyRoutePlanner.setNonProxyHttpHosts(nonProxyHttpHosts);
    }
    if (nonProxyHttpsHosts != null) {
      proxyRoutePlanner.setNonProxyHttpsHosts(nonProxyHttpsHosts);
    }
    if (bypassProxyInLocalNetwork != null) {
      proxyRoutePlanner.setBypassProxyInLocalNetwork(bypassProxyInLocalNetwork);
    }

    return proxyRoutePlanner;

  }

  private HttpHost createHttpProxy() {
    return (httpHost != null) ? new HttpHost(httpHost, Integer.valueOf(httpPort)) : null;
  }

  private HttpHost createHttpsProxy() {
    return (httpsHost != null) ? new HttpHost(httpsHost, Integer.valueOf(httpsPort)) : null;
  }

  private CredentialsProvider createCredentialsProvider() {

    CredentialsProvider credProv = new BasicCredentialsProvider();

    if (httpHost != null) {
      addCredentialsToCredentialsProvider(credProv, httpHost, httpPort, httpUser, httpPassword);
    }
    if (httpsHost != null) {
      addCredentialsToCredentialsProvider(credProv, httpsHost, httpsPort, httpsUser, httpsPassword);
    }

    return credProv;

  }

  private void addCredentialsToCredentialsProvider(CredentialsProvider credentialsProvider,
      String host,
      String port, String user, String password) {

    if (user != null && password != null) {

      AuthScope httpAuthScope = new AuthScope(host, Integer.valueOf(port));
      UsernamePasswordCredentials httpCredentials = new UsernamePasswordCredentials(user, password);
      credentialsProvider.setCredentials(httpAuthScope, httpCredentials);

    }

  }

}

