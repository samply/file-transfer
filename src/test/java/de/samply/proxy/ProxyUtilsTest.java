package de.samply.proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ProxyUtilsTest {

  private String proxyUrl1 = "http://myuser:mypassword@myproxy.com:1234";
  private String proxyUrl2 = "http://myproxy.com:1234";
  private String proxyUrl3 = "http://myproxy.com";
  private String proxyUrl4 = "http://myuser:mypassword@myproxy.com";
  private String proxyUrl5 = "http://myuser@myproxy.com";


  @Test
  void convert1() {

    Proxy proxy = ProxyUtils.convert(proxyUrl1);
    assertEquals("http://myproxy.com", proxy.getHost());
    assertEquals("1234", proxy.getPort());
    assertEquals("myuser", proxy.getUser());
    assertEquals("mypassword", proxy.getPassword());

  }

  @Test
  void convert2() {

    Proxy proxy = ProxyUtils.convert(proxyUrl2);
    assertEquals("http://myproxy.com", proxy.getHost());
    assertEquals("1234", proxy.getPort());
    assertEquals(null, proxy.getUser());
    assertEquals(null, proxy.getPassword());

  }

  @Test
  void convert3() {

    Proxy proxy = ProxyUtils.convert(proxyUrl3);
    assertEquals("http://myproxy.com", proxy.getHost());
    assertEquals(null, proxy.getPort());
    assertEquals(null, proxy.getUser());
    assertEquals(null, proxy.getPassword());

  }

  @Test
  void convert4() {

    Proxy proxy = ProxyUtils.convert(proxyUrl4);
    assertEquals("http://myproxy.com", proxy.getHost());
    assertEquals(null, proxy.getPort());
    assertEquals("myuser", proxy.getUser());
    assertEquals("mypassword", proxy.getPassword());

  }

  @Test
  void convert5() {

    Proxy proxy = ProxyUtils.convert(proxyUrl5);
    assertEquals("http://myproxy.com", proxy.getHost());
    assertEquals(null, proxy.getPort());
    assertEquals("myuser", proxy.getUser());
    assertEquals(null, proxy.getPassword());

  }

  @Test
  void convert6() {

    Proxy proxy = ProxyUtils.convert(null);
    assertEquals(null, proxy.getHost());
    assertEquals(null, proxy.getPort());
    assertEquals(null, proxy.getUser());
    assertEquals(null, proxy.getPassword());

  }


  @Test
  void extractHost() {
    String host = ProxyUtils.extractHost(proxyUrl1);
    assertEquals("http://myproxy.com", host);
  }

  @Test
  void extractPort() {
    String port = ProxyUtils.extractPort(proxyUrl1);
    assertEquals("1234", port);
  }

  @Test
  void extractUser() {
    String user = ProxyUtils.extractUser(proxyUrl1);
    assertEquals("myuser", user);
  }

  @Test
  void extractPassword() {
    String password = ProxyUtils.extractPassword(proxyUrl1);
    assertEquals("mypassword", password);
  }

}
