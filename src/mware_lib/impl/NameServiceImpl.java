package mware_lib.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import mware_lib.NameService;
import mware_lib.transferobjects.BindingContainer;
import mware_lib.transferobjects.Marshalling;
import mware_lib.transferobjects.ObjectReply;
import mware_lib.transferobjects.ObjectRequest;

public final class NameServiceImpl extends NameService {

  protected final String address;
  protected final int port;

  public NameServiceImpl(final String address, final int port) {
    this.address = address;
    this.port = port;
  }

  @Override
  public void rebind(final Object servant, final String name) {
    send(Marshalling.marshal(
      new BindingContainer(name, servant)
    ));
  }

  @Override
  public Object resolve(final String name) {
    return requestAndGetReply(Marshalling.marshal(
      new ObjectRequest(name)
    ));
  }
  
  private boolean send(final String str) {
    try {
      send(str, new Socket(address, port));
      return true;
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }
  
  private void send(final String str, final Socket socket) throws IOException {
    socket.getOutputStream().write((str+"\n").getBytes());
  }
  
  private Object requestAndGetReply(final String str) {
    try {
      Socket socket = new Socket(address, port);
      send(str, socket);
      BufferedReader reader = new BufferedReader(
        new InputStreamReader(socket.getInputStream()));

      String line = reader.readLine();
      Object obj = Marshalling.unmarshal(line);
      if(obj instanceof ObjectReply) {
        return ((ObjectReply)obj).getObject();
      }
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  protected final static class NameServiceStorage {
    final Map<String, Object> objects;
    final ReadWriteLock locker = new ReentrantReadWriteLock();

    public NameServiceStorage() {
      objects = new HashMap<String, Object>();
    }

    public void put(final String id, final Object obj) {
      try {
        locker.writeLock().lock();
        objects.put(id, obj);
      } finally {
        locker.writeLock().unlock();
      }
    }

    public Object get(String id) {
      try {
        locker.readLock().lock();
        return objects.get(id);
      } finally {
        locker.readLock().unlock();
      }
    }
  }

  public static void main(String[] args) throws IOException {
    int port = 1337; // default port
    final ExecutorService pool = Executors.newFixedThreadPool(12);
    final NameServiceStorage storage = new NameServiceStorage();

    if (args.length > 0) {
      try {
        port = Integer.parseInt(args[0]);
      } catch (NumberFormatException e) {
        System.out.println("Argument has to be a number.");
        System.out.println("Falling back to default port " + port + ".");
      }
    }
    try {
      InetAddress localhost = java.net.InetAddress.getLocalHost();
      System.out.println("Starting NameService on "
          + localhost.getHostAddress() + ":" + port + "...");

      ServerSocket sock = new ServerSocket(port);
      while (true) {
        final Socket client = sock.accept();
        System.out.println("Accepting client "
            + client.getInetAddress().getHostAddress() + " on port "
            + client.getPort() + "...");
        pool.execute(new RequestHandler(client, storage));
      }
    } catch (UnknownHostException e) {
      System.err.println("Host could not be determined.");
    }
  }

}
