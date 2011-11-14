package mware_lib;

public abstract class NameService { // - Schnittstelle des Namensdienstes -
  public abstract void rebind(Object servant, String name);

  // Meldet ein Objekt (servant) beim Namensdienst an.
  // Eine eventuell schon vorhandene Objektreferenz gleichen Namens
  // soll Ÿberschrieben werden.
  public abstract Object resolve(String name);
  // Liefert die Objektreferenz (Stellvertreterobjekt) zu einem Namen.
}
