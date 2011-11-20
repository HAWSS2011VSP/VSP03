package mware_lib.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mware_lib.impl.NameServiceImpl.NameServiceStorage;
import mware_lib.transferobjects.CallReply;
import mware_lib.transferobjects.ExceptionReply;
import mware_lib.transferobjects.Marshalling;
import mware_lib.transferobjects.RemoteCall;

public class RPCHandler implements Runnable {

  private ServerSocket sock;
  private final NameServiceStorage storage;

  public RPCHandler(NameServiceStorage storage) {
    this.storage = storage;
  }

  @Override
  public void run() {
    try {
      ExecutorService pool = Executors.newCachedThreadPool();
      sock = new ServerSocket(0);
      System.out.println("RPC handler running on "
          + sock.getLocalSocketAddress().toString());
      while (true) {
        final Socket client = sock.accept();
        pool.execute(new Runnable() {
          @Override
          public void run() {
            BufferedReader reader;
            try {
              reader = new BufferedReader(new InputStreamReader(client
                  .getInputStream()));

              String line;
              while ((line = reader.readLine()) != null) {
                Object result = null;
                Object call = Marshalling.unmarshal(line);
                if (call instanceof RemoteCall) {
                  RemoteCall rc = (RemoteCall) call;
                  try {
                    result = new CallReply(rc.invokeOn(storage.get(rc
                        .getObjectName())));
                  } catch (InvocationTargetException e) {
                    result = new ExceptionReply(e.getCause());
                  } catch (Exception e) {
                    result = new ExceptionReply(e);
                  }
                  client.getOutputStream().write(
                      (Marshalling.marshal(result) + "\n").getBytes());
                }
              }
            } catch (IOException e) {
              e.printStackTrace();
            } finally {
              try {
                client.close();
                System.out.println("Connection to client on "
                    + client.getInetAddress().getHostAddress() + " closed...");
              } catch (IOException e) {
                e.printStackTrace();
              }
            }
          }
        });
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public int getPort() {
    return sock.getLocalPort();
  }

}
