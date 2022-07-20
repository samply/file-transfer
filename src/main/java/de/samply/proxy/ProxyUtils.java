package de.samply.proxy;

public class ProxyUtils {

  private enum ProxyPart {
    HOST,
    PORT,
    USER,
    PASSWORD
  }

  public static Proxy convert(String sysProxy) {

    Proxy result = new Proxy();

    if (sysProxy != null && sysProxy.length() > 0) {

      String host = null;
      String user = null;
      String password = null;
      String port = null;

      int slashIndex = sysProxy.indexOf("//") + "//".length();
      if (slashIndex > 0) {
        host = sysProxy.substring(0, slashIndex);
        sysProxy = sysProxy.substring(slashIndex);
      }

      int atIndex = sysProxy.indexOf('@');
      if (atIndex > 0) {

        int userPasswortSeparatorIndex = sysProxy.indexOf(':');

        if (userPasswortSeparatorIndex > 0) {
          user = sysProxy.substring(0, userPasswortSeparatorIndex);
          password = sysProxy.substring(userPasswortSeparatorIndex + 1, atIndex);
        } else {
          user = sysProxy.substring(0, atIndex);
        }

        sysProxy = sysProxy.substring(atIndex + 1);

      }

      int hostPortSeparatorIndex = sysProxy.indexOf(':');

      if (hostPortSeparatorIndex > 0) {
        host += sysProxy.substring(0, hostPortSeparatorIndex);
        port = sysProxy.substring(hostPortSeparatorIndex + 1);
      } else {
        host += sysProxy;
      }

      result.setHost(host);
      result.setPort(port);
      result.setUser(user);
      result.setPassword(password);

    }

    return result;

  }

  private static String extract(String sysProxy, ProxyPart proxyPart) {

    Proxy proxy2 = convert(sysProxy);

    return switch (proxyPart) {
      case HOST -> proxy2.getHost();
      case PORT -> proxy2.getPort();
      case USER -> proxy2.getUser();
      case PASSWORD -> proxy2.getPassword();
      default -> null;
    };

  }

  public static String extractHost(String sysProxy) {
    return extract(sysProxy, ProxyPart.HOST);
  }

  public static String extractPort(String sysProxy) {
    return extract(sysProxy, ProxyPart.PORT);
  }

  public static String extractUser(String sysProxy) {
    return extract(sysProxy, ProxyPart.USER);
  }

  public static String extractPassword(String sysProxy) {
    return extract(sysProxy, ProxyPart.PASSWORD);
  }

}
