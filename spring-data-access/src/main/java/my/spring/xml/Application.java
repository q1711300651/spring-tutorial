package my.spring.xml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Application, собственно класс где производиться работа с xml
 */
public class Application {

    private static final String FILE_NAME = "xml/test-settings.xml";
    private Settings settings = new Settings();
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    public void setMarshaller( Marshaller marshaller ) {
        this.marshaller = marshaller;
    }

    public void setUnmarshaller( Unmarshaller unmarshaller ) {
        this.unmarshaller = unmarshaller;
    }

    public void saveSettings() throws IOException {
        try( FileOutputStream out = new FileOutputStream( FILE_NAME ) ) {
            this.marshaller.marshal( settings, new StreamResult( out ) );
        }
    }

    public void loadSettings() throws IOException {
        try( FileInputStream in = new FileInputStream( FILE_NAME ) ) {

            this.settings = ( Settings ) this.unmarshaller.unmarshal(  new StreamSource( in ) );
        }
    }

    public static void main( String[] args ) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("xml/xml-app.xml");
        Application app = context.getBean( Application.class );
        app.saveSettings();
        app.loadSettings();
    }
}
