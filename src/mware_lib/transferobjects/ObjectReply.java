package mware_lib.transferobjects;

import java.lang.reflect.Constructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public final class ObjectReply {
  @XmlElement
  private final String id;
  
  @XmlElement
  private final String object;
  
  @SuppressWarnings("unused")
  private ObjectReply(){
    id = null;
    object = null;
  }
  
  public ObjectReply(final String id, final String object) {
    this.id = id;
    this.object = object;
  }
  
  public String getId() {
    return id;
  }
  
  public Object getObject() {
    String[] parts = object.split(";");
    try {
      Class<?> klazz = Class.forName(parts[0]);
      for(Constructor<?> constructor : klazz.getDeclaredConstructors()) {
        Class<?>[] types = constructor.getParameterTypes();
        if(parts.length - 1 != types.length)
          continue;
        Object[] params = new Object[types.length];
        for(int i = 0; i < types.length; i++) {
          params[i] = valueFromString(types[i], parts[i+1]);
        }
        return constructor.newInstance(params);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
  
  private Object valueFromString(Class<?> klazz, String str) {
    String className = klazz.getCanonicalName();
    if(className.equals("int")) {
      return Integer.parseInt(str);
    } else if(className.equals("double")) {
      return Double.parseDouble(str);
    } else if(className.equals("java.lang.String")) {
      return str;
    }
    return null;
  }
}
