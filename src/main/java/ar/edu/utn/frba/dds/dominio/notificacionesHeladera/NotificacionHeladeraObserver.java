package ar.edu.utn.frba.dds.dominio.notificacionesHeladera;

import ar.edu.utn.frba.dds.dominio.*;
import ar.edu.utn.frba.dds.externo.InvalidTelephoneNumberException;

public interface NotificacionHeladeraObserver {
  void notificar(Heladera heladera) throws InvalidTelephoneNumberException;
}