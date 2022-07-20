package de.samply.proxy;

import java.net.MalformedURLException;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.protocol.HttpContext;

public class ProxyRoutePlanner extends DefaultProxyRoutePlanner {

  private HttpHost httpsProxy;

  public ProxyRoutePlanner(HttpHost httpProxy, HttpHost httpsProxy) {
    super(httpProxy);
    this.httpsProxy = httpsProxy;
  }

  @Override
  protected HttpHost determineProxy(HttpHost target, HttpRequest request, HttpContext context)
      throws HttpException {
    return isHttps(request) ? this.httpsProxy : super.determineProxy(target, request, context);
  }

  private boolean isHttps(HttpRequest httpRequest) {
    try {
      return isHttps_WithoutManagementException(httpRequest);
    } catch (MalformedURLException e) {
      return false;
    }
  }

  private boolean isHttps_WithoutManagementException(HttpRequest httpRequest)
      throws MalformedURLException {

    boolean isHttps = false;

    if (httpRequest instanceof HttpUriRequest) {
      String protocol = ((HttpUriRequest) httpRequest).getURI().toURL().getProtocol();
      isHttps = protocol.equalsIgnoreCase("https");
    }

    return isHttps;

  }

}
