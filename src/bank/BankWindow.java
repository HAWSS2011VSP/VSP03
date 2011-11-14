package bank;


import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Diese Klasse ist die GUI der Bankanwendung.
 * 
 */
public class BankWindow extends Frame implements Runnable {
	private static final long serialVersionUID = -1477448749199671906L;
	
	private static final int UPDATE_INTERVAL = 10000; // ms
	
	private AccountListTextArea AccountList; // Kontoanzeigeliste
	
	
	/**
	 * Konstruktor.
	 * @param bankName Name der Bank. 
	 *                  Es sollte derselbe sein, unter dem die Bank beim 
	 *                  Namensdienst angemeldet wird.
	 */
	public BankWindow(String bankName, Bank manager) {
		super("Bank: '"+ bankName + "'");
		
		// Kontoanzeigeliste
		AccountList = new AccountListTextArea("", 25, 70, 
					AccountListTextArea.SCROLLBARS_BOTH, manager);
		
		setLayout(new FlowLayout());
		add(AccountList);

		/************ Schliessknopf *************/
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		pack();
	}
	
	/**
	 * Aktualisieren der angezeigten Liste.
	 */
	private void update() {
		AccountList.update();
	}

	/**
	 * Fuer Aktualisierungsthread der GUI
	 */
	public void run() {
		while (true) {
			update(); // Liste aktualisieren
			try { Thread.sleep(UPDATE_INTERVAL); } catch (InterruptedException e) { }
		}
	}
}
