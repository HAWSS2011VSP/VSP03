package mware_lib.transferobjects;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Marshalling {
  private static Marshaller marshaller;
  private static Unmarshaller unmarshaller;
  
  static {
    try {
      JAXBContext context = JAXBContext.newInstance(
          BindingContainer.class,
          ObjectRequest.class,
          ObjectReply.class);
      marshaller = context.createMarshaller();
      unmarshaller = context.createUnmarshaller();
    } catch(JAXBException e) {
      e.printStackTrace();
    }
  }
  
  public static String marshal(Object obj) {
    Writer writer = new StringWriter();
    try {
      marshaller.marshal(obj, writer);
    } catch (JAXBException e) {
      e.printStackTrace();
    }
    return writer.toString();
  }
  
  public static Object unmarshal(String str) {
    try {
      return unmarshaller.unmarshal(new StringReader(str));
    } catch (JAXBException e) {
      e.printStackTrace();
    }
    return null;
  }
}
