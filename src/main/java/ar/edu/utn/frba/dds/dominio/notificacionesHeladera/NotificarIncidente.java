package ar.edu.utn.frba.dds.dominio.notificacionesHeladera;

import ar.edu.utn.frba.dds.dominio.ColaboradorHumano;
import ar.edu.utn.frba.dds.dominio.Heladera;

public class NotificarIncidente implements NotificacionHeladeraObserver{

  private ColaboradorHumano colaboradorInteresado;
  //crear algo para las opciones



  @Override
  public void notificar(Heladera heladera) {
    //crear notificacion
    if(heladera.estaActiva()){
      //crear algoritmo de notificacion
    }
  }
}
