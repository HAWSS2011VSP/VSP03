package branch_access;

import mware_lib.transferobjects.Transportable;

public abstract class Manager extends Transportable { // - Manager,
                                                      // Funktionalit�t s.u -
  public abstract String createAccount(String owner);

  public abstract boolean removeAccount(String accountID);

  public Class<?> getRemoteClass() {
    return branch_access.impl.Manager.class;
  }
}
