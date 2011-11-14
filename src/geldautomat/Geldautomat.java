package geldautomat;

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * GUI-basierte Hauptanwendung des Geldautomaten.
 * 
 */
public class Geldautomat extends Frame implements ActionListener {
	private static final long serialVersionUID = 6751599359033886260L;
	// Textfelder
	private TextField KontoTextField, 
			KontostandTextField, BetragsTextField, 
			EmpfKontoTextField, BankCodeTextField;
	// Knoepfe
	private Button EinzahlenButton, AbhebenButton, 
			KStandAktualisierenButton, UeberweisenButton, SerienEinzahlenButton;
	// Statuszeile
	private StatusLine StatusLabel;
	private static String DoneMessage = "Done.";
	
	private int AnzSerienEinzahlung;
	
	/**
	 * Statuszeile des Fensters
	 */
	private class StatusLine extends Label {
		private static final long serialVersionUID = -1575611930399336962L;

		public StatusLine(String text) {
			super(text);
		}
		/**
		 * Für normalen Text.
		 * 
		 * @param message
		 */
		public void setInfoText(String message) {
			setForeground(Color.BLUE);
			setText(message);
		}
		
		/**
		 * Für Fehlertext.
		 * 
		 * @param message
		 */
		public void setErrorText(String message) {
			setForeground(Color.RED);
			setText(message);
		}
	}
	
	/**
	 * Konstruktor. 
	 * Aufbau der GUI.
	 *
	 */
	public Geldautomat() {
		super("Bancomat");
		
		AnzSerienEinzahlung = 20000;
		
		//--- Layout manager
		GridBagConstraints constraints=new GridBagConstraints();
		GridBagLayout gridbag=new GridBagLayout();
		setLayout(gridbag);
		
		//--- Kontoeingabefeld
		Label KontoLabel = new Label("Konto:       ");
		constraints.insets=new Insets(10,20,5,20);
		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridwidth=GridBagConstraints.RELATIVE;
		gridbag.setConstraints(KontoLabel, constraints);
		add(KontoLabel);
		
		KontoTextField = new TextField(20);
		constraints.gridwidth=GridBagConstraints.REMAINDER;
		gridbag.setConstraints(KontoTextField, constraints);
		add(KontoTextField);
		
		//--- (Trenner) ------------------------
		Label separatorLabel1 = new Label("----------------------------------------------");
		constraints.insets=new Insets(5,20,5,20);
		gridbag.setConstraints(separatorLabel1, constraints);
		add(separatorLabel1);
		
		//--- Kontostandfeld
		Label KontostandLabel = new Label("Kontostand:");
		constraints.insets=new Insets(5,20,5,20);
		constraints.gridwidth=GridBagConstraints.RELATIVE;
		gridbag.setConstraints(KontostandLabel, constraints);
		add(KontostandLabel);
		
		KontostandTextField = new TextField(20);
		KontostandTextField.setEditable(false);
		constraints.gridwidth=GridBagConstraints.REMAINDER;
		gridbag.setConstraints(KontostandTextField, constraints);
		add(KontostandTextField);
		
		//--- Operation: Kontostand aktualisieren
		KStandAktualisierenButton = new Button("aktualisieren");
		constraints.gridwidth=GridBagConstraints.REMAINDER;
		constraints.anchor = GridBagConstraints.EAST;
		gridbag.setConstraints(KStandAktualisierenButton, constraints);
		add(KStandAktualisierenButton);
		KStandAktualisierenButton.addActionListener(this);
		
		//--- (Trenner) ------------------------
		Label separatorLabel2 = new Label("----------------------------------------------");
		constraints.insets=new Insets(5,20,5,20);
		constraints.anchor = GridBagConstraints.WEST;
		gridbag.setConstraints(separatorLabel2, constraints);
		add(separatorLabel2);
		
		//--- Betragsfeld
		Label BetragsLabel = new Label("Betrag:       ");
		constraints.insets=new Insets(10,20,5,20);
		constraints.gridwidth=GridBagConstraints.RELATIVE;
		gridbag.setConstraints(BetragsLabel, constraints);
		add(BetragsLabel);
		
		BetragsTextField = new TextField(20);
		constraints.gridwidth=GridBagConstraints.REMAINDER;
		gridbag.setConstraints(BetragsTextField, constraints);
		add(BetragsTextField);
		
		//--- Operation: Einzahlen
		EinzahlenButton = new Button("Einzahlen");
		EinzahlenButton.addActionListener(this);
		
		//--- Operation: Abheben
		AbhebenButton = new Button("Abheben");
		AbhebenButton.addActionListener(this);
		
		//--- Operation: Serieneinzahlung
		SerienEinzahlenButton = new Button(AnzSerienEinzahlung + " x Einzahlen");
		SerienEinzahlenButton.addActionListener(this);
		
		//--- Panel Einzahlen/Abheben
		Panel OpPanel1 = new Panel();
		OpPanel1.setLayout(new FlowLayout());
		OpPanel1.add(EinzahlenButton);
		OpPanel1.add(SerienEinzahlenButton);
		OpPanel1.add(AbhebenButton);
		constraints.gridwidth=GridBagConstraints.REMAINDER;
		constraints.anchor = GridBagConstraints.EAST;
		gridbag.setConstraints(OpPanel1, constraints);
		add(OpPanel1);
		
		//--- Statusfeld
		StatusLabel = new StatusLine("                                                                      ");
		constraints.insets=new Insets(20,20,20,20);
		gridbag.setConstraints(StatusLabel, constraints);
		add(StatusLabel);


		/************ Schliessknopf *************/
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		pack();
	}

	/**
	 * Hier werden die eigentlichen Aktionen veranlasst.
	 */
	public void actionPerformed(ActionEvent e) {
		StatusLabel.setInfoText("");
		
		if (e.getSource() == KStandAktualisierenButton) {
			//- Pruefung des Kontofeldinhalts
			String kontoID = KontoTextField.getText();
			if (kontoID == null || kontoID.equals("")) 
				StatusLabel.setErrorText("Kontofeld leer bzw. null");
			else {

				/*---------------------------------------------
				 * TODO: Kontostand abfragen.
				 * 
				 * --------------------------------------------
				 */
				
				
				//TODO: Kontostandanzeige in GUI aktualisieren:
				//KontostandTextField.setText(...); 
				
				StatusLabel.setInfoText(DoneMessage);
			}
		}
		else if (e.getSource() == EinzahlenButton) {
			try {
				// Einzuzahlender Betrag
				double betrag = Double.parseDouble(BetragsTextField.getText());
				
				//- Pruefung des Kontofeldinhalts
				String kontoID = KontoTextField.getText();
				if (kontoID == null || kontoID.equals("")) 
					StatusLabel.setErrorText("Kontofeld leer bzw. null");
				else {
					
					/*-----------------------------
					 * TODO: Einzahlen veranlassen
					 * ----------------------------
					 */

					
					//TODO: Kontostandanzeige aktualisieren:
					//KontostandTextField.setText(...);
					
					StatusLabel.setInfoText(DoneMessage);
				}
			} catch (NumberFormatException e1) {
				StatusLabel.setErrorText("Ungültiger Betrag!");				
			}
		}
		else if (e.getSource() == AbhebenButton) {
			try {
				// Abzuhebender Betrag
				double betrag = Double.parseDouble(BetragsTextField.getText());
				
				//- Pruefung des Kontofeldinhalts
				String kontoID = KontoTextField.getText();
				if (kontoID == null || kontoID.equals("")) 
					StatusLabel.setErrorText("Kontofeld leer bzw. null");
				else {
					
					/*-----------------------------
					 * TODO: Abheben veranlassen
					 * ----------------------------
					 */
					

					
					// TODO: Kontostandanzeige aktualisieren:
					//KontostandTextField.setText(...);
					
					StatusLabel.setInfoText(DoneMessage);
				}
			} catch (NumberFormatException e1) {
				StatusLabel.setErrorText("Ungültiger Betrag!");				
			}
			
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length>0 && !args[0].equals("--help")) {
			Geldautomat kundenDienst = new Geldautomat();
			kundenDienst.setVisible(true);		
		} else {
			System.err.println("Usage: java geldautomat.Geldautomat <name-service-host> <name-service-port>");
		}
	}

}
