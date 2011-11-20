package cash_access.impl;

import mware_lib.impl.RPCClient;
import mware_lib.transferobjects.CallReply;
import mware_lib.transferobjects.ExceptionReply;
import mware_lib.transferobjects.RemoteCall;
import cash_access.OverdraftException;

public class Account extends cash_access.Account {

  private final String name;
  private final int port;
  private final String host;
  private final RPCClient client;

  public Account(final String host, int port, String name) {
    this.name = name;
    this.port = port;
    this.host = host;
    this.client = new RPCClient(host, port);
  }

  @Override
  public void deposit(double amount) {
    client.sendRPC(new RemoteCall(name, "deposit", amount));
  }

  @Override
  public void withdraw(double amount) throws OverdraftException {
    Object result = client.sendRPC(new RemoteCall(name, "withdraw", amount));
    if (result instanceof ExceptionReply) {
      ExceptionReply e = (ExceptionReply) result;
      if (e.getExceptionType().equals("cash_access.OverdraftException")) {
        throw new OverdraftException(e.getMessage());
      }
    }
  }

  @Override
  public double getBalance() {
    Object result = client.sendRPC(new RemoteCall(name, "getBalance"));
    if (result instanceof CallReply) {
      return (Double) ((CallReply) result).getObject();
    }
    return 0.0;
  }
}
