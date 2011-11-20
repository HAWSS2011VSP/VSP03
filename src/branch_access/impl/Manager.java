package branch_access.impl;

import mware_lib.impl.RPCClient;
import mware_lib.transferobjects.CallReply;
import mware_lib.transferobjects.RemoteCall;

public final class Manager extends branch_access.Manager {

  private final String host;
  private final int port;
  private final String name;
  private final RPCClient client;

  public Manager(String host, int port, String name) {
    this.host = host;
    this.port = port;
    this.name = name;
    this.client = new RPCClient(host, port);
  }

  @Override
  public String createAccount(String owner) {
    Object result = client
        .sendRPC(new RemoteCall(name, "createAccount", owner));
    if (result instanceof CallReply) {
      return (String) ((CallReply) result).getObject();
    }
    return null;
  }

  @Override
  public boolean removeAccount(String accountID) {
    Object result = client.sendRPC(new RemoteCall(name, "removeAccount",
        accountID));
    if (result instanceof CallReply) {
      return (Boolean) ((CallReply) result).getObject();
    }
    return false;
  }

  public String getHost() {
    return host;
  }

  public int getPort() {
    return port;
  }

}
