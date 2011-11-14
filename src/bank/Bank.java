package bank;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Klasse fuer den Mananger der Bank.
 *
 */
public class Bank extends branch_access.Manager {
	private Hashtable<String, Account> AccountTable; // interne Liste der Konten
	private Hashtable<String, String> OwnerTable; // interne Liste der Kontoinhaber
	
	private String AccountPrefix; // fuer bankuebergreifend eindeutige Konto-IDs
	
	private int AccountCounter;  // Kontnummernzaehler
	private Object NumberFormatted[] = new Object[1]; // zum Formatieren
	
	
	/**
	 * Konstruktor.
	 * 
	 * @param accountPrefix Prefix fuer (bankuebergreifend) eindeutige Konto-IDs
	 */
	public Bank(String accountPrefix) {
		AccountTable = new Hashtable<String, Account>();
		OwnerTable = new Hashtable<String, String>();
		
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
		return AccountPrefix+String.format("%010d", NumberFormatted);
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

		/* -----------------------------------
		 * TODO: Im Namensdienst anmelden
		 * -----------------------------------
		 */
		
		return newID;
	}

	/**
	 * Liefert Liste mit aktuellen Kontostaenden.
	 * (Wird von der GUI benutzt.)
	 * 
	 * @return Liste mit Kontostaenden.
	 */
	public Hashtable<String, Double> getBalanceList() {
		Hashtable<String, Double> balanceList = new Hashtable<String, Double>();
		
		Enumeration<String> keys = AccountTable.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			balanceList.put(key, new Double(AccountTable.get(key).getBalance()));
		}
		return balanceList;
	}

	/**
	 * Liefert Liste mit Namen der Kontoinhaber.
	 * (Wird von der GUI benutzt.)
	 * 
	 * @return Liste mit Namen der Kontoinhaber.
	 */
	public Hashtable<String, String> getOwnerList() {
		return OwnerTable;
	}


		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String myName;  // fuer Titelleiste
		String myPrefix = "A";   // fuer Konto-IDs

		if (args.length>0 && !args[0].equals("--help")) {
			myName = args[0];
			
			// Manager
			Bank myBank = new Bank(myPrefix);
			
			// GUI
			BankWindow myGUI = new BankWindow(myName, myBank);
			myGUI.setVisible(true);
			new Thread(myGUI).start();  // Aktualisierungsthread der GUI
			
			/* ---------------------------------------
			 * TODO: Manager im Namensdienst anmelden
			 * ---------------------------------------
			 */

			
		} else {
			System.err.println("Usage: java bank.Bank <name> <name-service-host> <name-service-port>");
		}
	}

	@Override
	public boolean removeAccount(String accountID) {
		// TODO Auto-generated method stub
		return false;
	}
}
