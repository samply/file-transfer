package de.samply.proxy;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.protocol.HttpContext;

public class ProxyRoutePlanner extends DefaultProxyRoutePlanner {

  private HttpHost httpsProxy;
  private Set<String> nonProxyHttpHosts = new HashSet<>();
  private Set<String> nonProxyHttpsHosts = new HashSet<>();
  private boolean bypassProxyInLocalNetwork = true;

  public ProxyRoutePlanner(HttpHost httpProxy, HttpHost httpsProxy) {
    super(httpProxy);
    this.httpsProxy = httpsProxy;
  }

  @Override
  protected HttpHost determineProxy(HttpHost target, HttpRequest request, HttpContext context)
      throws HttpException {

    boolean isNonProxyHost = false;

    if (isHttps(target, request)) {
      if (nonProxyHttpsHosts.size() > 0) {
        isNonProxyHost = isNonProxyHost(target, request, nonProxyHttpsHosts);
      }
    } else if (nonProxyHttpHosts.size() > 0) {
      isNonProxyHost = isNonProxyHost(target, request, nonProxyHttpHosts);
    }

    return (isNonProxyHost) ? null : isHttps(target, request) ? this.httpsProxy
        : super.determineProxy(target, request, context);

  }

  private boolean isNonProxyHost(HttpHost target, HttpRequest request, Set<String> nonProxyHosts)
      throws HttpException {
    try {
      return isNonProxyHost_WithoutManagementException(target, request, nonProxyHosts);
    } catch (URISyntaxException | UnknownHostException e) {
      throw new HttpException("Error evaluating if it is a non proxy host", e);
    }
  }

  private boolean isNonProxyHost_WithoutManagementException(HttpHost target, HttpRequest request,
      Set<String> nonProxyHosts)
      throws URISyntaxException, UnknownHostException {

    String host = getHost(target, request);

    return nonProxyHosts.contains(host) || (bypassProxyInLocalNetwork && isLocalOrLookbackAddress(
        host));

  }

  private String getHost(HttpHost target, HttpRequest request) throws URISyntaxException {

    String host = (target != null) ? target.getHostName()
        : new URI(request.getRequestLine().getUri()).getHost();

    int index = host.indexOf('/');
    if (index > 0) {
      host = host.substring(0, index);
    }

    return host;

  }

  private boolean isLocalOrLookbackAddress(String host) throws UnknownHostException {

    InetAddress inetAddressName = InetAddress.getByName(host);
    return inetAddressName.isAnyLocalAddress() || inetAddressName.isLoopbackAddress();

  }

  private boolean isHttps(HttpHost target, HttpRequest httpRequest) {
    try {
      return isHttps_WithoutManagementException(target, httpRequest);
    } catch (MalformedURLException e) {
      return false;
    }
  }

  private boolean isHttps_WithoutManagementException(HttpHost target, HttpRequest httpRequest)
      throws MalformedURLException {

    String protocol = (target != null) ? target.getSchemeName()
        : httpRequest.getRequestLine().getProtocolVersion().getProtocol();
// ((HttpUriRequest) httpRequest).getURI().toURL().getProtocol()
    return protocol.equalsIgnoreCase("https");

  }

  public void setNonProxyHttpHosts(Set<String> nonProxyHttpHosts) {
    this.nonProxyHttpHosts = nonProxyHttpHosts;
  }

  public void setNonProxyHttpsHosts(Set<String> nonProxyHttpsHosts) {
    this.nonProxyHttpsHosts = nonProxyHttpsHosts;
  }

  public void setBypassProxyInLocalNetwork(boolean bypassProxyInLocalNetwork) {
    this.bypassProxyInLocalNetwork = bypassProxyInLocalNetwork;
  }

}
