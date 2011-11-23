package mware_lib.transferobjects;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Marshalling {

  private static JAXBContext context;
	
  static {
	try {
	  context = JAXBContext.newInstance(BindingContainer.class,
		          ObjectRequest.class, ObjectReply.class, RemoteCall.class,
		          ExceptionReply.class, CallReply.class);
	} catch (JAXBException e) {
		// TODO Auto-generated catch block
		context = null;
		e.printStackTrace();
	}
  }
  
  public static String marshal(Object obj) {
	  
	Marshaller marshaller = null;
    Writer writer = new StringWriter();
    try {
      marshaller = context.createMarshaller();
      marshaller.marshal(obj, writer);
    } catch (JAXBException e) {
      e.printStackTrace();
    }
    return writer.toString();
  }

  public static Object unmarshal(String str) {
    try {
      Unmarshaller unmarshaller = context.createUnmarshaller();
      return unmarshaller.unmarshal(new StringReader(str));
    } catch (JAXBException e) {
      e.printStackTrace();
    }
    return null;
  }
}
