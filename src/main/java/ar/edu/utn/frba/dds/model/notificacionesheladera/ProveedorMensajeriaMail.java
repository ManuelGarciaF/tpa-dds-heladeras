package ar.edu.utn.frba.dds.model.notificacionesheladera;

import javax.persistence.Entity;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

@Entity
public class ProveedorMensajeriaMail extends ProveedorMensajeria {
  public static final String MAIL_ORIGEN = "sistemademanejodeheladeras@gmail.com";

  private String mail;

  @Transient
  private Mailer mailer;

  public ProveedorMensajeriaMail(String mail) {
    this.mail = mail;

    configurarMailer();
  }

  public ProveedorMensajeriaMail() {
  }

  @PostLoad
  public void postLoad() {
    configurarMailer();
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

  private void configurarMailer() {
    // Configurar el mailer
    mailer = MailerBuilder
        .withSMTPServer("smtp.gmail.com",
            587,
            MAIL_ORIGEN,
            System.getenv("MAIL_PASSWORD"))
        .buildMailer();
    mailer.testConnection();
  }
}
