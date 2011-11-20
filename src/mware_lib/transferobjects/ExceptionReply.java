package mware_lib.transferobjects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "exception")
public class ExceptionReply {
  @XmlElement
  private final String message;

  @XmlElement
  private final String exceptionType;

  @SuppressWarnings("unused")
  private ExceptionReply() {
    this.message = "";
    this.exceptionType = "";
  }

  public ExceptionReply(Throwable t) {
    this.message = t.getMessage();
    this.exceptionType = t.getClass().getCanonicalName();
  }

  public String getMessage() {
    return message;
  }

  public String getExceptionType() {
    return exceptionType;
  }
}
