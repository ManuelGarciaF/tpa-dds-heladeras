/*package ar.edu.utn.frba.dds.dominio.notificacionesHeladera;

import static ar.edu.utn.frba.dds.Main.despachadorDeMensajes;

import ar.edu.utn.frba.dds.dominio.*;
import ar.edu.utn.frba.dds.externo.InstantMessageApp;
import ar.edu.utn.frba.dds.externo.InvalidTelephoneNumberException;

public class NotificarFaltaDeViandas implements NotificacionHeladeraObserver {

  private Integer viandasDisponibles;
  private Colaborador colaborador;
  private InstantMessageApp metodoDeEnvio;

  public NotificarFaltaDeViandas(Integer viandasDisponibles, Colaborador colaborador, InstantMessageApp metodoDeEnvio) {
    this.viandasDisponibles = viandasDisponibles;
    this.colaborador = colaborador;
    this.metodoDeEnvio = metodoDeEnvio;
  }


  @Override
  public void notificar(Heladera heladera){
    if(heladera.cantidadDeViandas() <= viandasDisponibles){
      despachadorDeMensajes.enviarMensaje(metodoDeEnvio, colaborador.getMedioDeContacto().getTelefono(), "Faltan viandas");
    }
  }
}*/
