package ar.edu.utn.frba.dds.dominio.notificacionesHeladera;


import ar.edu.utn.frba.dds.externo.InstantMessageApp;
import ar.edu.utn.frba.dds.externo.InstantMessageSender;
import ar.edu.utn.frba.dds.externo.InvalidTelephoneNumberException;

public class DespachadorDeMensajes implements DespachadorDeMensajeria{
  private InstantMessageSender instantMessageSender;

  public void enviarMensaje(InstantMessageApp provider, String telephone, String message)  {
    instantMessageSender.sendMessage(provider, telephone, message);
  }
}
