package mware_lib.transferobjects;

final public class Stringifier {
  public static String stringify(Object obj) {
    StringBuffer result = new StringBuffer();
    if (obj instanceof Account) {
      Account acc = (Account) obj;
      result.append("Account;");
      result.append(acc.getAccountId() + ";");
      result.append(acc.getHost());
    }

    return result.toString();
  }

  public static Object destringify(String str) {
    String[] parts = str.trim().split(";");
    if (parts[0].equals("Account")) {
      return new Account(parts[1], parts[2]);
    }
    return null;
  }
}
