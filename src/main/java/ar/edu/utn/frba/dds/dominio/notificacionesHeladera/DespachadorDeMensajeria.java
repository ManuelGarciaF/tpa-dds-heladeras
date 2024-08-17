package ar.edu.utn.frba.dds.dominio.notificacionesHeladera;

import ar.edu.utn.frba.dds.exceptions.InvalidTelephoneNumberException;
import ar.edu.utn.frba.dds.externo.InstantMessageApp;

//Ver si se puede usar el enum, o hay que crear otro
public interface DespachadorDeMensajeria {
  void enviarMensaje(InstantMessageApp provider, String telephone, String message)
      throws InvalidTelephoneNumberException;
}
