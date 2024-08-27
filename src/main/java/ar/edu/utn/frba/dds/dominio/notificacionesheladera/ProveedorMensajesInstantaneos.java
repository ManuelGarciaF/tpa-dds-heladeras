package ar.edu.utn.frba.dds.dominio.notificacionesheladera;


import ar.edu.utn.frba.dds.externo.InstantMessageApp;
import ar.edu.utn.frba.dds.externo.InstantMessageSender;

public class ProveedorMensajesInstantaneos implements ProveedorMensajeria {
  private final InstantMessageSender instantMessageSender;
  private final InstantMessageApp provider;
  private final String telefono;

  public ProveedorMensajesInstantaneos(InstantMessageSender instantMessageSender,
                                       InstantMessageApp provider,
                                       String telefono) {
    this.instantMessageSender = instantMessageSender;
    this.provider = provider;
    this.telefono = telefono;
  }

  @Override
  public void enviarMensaje(String mensaje) {
    instantMessageSender.sendMessage(provider, telefono, mensaje);
  }
}
