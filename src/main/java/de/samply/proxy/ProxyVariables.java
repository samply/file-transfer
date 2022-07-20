package de.samply.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProxyVariables {

  private String javaHttpHost;
  private String javaHttpPort;
  private String javaHttpNohosts;
  private String javaHttpUser;
  private String javaHttpPassword;
  private String javaHttpsHost;
  private String javaHttpsPort;
  private String javaHttpsNohosts;
  private String javaHttpsUser;
  private String javaHttpsPassword;

  private String sysHttpProxy;
  private String sysHttpsProxy;
  private String sysHttpProxyCap;
  private String sysHttpsProxyCap;

  private Boolean bypassProxyInLocalNetwork;


  @Autowired
  public void setJavaHttpHost(
      @Value("${" + ProxyConstants.JAVA_HTTP_HOST + ":#{null}}") String javaHttpHost) {
    this.javaHttpHost = javaHttpHost;
  }

  @Autowired
  public void setJavaHttpPort(
      @Value("${" + ProxyConstants.JAVA_HTTP_PORT + ":#{null}}") String javaHttpPort) {
    this.javaHttpPort = javaHttpPort;
  }

  @Autowired
  public void setJavaHttpNohosts(
      @Value("${" + ProxyConstants.JAVA_HTTP_NO_HOSTS + ":#{null}}") String javaHttpNohosts) {
    this.javaHttpNohosts = javaHttpNohosts;
  }

  @Autowired
  public void setJavaHttpUser(
      @Value("${" + ProxyConstants.JAVA_HTTP_USER + ":#{null}}") String javaHttpUser) {
    this.javaHttpUser = javaHttpUser;
  }

  @Autowired
  public void setJavaHttpPassword(
      @Value("${" + ProxyConstants.JAVA_HTTP_PASSWORD + ":#{null}}") String javaHttpPassword) {
    this.javaHttpPassword = javaHttpPassword;
  }

  @Autowired
  public void setJavaHttpsHost(
      @Value("${" + ProxyConstants.JAVA_HTTPS_HOST + ":#{null}}") String javaHttpsHost) {
    this.javaHttpsHost = javaHttpsHost;
  }

  @Autowired
  public void setJavaHttpsPort(
      @Value("${" + ProxyConstants.JAVA_HTTPS_PORT + ":#{null}}") String javaHttpsPort) {
    this.javaHttpsPort = javaHttpsPort;
  }

  @Autowired
  public void setJavaHttpsNohosts(
      @Value("${" + ProxyConstants.JAVA_HTTPS_NO_HOSTS + ":#{null}}") String javaHttpsNohosts) {
    this.javaHttpsNohosts = javaHttpsNohosts;
  }

  @Autowired
  public void setJavaHttpsUser(
      @Value("${" + ProxyConstants.JAVA_HTTPS_USER + ":#{null}}") String javaHttpsUser) {
    this.javaHttpsUser = javaHttpsUser;
  }

  @Autowired
  public void setJavaHttpsPassword(
      @Value("${" + ProxyConstants.JAVA_HTTPS_PASSWORD + ":#{null}}") String javaHttpsPassword) {
    this.javaHttpsPassword = javaHttpsPassword;
  }

  @Autowired
  public void setSysHttpProxy(
      @Value("${" + ProxyConstants.SYS_HTTP_PROXY + ":#{null}}") String sysHttpProxy) {
    this.sysHttpProxy = sysHttpProxy;
  }

  @Autowired
  public void setSysHttpsProxy(
      @Value("${" + ProxyConstants.SYS_HTTPS_PROXY + ":#{null}}") String sysHttpsProxy) {
    this.sysHttpsProxy = sysHttpsProxy;
  }

  @Autowired
  public void setSysHttpProxyCap(
      @Value("${" + ProxyConstants.SYS_HTTP_PROXY_CAP + ":#{null}}") String sysHttpProxyCap) {
    this.sysHttpProxyCap = sysHttpProxyCap;
  }

  @Autowired
  public void setSysHttpsProxyCap(
      @Value("${" + ProxyConstants.SYS_HTTPS_PROXY_CAP + ":#{null}}") String sysHttpsProxyCap) {
    this.sysHttpsProxyCap = sysHttpsProxyCap;
  }

  @Autowired
  public void setBypassProxyInLocalNetwork(@Value(
      "${" + ProxyConstants.BYPASS_PROXY_IN_LOCAL_NETWORK
          + ":#{null}}") Boolean bypassProxyInLocalNetwork) {
    this.bypassProxyInLocalNetwork = bypassProxyInLocalNetwork;
  }

  public String getHttpHost() {
    String httpHost = javaHttpHost;
    if (httpHost == null || httpHost.length() == 0) {
      httpHost =
          (sysHttpProxy != null && sysHttpProxy.length() > 0) ? ProxyUtils.extractHost(sysHttpProxy)
              : ProxyUtils.extractHost(sysHttpProxyCap);
    }
    return httpHost;
  }

  public String getHttpPort() {
    String httpPort = javaHttpPort;
    if (httpPort == null || httpPort.length() == 0) {
      httpPort =
          (sysHttpProxy != null && sysHttpProxy.length() > 0) ? ProxyUtils.extractPort(sysHttpProxy)
              : ProxyUtils.extractPort(sysHttpProxyCap);
    }
    return httpPort;
  }

  public String getHttpNohosts() {
    return javaHttpNohosts;
  }

  public String getHttpUser() {
    String httpUser = javaHttpUser;
    if (httpUser == null || httpUser.length() == 0) {
      httpUser =
          (sysHttpProxy != null && sysHttpProxy.length() > 0) ? ProxyUtils.extractUser(sysHttpProxy)
              : ProxyUtils.extractUser(sysHttpProxyCap);
    }
    return httpUser;
  }

  public String getHttpPassword() {
    String httpPassword = javaHttpPassword;
    if (httpPassword == null || httpPassword.length() == 0) {
      httpPassword =
          (sysHttpProxy != null && sysHttpProxy.length() > 0) ? ProxyUtils.extractPassword(sysHttpProxy)
              : ProxyUtils.extractPassword(sysHttpProxyCap);
    }
    return httpPassword;
  }

  public String getHttpsHost() {
    String httpsHost = javaHttpsHost;
    if (httpsHost == null || httpsHost.length() == 0) {
      httpsHost =
          (sysHttpsProxy != null && sysHttpsProxy.length() > 0) ? ProxyUtils.extractHost(sysHttpsProxy)
              : ProxyUtils.extractHost(sysHttpsProxyCap);
    }
    return httpsHost;
  }

  public String getHttpsPort() {
    String httpsPort = javaHttpsPort;
    if (httpsPort == null || httpsPort.length() == 0) {
      httpsPort =
          (sysHttpsProxy != null && sysHttpsProxy.length() > 0) ? ProxyUtils.extractPort(sysHttpsProxy)
              : ProxyUtils.extractPort(sysHttpsProxyCap);
    }
    return httpsPort;
  }

  public String getHttpsNohosts() {
    return javaHttpsNohosts;
  }

  public String getHttpsUser() {
    String httpsUser = javaHttpsUser;
    if (httpsUser == null || httpsUser.length() == 0) {
      httpsUser =
          (sysHttpsProxy != null && sysHttpsProxy.length() > 0) ? ProxyUtils.extractUser(sysHttpsProxy)
              : ProxyUtils.extractUser(sysHttpsProxyCap);
    }
    return httpsUser;
  }

  public String getHttpsPassword() {
    String httpsPassword = javaHttpsPassword;
    if (httpsPassword == null || httpsPassword.length() == 0) {
      httpsPassword =
          (sysHttpsProxy != null && sysHttpsProxy.length() > 0) ? ProxyUtils.extractPassword(sysHttpsProxy)
              : ProxyUtils.extractPassword(sysHttpsProxyCap);
    }
    return httpsPassword;

  }

  public Boolean getBypassProxyInLocalNetwork() {
    return bypassProxyInLocalNetwork;
  }


}
