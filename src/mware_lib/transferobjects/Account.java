package mware_lib.transferobjects;

import cash_access.OverdraftException;

public class Account extends cash_access.Account {

  private final String accountId;
  private final String host;

  public Account(final String accountId, final String host) {
    this.accountId = accountId;
    this.host = host;
  }

  @Override
  public void deposit(double amount) {

  }

  @Override
  public void withdraw(double amount) throws OverdraftException {

  }

  @Override
  public double getBalance() {

    return 0;
  }

  public String getAccountId() {
    return accountId;
  }

  public String getHost() {
    return host;
  }
}
