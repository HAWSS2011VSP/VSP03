package mware_lib;

import mware_lib.impl.NameServiceImpl;

public class ObjectBroker { // - Frontend der Middleware -
  
  protected final String serviceHost;
  protected final int listenPort;
 
  protected ObjectBroker(final String serviceHost, final int listenPort) {
    this.serviceHost = serviceHost;
    this.listenPort = listenPort;
  }
  
  public static ObjectBroker getBroker(String serviceHost, int listenPort) {
    return new ObjectBroker(serviceHost, listenPort);
  }

  // Das hier zurückgelieferte Objekt soll der zentrale Einstiegspunkt
  // der Middleware aus Anwendersicht sein.
  // Parameter: Host und Port, bei dem die Dienste (Namensdienst)
  // kontaktiert werden sollen.
  public NameService getNameService() {
    return new NameServiceImpl(serviceHost, listenPort);
  }
  // Liefert den Namensdienst (Stellvetreterobjekt).
}
