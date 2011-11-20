package cash_access;

import mware_lib.transferobjects.Transportable;

public abstract class Account extends Transportable { // - Konto, Funktionalität
                                                      // s.u. -
  public abstract void deposit(double amount);

  public abstract void withdraw(double amount) throws OverdraftException;

  public abstract double getBalance();

  public Class<?> getRemoteClass() {
    return cash_access.impl.Account.class;
  }
}
