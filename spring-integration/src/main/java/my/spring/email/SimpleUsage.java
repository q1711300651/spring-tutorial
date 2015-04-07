package my.spring.email;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;

/**
 * Простой способ реализации отправки сообщений по почте через spring
 *
 */
public class SimpleUsage {

    @Inject
    private MailSender mailSender;

    @Inject
    private SimpleMailMessage templateMessage;

    public void sendMassage(String someBody ) {
        SimpleMailMessage msg = new SimpleMailMessage( templateMessage );
        msg.setTo( "toSomebody@email.com" );
        msg.setText( someBody );
        mailSender.send( msg );
    }

}

/**
 * Реализация метода обратоного вызова при отпрвки и настройки элекстронных сообщений
 */
class SimplePrepareManager {

    @Inject
    private JavaMailSender mailSender;


    public void sendMassage(String someBody ) {
        MimeMessagePreparator preparator = mimeMassage -> {
            mimeMassage.setRecipients( Message.RecipientType.TO, "some.customer@mail.com " );
            mimeMassage.setFrom( new InternetAddress( "my@compny.com" ) );
            mimeMassage.setText( someBody );
        };

        mailSender.send( preparator );

    }
}

class MimeSimpleMessage {

    @Inject
    private JavaMailSender sender;

    public void sendMassage(String someBody ) throws MessagingException {
        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper( mimeMessage, true );
        helper.setTo( "test@test.com" );
        helper.setText( someBody );
        helper.addAttachment( "fileName.jpg", new File( "c:/SimpleFile.jpg" ) );

        sender.send( mimeMessage );

        /*
            или
            использовать встроенные рессурсы, для отображения в сообщении
         */

        helper.setText("<html><body><img src='cid:identifier1234'></body></html>", true);
        helper.addInline("identifier1234", new File( "somePicture.jpg" ));
    }


}