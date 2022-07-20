package de.samply.proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.samply.filetransfer.FileTransferApplication;
import de.samply.filetransfer.FileTransferConst;
import org.junit.jupiter.api.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

class ProxyVariablesTest {

  private static final String BK_PORT = "8181";
  private static final String BK_API_KEY = "abc123";


  private final static String BYPASS_PROXY_IN_LOCAL_NETWORK = "false";

  // HTTP
  private final static String JAVA_PROXY_HTTP_HOST = "http://myproxy.com";
  private final static String JAVA_PROXY_HTTP_PORT = "1234";
  private final static String JAVA_PROXY_HTTP_USER = "myuser";
  private final static String JAVA_PROXY_HTTP_PASSWORD = "mypassword";
  private final static String JAVA_HTTP_NO_HOSTS = "noproxy1,noproxy2";

  // HTTPS
  private final static String JAVA_PROXY_HTTPS_HOST = "https://myproxy.com";
  private final static String JAVA_PROXY_HTTPS_PORT = "4321";
  private final static String JAVA_PROXY_HTTPS_USER = "myuser2";
  private final static String JAVA_PROXY_HTTPS_PASSWORD = "mypassword2";
  private final static String JAVA_HTTPS_NO_HOSTS = "noproxy3,noproxy4,noproxy5";

  // SYS
  public static final String SYS_HTTP_PROXY = "http://myuser:mypassword@myproxy.com:1234";
  public static final String SYS_HTTP_PROXY_CAP = "http://myuser:mypassword@myproxy.com:1234";
  public static final String SYS_HTTPS_PROXY = "https://myuser2:mypassword2@myproxy.com:4321";
  public static final String SYS_HTTPS_PROXY_CAP = "https://myuser2:mypassword2@myproxy.com:4321";


  @Test
  void testGetProxyVariables1() {

    ConfigurableApplicationContext bkContext = new SpringApplicationBuilder(
        FileTransferApplication.class)
        .properties(
            FileTransferConst.CLIENT_API_KEY + "=" + BK_API_KEY,
            FileTransferConst.APPLICATION_PORT + "=" + BK_PORT,
            ProxyConstants.JAVA_HTTP_HOST + "=" + JAVA_PROXY_HTTP_HOST,
            ProxyConstants.JAVA_HTTP_PORT + "=" + JAVA_PROXY_HTTP_PORT,
            ProxyConstants.JAVA_HTTP_USER + "=" + JAVA_PROXY_HTTP_USER,
            ProxyConstants.JAVA_HTTP_PASSWORD + "=" + JAVA_PROXY_HTTP_PASSWORD,
            ProxyConstants.JAVA_HTTP_NO_HOSTS + "=" + JAVA_HTTP_NO_HOSTS,
            ProxyConstants.JAVA_HTTPS_HOST + "=" + JAVA_PROXY_HTTPS_HOST,
            ProxyConstants.JAVA_HTTPS_PORT + "=" + JAVA_PROXY_HTTPS_PORT,
            ProxyConstants.JAVA_HTTPS_USER + "=" + JAVA_PROXY_HTTPS_USER,
            ProxyConstants.JAVA_HTTPS_PASSWORD + "=" + JAVA_PROXY_HTTPS_PASSWORD,
            ProxyConstants.JAVA_HTTPS_NO_HOSTS + "=" + JAVA_HTTPS_NO_HOSTS,
            ProxyConstants.BYPASS_PROXY_IN_LOCAL_NETWORK + "=" + BYPASS_PROXY_IN_LOCAL_NETWORK
        )
        .build().run();

    ProxyVariables proxyVariables = bkContext.getBean(ProxyVariables.class);

    assertEquals(JAVA_PROXY_HTTP_HOST, proxyVariables.getHttpHost());
    assertEquals(JAVA_PROXY_HTTP_PORT, proxyVariables.getHttpPort());
    assertEquals(JAVA_PROXY_HTTP_USER, proxyVariables.getHttpUser());
    assertEquals(JAVA_PROXY_HTTP_PASSWORD, proxyVariables.getHttpPassword());
    assertEquals(JAVA_HTTP_NO_HOSTS, proxyVariables.getHttpNohosts());

    assertEquals(JAVA_PROXY_HTTPS_HOST, proxyVariables.getHttpsHost());
    assertEquals(JAVA_PROXY_HTTPS_PORT, proxyVariables.getHttpsPort());
    assertEquals(JAVA_PROXY_HTTPS_USER, proxyVariables.getHttpsUser());
    assertEquals(JAVA_PROXY_HTTPS_PASSWORD, proxyVariables.getHttpsPassword());
    assertEquals(JAVA_HTTPS_NO_HOSTS, proxyVariables.getHttpsNohosts());

    assertEquals(Boolean.valueOf(BYPASS_PROXY_IN_LOCAL_NETWORK),
        proxyVariables.getBypassProxyInLocalNetwork());

    bkContext.close();

  }

  @Test
  void testGetProxyVariables2() {

    ConfigurableApplicationContext bkContext = new SpringApplicationBuilder(
        FileTransferApplication.class)
        .properties(
            FileTransferConst.CLIENT_API_KEY + "=" + BK_API_KEY,
            FileTransferConst.APPLICATION_PORT + "=" + BK_PORT,
            ProxyConstants.JAVA_HTTP_NO_HOSTS + "=" + JAVA_HTTP_NO_HOSTS,
            ProxyConstants.JAVA_HTTPS_NO_HOSTS + "=" + JAVA_HTTPS_NO_HOSTS,
            ProxyConstants.SYS_HTTP_PROXY + "=" + SYS_HTTP_PROXY,
            ProxyConstants.SYS_HTTPS_PROXY + "=" + SYS_HTTPS_PROXY,
            ProxyConstants.BYPASS_PROXY_IN_LOCAL_NETWORK + "=" + BYPASS_PROXY_IN_LOCAL_NETWORK
        )
        .build().run();

    ProxyVariables proxyVariables = bkContext.getBean(ProxyVariables.class);

    assertEquals(JAVA_PROXY_HTTP_HOST, proxyVariables.getHttpHost());
    assertEquals(JAVA_PROXY_HTTP_PORT, proxyVariables.getHttpPort());
    assertEquals(JAVA_PROXY_HTTP_USER, proxyVariables.getHttpUser());
    assertEquals(JAVA_PROXY_HTTP_PASSWORD, proxyVariables.getHttpPassword());
    assertEquals(JAVA_HTTP_NO_HOSTS, proxyVariables.getHttpNohosts());

    assertEquals(JAVA_PROXY_HTTPS_HOST, proxyVariables.getHttpsHost());
    assertEquals(JAVA_PROXY_HTTPS_PORT, proxyVariables.getHttpsPort());
    assertEquals(JAVA_PROXY_HTTPS_USER, proxyVariables.getHttpsUser());
    assertEquals(JAVA_PROXY_HTTPS_PASSWORD, proxyVariables.getHttpsPassword());
    assertEquals(JAVA_HTTPS_NO_HOSTS, proxyVariables.getHttpsNohosts());

    assertEquals(Boolean.valueOf(BYPASS_PROXY_IN_LOCAL_NETWORK),
        proxyVariables.getBypassProxyInLocalNetwork());

    bkContext.close();

  }

  @Test
  void testGetProxyVariables3() {

    ConfigurableApplicationContext bkContext = new SpringApplicationBuilder(
        FileTransferApplication.class)
        .properties(
            FileTransferConst.CLIENT_API_KEY + "=" + BK_API_KEY,
            FileTransferConst.APPLICATION_PORT + "=" + BK_PORT,
            ProxyConstants.JAVA_HTTP_NO_HOSTS + "=" + JAVA_HTTP_NO_HOSTS,
            ProxyConstants.JAVA_HTTPS_NO_HOSTS + "=" + JAVA_HTTPS_NO_HOSTS,
            ProxyConstants.SYS_HTTP_PROXY_CAP + "=" + SYS_HTTP_PROXY_CAP,
            ProxyConstants.SYS_HTTPS_PROXY_CAP + "=" + SYS_HTTPS_PROXY_CAP,
            ProxyConstants.BYPASS_PROXY_IN_LOCAL_NETWORK + "=" + BYPASS_PROXY_IN_LOCAL_NETWORK
        )
        .build().run();

    ProxyVariables proxyVariables = bkContext.getBean(ProxyVariables.class);

    assertEquals(JAVA_PROXY_HTTP_HOST, proxyVariables.getHttpHost());
    assertEquals(JAVA_PROXY_HTTP_PORT, proxyVariables.getHttpPort());
    assertEquals(JAVA_PROXY_HTTP_USER, proxyVariables.getHttpUser());
    assertEquals(JAVA_PROXY_HTTP_PASSWORD, proxyVariables.getHttpPassword());
    assertEquals(JAVA_HTTP_NO_HOSTS, proxyVariables.getHttpNohosts());

    assertEquals(JAVA_PROXY_HTTPS_HOST, proxyVariables.getHttpsHost());
    assertEquals(JAVA_PROXY_HTTPS_PORT, proxyVariables.getHttpsPort());
    assertEquals(JAVA_PROXY_HTTPS_USER, proxyVariables.getHttpsUser());
    assertEquals(JAVA_PROXY_HTTPS_PASSWORD, proxyVariables.getHttpsPassword());
    assertEquals(JAVA_HTTPS_NO_HOSTS, proxyVariables.getHttpsNohosts());

    assertEquals(Boolean.valueOf(BYPASS_PROXY_IN_LOCAL_NETWORK),
        proxyVariables.getBypassProxyInLocalNetwork());

    bkContext.close();

  }

  @Test
  void testGetProxyVariables4() {

    ConfigurableApplicationContext bkContext = new SpringApplicationBuilder(
        FileTransferApplication.class)
        .properties(
            FileTransferConst.CLIENT_API_KEY + "=" + BK_API_KEY,
            FileTransferConst.APPLICATION_PORT + "=" + BK_PORT,
            ProxyConstants.JAVA_HTTP_HOST + "=" + JAVA_PROXY_HTTP_HOST,
            ProxyConstants.JAVA_HTTP_PORT + "=" + JAVA_PROXY_HTTP_PORT,
            ProxyConstants.JAVA_HTTP_USER + "=" + JAVA_PROXY_HTTP_USER,
            ProxyConstants.JAVA_HTTP_PASSWORD + "=" + JAVA_PROXY_HTTP_PASSWORD,
            ProxyConstants.JAVA_HTTP_NO_HOSTS + "=" + JAVA_HTTP_NO_HOSTS,
            ProxyConstants.JAVA_HTTPS_HOST + "=" + JAVA_PROXY_HTTPS_HOST,
            ProxyConstants.JAVA_HTTPS_PORT + "=" + JAVA_PROXY_HTTPS_PORT,
            ProxyConstants.JAVA_HTTPS_USER + "=" + JAVA_PROXY_HTTPS_USER,
            ProxyConstants.JAVA_HTTPS_PASSWORD + "=" + JAVA_PROXY_HTTPS_PASSWORD,
            ProxyConstants.JAVA_HTTPS_NO_HOSTS + "=" + JAVA_HTTPS_NO_HOSTS,
            ProxyConstants.SYS_HTTP_PROXY + "=" + SYS_HTTP_PROXY,
            ProxyConstants.SYS_HTTPS_PROXY + "=" + SYS_HTTPS_PROXY,
            ProxyConstants.SYS_HTTP_PROXY_CAP + "=" + SYS_HTTP_PROXY_CAP,
            ProxyConstants.SYS_HTTPS_PROXY_CAP + "=" + SYS_HTTPS_PROXY_CAP,
            ProxyConstants.BYPASS_PROXY_IN_LOCAL_NETWORK + "=" + BYPASS_PROXY_IN_LOCAL_NETWORK
        )
        .build().run();

    ProxyVariables proxyVariables = bkContext.getBean(ProxyVariables.class);

    assertEquals(JAVA_PROXY_HTTP_HOST, proxyVariables.getHttpHost());
    assertEquals(JAVA_PROXY_HTTP_PORT, proxyVariables.getHttpPort());
    assertEquals(JAVA_PROXY_HTTP_USER, proxyVariables.getHttpUser());
    assertEquals(JAVA_PROXY_HTTP_PASSWORD, proxyVariables.getHttpPassword());
    assertEquals(JAVA_HTTP_NO_HOSTS, proxyVariables.getHttpNohosts());

    assertEquals(JAVA_PROXY_HTTPS_HOST, proxyVariables.getHttpsHost());
    assertEquals(JAVA_PROXY_HTTPS_PORT, proxyVariables.getHttpsPort());
    assertEquals(JAVA_PROXY_HTTPS_USER, proxyVariables.getHttpsUser());
    assertEquals(JAVA_PROXY_HTTPS_PASSWORD, proxyVariables.getHttpsPassword());
    assertEquals(JAVA_HTTPS_NO_HOSTS, proxyVariables.getHttpsNohosts());

    assertEquals(Boolean.valueOf(BYPASS_PROXY_IN_LOCAL_NETWORK),
        proxyVariables.getBypassProxyInLocalNetwork());

    bkContext.close();

  }


}
