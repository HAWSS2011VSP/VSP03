package branch_access;

public abstract class Manager { // - Manager, FunktionalitŠt s.u -
  public abstract String createAccount(String owner);

  public abstract boolean removeAccount(String accountID);
}
