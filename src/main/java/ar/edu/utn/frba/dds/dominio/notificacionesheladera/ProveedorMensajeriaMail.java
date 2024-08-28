package ar.edu.utn.frba.dds.dominio.notificacionesheladera;

import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

public class ProveedorMensajeriaMail implements ProveedorMensajeria {
  public static final String MAIL_ORIGEN = "sistemademanejodeheladeras@gmail.com";
  private final String mail;
  private final Mailer mailer;

  public ProveedorMensajeriaMail(String mail) {
    this.mail = mail;

    // Configurar el mailer
    mailer = MailerBuilder
        .withSMTPServer("smtp.gmail.com",
            587,
            MAIL_ORIGEN,
            System.getenv("MAIL_PASSWORD"))
        .buildMailer();
    mailer.testConnection();
  }

  @Override
  public void enviarMensaje(String mensaje) {
    Email email = EmailBuilder.startingBlank()
        .from(MAIL_ORIGEN)
        .to(mail)
        .withSubject("Notificación de heladera")
        .withPlainText(mensaje)
        .buildEmail();

    mailer.sendMail(email);
  }
}
