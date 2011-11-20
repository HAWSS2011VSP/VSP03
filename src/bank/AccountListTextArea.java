package bank;

import java.awt.Font;
import java.awt.TextArea;
import java.util.Map;

/**
 * Diese Klasse ist die Anzeigeliste fuer die Kontostaende.
 */
public class AccountListTextArea extends TextArea {
  private static final long serialVersionUID = -1596766873725592914L;

  private Bank MyBank;

  private Object FormattedEntry[] = new Object[3]; // zum Formatieren

  public AccountListTextArea(String text, int rows, int columns,
      int scrollbars, Bank myBank) {
    super(text, rows, columns, scrollbars);
    setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

    MyBank = myBank;
  }

  /**
   * Anzeige aktualisieren
   */
  public void update() {
    Map<String, Double> AccountHTable = MyBank.getBalanceList();
    Map<String, String> OwnerHTable = MyBank.getOwnerList();

    // Angezeigte Liste aktualisieren
    setText("");
    append("Konto                      Stand          Kontoinhaber\n\n");
    for (String id : AccountHTable.keySet()) {
      FormattedEntry[0] = id;
      FormattedEntry[1] = (Double) AccountHTable.get(id);
      FormattedEntry[2] = (String) OwnerHTable.get(id);

      append(String.format("%10s:     %15.2f          %s", FormattedEntry)
          + "\n");
    }
  }
}
