package mware_lib.transferobjects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "bind")
final public class BindingContainer {

  @XmlElement
  private final String id;

  @XmlElement
  private final String object;

  @SuppressWarnings("unused")
  private BindingContainer() {
    id = null;
    object = null;
  }

  public BindingContainer(final String id, final Transportable object) {
    this.id = id;
    this.object = object.toTransportString();
  }

  public String getId() {
    return id;
  }

  public String getObject() {
    return object;
  }
}
