package bank;

import cash_access.OverdraftException;

/**
 * Klasse fuer ein Konto
 */
public class Account extends cash_access.Account {
  private String ID; // Konto-ID
  private double balance; // Kontostand

  public Account(String id) {
    // Initialisieren
    ID = id;
    balance = 0.0;
  }

  /**
   * Liefert die ID dieses Kontos. (Wird fuer die GUI benutzt.)
   * 
   * @return Konto-ID
   */
  protected String getID() {
    return ID;
  }

  @Override
  public synchronized void deposit(double amount) {
    balance += amount;
  }

  @Override
  public synchronized void withdraw(double amount) throws OverdraftException {
    if (amount > balance) {
      throw new OverdraftException("Unable to withdraw " + amount
          + " because the balance is only " + balance + ".");
    }
    balance -= amount;
  }

  @Override
  public double getBalance() {
    return balance;
  }
}
