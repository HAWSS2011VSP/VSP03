package mware_lib.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import mware_lib.NameService;
import mware_lib.transferobjects.BindingContainer;
import mware_lib.transferobjects.Marshalling;
import mware_lib.transferobjects.ObjectReply;
import mware_lib.transferobjects.ObjectRequest;
import mware_lib.transferobjects.Transportable;

public final class NameServiceImpl extends NameService {

  protected final String address;
  protected final int port;
  protected static final NameServiceStorage boundObjects;
  protected static final RPCHandler rpcHandler;

  static {
    boundObjects = new NameServiceStorage();
    rpcHandler = new RPCHandler(boundObjects);
    Thread rpcThread = new Thread(rpcHandler);
    rpcThread.setDaemon(true);
    rpcThread.start();
  }

  public NameServiceImpl(final String address, final int port) {
    this.address = address;
    this.port = port;
  }

  @Override
  public void rebind(final Object servant, final String name) {
    if (servant == null) {
      send(Marshalling.marshal(new BindingContainer(name, "")));
      boundObjects.put(name, null);
    } else {
      send(Marshalling.marshal(new BindingContainer(name, stringify(servant,
          name))));
      boundObjects.put(name, servant);
    }
  }

  private String stringify(Object servant, String name) {
    String klass = "";
    if (servant instanceof Transportable) {
      klass = ((Transportable) servant).getRemoteClass().getCanonicalName();
    }
    String host = "";
    try {
      host = InetAddress.getLocalHost().getCanonicalHostName();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }

    return klass + ";" + host + ";" + rpcHandler.getPort() + ";" + name;
  }

  @Override
  public Object resolve(final String name) {
    return requestAndGetReply(Marshalling.marshal(new ObjectRequest(name)));
  }

  private boolean send(final String str) {
    Socket socket = null;
    try {
      socket = new Socket(address, port);
      send(str, socket);
      return true;
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (socket != null) {
          socket.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return false;
  }

  private void send(final String str, final Socket socket) throws IOException {
    socket.getOutputStream().write((str + "\n").getBytes());
  }

  private Object requestAndGetReply(final String str) {
    Socket socket = null;
    try {
      socket = new Socket(address, port);
      send(str, socket);
      BufferedReader reader = new BufferedReader(new InputStreamReader(
          socket.getInputStream()));

      String line = reader.readLine();
      Object obj = Marshalling.unmarshal(line);
      if (obj instanceof ObjectReply) {
        return ((ObjectReply) obj).getObject();
      }
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (socket != null) {
        try {
          socket.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return null;
  }
}
