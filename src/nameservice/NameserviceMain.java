package nameservice;

import java.io.IOException;
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

public class NameserviceMain {

  protected final static class NameServiceStorage {
    final Map<String, Object> objects;
    final ReadWriteLock locker;

    public NameServiceStorage() {
      objects = new HashMap<String, Object>();
      locker = new ReentrantReadWriteLock();
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