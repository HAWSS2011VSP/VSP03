package bank;


import java.awt.Font;
import java.awt.TextArea;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Diese Klasse ist die Anzeigeliste fuer die Kontostaende.
 */
public class AccountListTextArea extends TextArea {
	private static final long serialVersionUID = -1596766873725592914L;

	private Bank MyBank;
	
	private Object FormattedEntry[] = new Object[3]; // zum Formatieren
	
	public AccountListTextArea(String text, int rows, int columns, int scrollbars, Bank myBank) {
		super(text, rows, columns, scrollbars);	
		setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
		
		MyBank = myBank;
	}
	
	/**
	 * Anzeige aktualisieren
	 */
	public void update() {
		Hashtable AccountHTable = MyBank.getBalanceList();
		Hashtable OwnerHTable = MyBank.getOwnerList();
		
		// Angezeigte Liste aktualisieren
		setText("");
		append("Konto                      Stand          Kontoinhaber\n\n");
		Enumeration keys = AccountHTable.keys();
		while (keys.hasMoreElements()) {
			String id = (String)keys.nextElement();
			FormattedEntry[0] = id;
			FormattedEntry[1] = (Double) AccountHTable.get(id);
			FormattedEntry[2] = (String) OwnerHTable.get(id);
			
			append(String.format("%10s:     %15.2f          %s", FormattedEntry)
					+ "\n");		
		}
	}
}
