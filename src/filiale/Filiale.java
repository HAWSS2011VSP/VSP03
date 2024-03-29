package filiale;

import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import mware_lib.NameService;
import mware_lib.ObjectBroker;
import branch_access.Manager;

/**
 * GUI-basierte Hauptanwendung des Geldautomaten.
 * 
 */
public class Filiale extends Frame implements ActionListener {
  private static final long serialVersionUID = 6751599359033886260L;
  // Textfelder
  private TextField BankTextField, KontoTextFieldNeu, KontoTextFieldDelete,
      OwnerTextField;
  // Knoepfe
  private Button NeuesKontoButton, KontoLoeschenButton;
  // Statuszeile
  private StatusLine StatusLabel;
  private ObjectBroker objBroker;

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
   * Konstruktor. Aufbau der GUI.
   * 
   * @param objBroker
   * 
   */
  public Filiale(ObjectBroker objBroker) {
    super("Filiale");

    this.objBroker = objBroker;

    // --- Layout manager
    GridBagConstraints constraints = new GridBagConstraints();
    GridBagLayout gridbag = new GridBagLayout();
    setLayout(gridbag);

    // --- Bank (Namenseingabe)
    Label BankLabel = new Label("Bank:        ");
    constraints.insets = new Insets(10, 20, 5, 20);
    constraints.gridwidth = GridBagConstraints.RELATIVE;
    constraints.anchor = GridBagConstraints.WEST;
    gridbag.setConstraints(BankLabel, constraints);
    add(BankLabel);

    BankTextField = new TextField(20);
    constraints.gridwidth = GridBagConstraints.REMAINDER;
    gridbag.setConstraints(BankTextField, constraints);
    add(BankTextField);

    // --- (Trenner) ------------------------
    Label separatorLabel1 = new Label(
        "--------------------------------------------------");
    constraints.insets = new Insets(5, 20, 5, 20);
    gridbag.setConstraints(separatorLabel1, constraints);
    add(separatorLabel1);

    // --- Operation: Konto einrichten

    Label KontoLabel = new Label("Kontoinhaber:   ");
    constraints.anchor = GridBagConstraints.WEST;
    constraints.gridwidth = GridBagConstraints.RELATIVE;
    constraints.insets = new Insets(10, 20, 5, 0);
    gridbag.setConstraints(KontoLabel, constraints);
    add(KontoLabel);

    OwnerTextField = new TextField(20);
    constraints.insets = new Insets(10, 20, 5, 25);
    constraints.gridwidth = GridBagConstraints.REMAINDER;
    constraints.anchor = GridBagConstraints.EAST;
    gridbag.setConstraints(OwnerTextField, constraints);
    add(OwnerTextField);

    NeuesKontoButton = new Button("Neues Konto!");
    constraints.insets = new Insets(5, 20, 5, 20);
    constraints.anchor = GridBagConstraints.WEST;
    constraints.gridwidth = GridBagConstraints.RELATIVE;
    gridbag.setConstraints(NeuesKontoButton, constraints);
    add(NeuesKontoButton);
    NeuesKontoButton.addActionListener(this);

    KontoTextFieldNeu = new TextField(20);
    KontoTextFieldNeu.setEditable(false);
    constraints.gridwidth = GridBagConstraints.REMAINDER;
    gridbag.setConstraints(KontoTextFieldNeu, constraints);
    add(KontoTextFieldNeu);

    // --- (Trenner) ------------------------
    Label separatorLabel2 = new Label(
        "--------------------------------------------------");
    constraints.insets = new Insets(5, 20, 5, 20);
    constraints.anchor = GridBagConstraints.WEST;
    gridbag.setConstraints(separatorLabel2, constraints);
    add(separatorLabel2);

    // --- Operation: Konto loeschen

    KontoTextFieldDelete = new TextField(20);
    constraints.anchor = GridBagConstraints.WEST;
    constraints.gridwidth = GridBagConstraints.RELATIVE;
    constraints.insets = new Insets(10, 20, 5, 0);
    gridbag.setConstraints(KontoTextFieldDelete, constraints);
    add(KontoTextFieldDelete);

    KontoLoeschenButton = new Button("Konto löschen!");
    constraints.insets = new Insets(10, 20, 5, 25);
    constraints.gridwidth = GridBagConstraints.REMAINDER;
    gridbag.setConstraints(KontoLoeschenButton, constraints);
    add(KontoLoeschenButton);
    KontoLoeschenButton.addActionListener(this);

    // --- Statusfeld
    StatusLabel = new StatusLine(
        "                                                                      ");
    constraints.insets = new Insets(20, 20, 20, 20);
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

    if (e.getSource() == NeuesKontoButton) {
      if (BankTextField.getText().trim().length() == 0) {
        StatusLabel.setErrorText("Keine Bank angegeben!");
      } else if (OwnerTextField.getText().length() == 0) {
        StatusLabel.setErrorText("Kein Kontoinhaber angegeben!");
      } else {
        String kontoIDneu = null;
        String accountOwner = OwnerTextField.getText();

        // Neues Konto einrichten lassen
        NameService nameSvc = objBroker.getNameService();
        Object bankObj = nameSvc.resolve(BankTextField.getText());
        if (bankObj instanceof Manager) {
          Manager bank = (Manager) bankObj;
          kontoIDneu = bank.createAccount(accountOwner);

          StatusLabel.setInfoText("Neues konto mit ID " + kontoIDneu + " für "
              + accountOwner + " eingerichtet");
          KontoTextFieldNeu.setText(kontoIDneu); // ins Kontofeld eintragen
        } else {
          StatusLabel.setErrorText("Die Bank existiert nicht!");
        }
      }
    } else if (e.getSource() == KontoLoeschenButton) {
      if (BankTextField.getText().trim().length() == 0) {
        StatusLabel.setErrorText("Keine Bank angegeben!");
      } else if (KontoTextFieldDelete.getText().trim().length() == 0) {
        StatusLabel.setErrorText("Kein Konto angegeben!");
      } else {
        String kontoID = KontoTextFieldDelete.getText();

        // Konto loeschen lassen
        NameService nameSvc = objBroker.getNameService();
        Object bankObj = nameSvc.resolve(BankTextField.getText());
        if (bankObj instanceof Manager) {
          Manager bank = (Manager) bankObj;
          boolean successDelete = bank.removeAccount(kontoID);
          if (successDelete) {
            StatusLabel.setInfoText("Konto mit ID " + kontoID + " gelöscht");
          } else {
            StatusLabel.setErrorText("Das Konto existiert nicht!");
          }
        } else {
          StatusLabel.setErrorText("Die Bank existiert nicht!");
        }
      }
    }
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    if (args.length >= 2 && !args[0].equals("--help")) {
      ObjectBroker objBroker;
      int port;
      String address = args[0];

      try {
        port = Integer.parseInt(args[1]);
        objBroker = ObjectBroker.getBroker(address, port);
      } catch (Exception e) {
        System.err
            .println("Usage: java filiale.Filiale <name-service-host> <name-service-port>");
        return;
      }

      Filiale kundenDienst = new Filiale(objBroker);
      kundenDienst.setVisible(true);
    } else {
      System.err
          .println("Usage: java filiale.Filiale <name-service-host> <name-service-port>");
    }
  }

}
