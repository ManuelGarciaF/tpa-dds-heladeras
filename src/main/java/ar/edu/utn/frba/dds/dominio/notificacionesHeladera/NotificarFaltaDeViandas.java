package ar.edu.utn.frba.dds.dominio.notificacionesHeladera;

import ar.edu.utn.frba.dds.dominio.Colaborador;
import ar.edu.utn.frba.dds.dominio.ColaboradorHumano;
import ar.edu.utn.frba.dds.dominio.Heladera;
import ar.edu.utn.frba.dds.externo.InstantMessageApp;

public class NotificarFaltaDeViandas implements NotificacionHeladeraObserver {

  //cambiar el colaboradorhumano por colaborador despues
  private final ColaboradorHumano colaboradorInteresado;
  private final Integer cantidadSeteada;

  public NotificarFaltaDeViandas(ColaboradorHumano colaboradorInteresado, Integer cantidadSeteada) {
    this.colaboradorInteresado = colaboradorInteresado;
    this.cantidadSeteada = cantidadSeteada;
  }

  @Override
  public void notificar(Heladera heladera){
    if(heladera.getCantidadDeViandas() <= cantidadSeteada){
      //Aca tendriamos que notificar por mail, pero con motivos de testing voy a hacer que se cargue en una lista
      colaboradorInteresado.agregarAlerta("hey");
    }
  }
}
