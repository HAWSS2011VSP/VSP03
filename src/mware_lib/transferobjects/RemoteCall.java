package mware_lib.transferobjects;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public final class RemoteCall extends AbstractTransferObject {

  @XmlElement
  private final String objectName;

  @XmlElement
  private final String methodName;

  @XmlElement
  private final String args;

  @SuppressWarnings("unused")
  private RemoteCall() {
    objectName = "";
    methodName = "";
    args = "";
  }

  public RemoteCall(final String objectName, final String methodName,
      final Object... args) {
    this.objectName = objectName;
    this.methodName = methodName;
    StringBuffer argsBuffer = new StringBuffer();
    for (int i = 0; i < args.length; i++) {
      argsBuffer.append(args[i].toString());
      if (i < args.length - 1)
        argsBuffer.append(";");
    }
    this.args = argsBuffer.toString();
  }

  public Object invokeOn(Object obj) throws IllegalArgumentException,
      IllegalAccessException, InvocationTargetException {
    String[] paramsStrings = args.split(";");
    for (Method method : obj.getClass().getMethods()) {
      Class<?>[] paramTypes = method.getParameterTypes();
      if (!method.getName().equals(methodName)
          || (!paramsStrings[0].isEmpty() && paramTypes.length != paramsStrings.length))
        continue;
      Object[] params = new Object[paramTypes.length];
      for (int i = 0; i < paramTypes.length; i++) {
        params[i] = valueFromString(paramTypes[i].getCanonicalName(),
            paramsStrings[i]);
      }
      method.setAccessible(true);
      return method.invoke(obj, params);
    }
    throw new IllegalAccessException("Method " + methodName
        + " could not be found.");
  }

  public String getObjectName() {
    return objectName;
  }
}
