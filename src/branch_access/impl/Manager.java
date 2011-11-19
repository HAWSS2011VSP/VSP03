package branch_access.impl;

public final class Manager extends branch_access.Manager {

  private final String host;
  private final int port;
  
  public Manager(String host, int port) {
    this.host = host;
    this.port = port;
  }
  
  @Override
  public String createAccount(String owner) {
    return null;
  }

  @Override
  public boolean removeAccount(String accountID) {
    return false;
  }
  
  public String getHost() {
    return host;
  }
  
  public int getPort() {
    return port;
  }

}
