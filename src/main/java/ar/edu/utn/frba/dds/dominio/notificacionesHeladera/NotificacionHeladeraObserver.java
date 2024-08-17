package ar.edu.utn.frba.dds.dominio.notificacionesHeladera;

import ar.edu.utn.frba.dds.dominio.Heladera;

public interface NotificacionHeladeraObserver {
  void notificar(Heladera heladera);
}