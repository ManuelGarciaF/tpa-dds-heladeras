package ar.edu.utn.frba.dds.model.notificacionesheladera;


import ar.edu.utn.frba.dds.externo.InstantMessageApp;
import ar.edu.utn.frba.dds.externo.InstantMessageSender;
import ar.edu.utn.frba.dds.model.ServiceLocator;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

@Entity
public class ProveedorMensajesInstantaneos extends ProveedorMensajeria {
  @Enumerated(EnumType.STRING)
  private InstantMessageApp provider;

  private String telefono;

  @Transient
  private InstantMessageSender instantMessageSender;

  public ProveedorMensajesInstantaneos(InstantMessageSender instantMessageSender,
                                       InstantMessageApp provider,
                                       String telefono) {
    this.instantMessageSender = instantMessageSender;
    this.provider = provider;
    this.telefono = telefono;
  }

  public ProveedorMensajesInstantaneos() {
  }

  @PostLoad
  public void postLoad() {
    instantMessageSender = ServiceLocator.getInstantMessageSender();
  }

  @Override
  public void enviarMensaje(String mensaje) {
    instantMessageSender.sendMessage(provider, telefono, mensaje);
  }
}
