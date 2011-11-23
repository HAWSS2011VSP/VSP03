package bank;

import java.util.HashMap;
import java.util.Map;

import mware_lib.NameService;
import mware_lib.ObjectBroker;

/**
 * Klasse fuer den Mananger der Bank.
 * 
 */
public class Bank extends branch_access.Manager {
  private Map<String, Account> AccountTable; // interne Liste der Konten
  private Map<String, String> OwnerTable; // interne Liste der
                                          // Kontoinhaber

  private String AccountPrefix; // fuer bankuebergreifend eindeutige Konto-IDs

  private int AccountCounter; // Kontnummernzaehler
  private Object NumberFormatted[] = new Object[1]; // zum Formatieren
  private NameService ns;

  /**
   * Konstruktor.
   * 
   * @param accountPrefix
   *          Prefix fuer (bankuebergreifend) eindeutige Konto-IDs
   * @param port
   * @param address
   */
  public Bank(String accountPrefix, NameService ns) {
    AccountTable = new HashMap<String, Account>();
    OwnerTable = new HashMap<String, String>();
    this.ns = ns;

    // Kontnummernzaehler initialisieren
    AccountCounter = 999;

    // Prefix fuer Konto-IDs dieser Bank
    AccountPrefix = accountPrefix;
  }

  /**
   * Liefert naechste verfuegbare Konto-ID.
   * 
   * @return Neue Konto-ID
   */
  private String getNextAvailID() {
    NumberFormatted[0] = new Integer(AccountCounter++);
    return AccountPrefix + String.format("%010d", NumberFormatted);
  }

  /**
   * Legt ein neues Konto an und traegt es in die angezeigte Liste ein.
   * 
   * @return Neues Kontoobjekt
   */
  private Account setupAccount(String ownerName) {
    // neue ID
    String newID = getNextAvailID();

    // neues Konto
    Account newAccount = new Account(newID);
    // ...in die Anzeigeiste
    AccountTable.put(newID, newAccount);
    OwnerTable.put(newID, ownerName);
    return newAccount;
  }

  @Override
  public String createAccount(String ownerName) {
    // Neues Kontoobjekt
    Account newAccount = setupAccount(ownerName);
    // ID des neuen Kontos
    String newID = newAccount.getID();

    // NamensdienstAnmeldung
    ns.rebind(newAccount, newID);

    return newID;
  }

  /**
   * Liefert Liste mit aktuellen Kontostaenden. (Wird von der GUI benutzt.)
   * 
   * @return Liste mit Kontostaenden.
   */
  public Map<String, Double> getBalanceList() {
    Map<String, Double> balanceList = new HashMap<String, Double>();
    for (String key : AccountTable.keySet()) {
      balanceList.put(key, new Double(AccountTable.get(key).getBalance()));
    }

    return balanceList;
  }

  /**
   * Liefert Liste mit Namen der Kontoinhaber. (Wird von der GUI benutzt.)
   * 
   * @return Liste mit Namen der Kontoinhaber.
   */
  public Map<String, String> getOwnerList() {
    return OwnerTable;
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    String myName; // fuer Titelleiste
    String myPrefix = "DJ"; // fuer Konto-IDs

    if (args.length >= 3 && !args[0].equals("--help")) {
      myName = args[0];
      String address = args[1];
      int port;
      ObjectBroker objBroker;

      try {
        port = Integer.parseInt(args[2]);
        objBroker = ObjectBroker.getBroker(address, port);
      } catch (Exception e) {
        System.err
            .println("Usage: java bank.Bank <name> <name-service-host> <name-service-port>");
        return;
      }

      NameService nameSvc = objBroker.getNameService();

      // Manager
      Bank myBank = new Bank(myPrefix+myName, nameSvc);

      // GUI
      BankWindow myGUI = new BankWindow(myName, myBank);
      myGUI.setVisible(true);
      new Thread(myGUI).start(); // Aktualisierungsthread der GUI

      // Anmeldung beim Namensdienst
      nameSvc.rebind(myBank, myName);

    } else {
      System.err
          .println("Usage: java bank.Bank <name> <name-service-host> <name-service-port>");
    }
  }

  @Override
  public boolean removeAccount(String accountID) {
    if (!AccountTable.containsKey(accountID))
      return false;

    // ...aus die Anzeigeiste
    AccountTable.remove(accountID);
    OwnerTable.remove(accountID);

    // ...indirekte abmeldung
    ns.rebind(null, accountID);

    return ns.resolve(accountID) == null;
  }
}
