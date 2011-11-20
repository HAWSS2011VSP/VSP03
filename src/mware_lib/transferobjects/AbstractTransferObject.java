package mware_lib.transferobjects;

public abstract class AbstractTransferObject {
  protected Object valueFromString(String className, String str) {
    if (className.equals("int") || className.equals("java.lang.Integer")) {
      return Integer.parseInt(str);
    } else if (className.equals("double")
        || className.equals("java.lang.Double")) {
      return Double.parseDouble(str);
    } else if (className.equals("java.lang.String")) {
      return str;
    } else if (className.equals("boolean")
        || className.equals("java.lang.Boolean")) {
      return Boolean.parseBoolean(str);
    }
    return null;
  }
}
