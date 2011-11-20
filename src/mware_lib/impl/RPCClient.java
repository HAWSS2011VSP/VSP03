package mware_lib.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import mware_lib.transferobjects.Marshalling;
import mware_lib.transferobjects.RemoteCall;

public class RPCClient {

  private final String host;
  private final int port;
  private Socket sock;

  public RPCClient(final String host, final int port) {
    this.host = host;
    this.port = port;
  }

  public Object sendRPC(RemoteCall call) {
    try {
      if (sock == null || sock.isClosed()) {
        reopenSock();
      }
      sock.getOutputStream().write(
          (Marshalling.marshal(call) + "\n").getBytes());
      BufferedReader reader = new BufferedReader(new InputStreamReader(
          sock.getInputStream()));
      String line = reader.readLine();
      return Marshalling.unmarshal(line);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private void reopenSock() throws UnknownHostException, IOException {
    sock = new Socket(host, port);
  }
}
