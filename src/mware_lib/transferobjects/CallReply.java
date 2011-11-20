package mware_lib.transferobjects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CallReply extends AbstractTransferObject {

  @XmlElement
  private final String resultType;

  @XmlElement
  private final String result;

  @SuppressWarnings("unused")
  private CallReply() {
    resultType = "";
    result = "";
  }

  public CallReply(final Object result) {
    if (result == null) {
      this.resultType = "null";
      this.result = "";
    } else {
      this.resultType = result.getClass().getCanonicalName();
      this.result = result.toString();
    }
  }

  public Object getObject() {
    return valueFromString(resultType, result);
  }
}
