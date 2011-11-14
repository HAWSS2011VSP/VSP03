package mware_lib.transferobjects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ObjectRequest {
  @XmlAttribute
  private final String id;

  @SuppressWarnings("unused")
  private ObjectRequest() {
    id = null;
  }

  public ObjectRequest(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }
}
