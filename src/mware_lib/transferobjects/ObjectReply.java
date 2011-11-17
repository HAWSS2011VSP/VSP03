package mware_lib.transferobjects;

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
    return Stringifier.destringify(object);
  }
}
