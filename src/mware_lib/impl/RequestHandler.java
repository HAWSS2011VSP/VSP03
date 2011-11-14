package mware_lib.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.Socket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import mware_lib.impl.NameServiceImpl.NameServiceStorage;
import mware_lib.transferobjects.BindingContainer;
import mware_lib.transferobjects.ObjectRequest;
import mware_lib.transferobjects.Stringifier;

final class RequestHandler implements Runnable {

  private final NameServiceStorage storage;
  private final Socket client;
  private final JAXBContext jaxbContext;
  private final Unmarshaller unmarshaller;

  public RequestHandler(Socket client, NameServiceStorage storage)
      throws JAXBException {
    this.storage = storage;
    this.client = client;
    this.jaxbContext = JAXBContext.newInstance(BindingContainer.class,
        ObjectRequest.class);
    this.unmarshaller = jaxbContext.createUnmarshaller();
  }

  @Override
  public void run() {
    BufferedReader reader;
    try {
      reader = new BufferedReader(
          new InputStreamReader(client.getInputStream()));

      String line;
      while ((line = reader.readLine()) != null) {
        handle(line);
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

  private void handle(String line) throws IOException {
    Object obj;
    try {
      obj = unmarshaller.unmarshal(new StringReader(line));
      if (obj instanceof BindingContainer) {
        handle((BindingContainer) obj);
      } else if (obj instanceof ObjectRequest) {
        handle((ObjectRequest) obj);
      }
    } catch (JAXBException e) {
      e.printStackTrace();
    }
  }

  private void handle(BindingContainer binding) {
    System.out.println("Binding object to key " + binding.getId());
    storage.put(binding.getId(), Stringifier.destringify(binding.getObject()));
  }

  private void handle(ObjectRequest request) throws IOException {
    System.out.println("Object with key " + request.getId() + " requested...");
    Object returnObj = storage.get(request.getId());
    client.getOutputStream().write(
        (Stringifier.stringify(returnObj) + "\n").getBytes());
  }
}
