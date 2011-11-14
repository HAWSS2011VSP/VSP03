package mware_lib;

public class ObjectBroker { // - Frontend der Middleware -
  public static ObjectBroker getBroker(String serviceHost, int listenPort) {
    return null;
  }

  // Das hier zurückgelieferte Objekt soll der zentrale Einstiegspunkt
  // der Middleware aus Anwendersicht sein.
  // Parameter: Host und Port, bei dem die Dienste (Namensdienst)
  // kontaktiert werden sollen.
  public NameService getNameService() {
    return null;
  }
  // Liefert den Namensdienst (Stellvetreterobjekt).
}
