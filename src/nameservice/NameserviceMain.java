package nameservice;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mware_lib.impl.NameServiceStorage;

public class NameserviceMain {

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
