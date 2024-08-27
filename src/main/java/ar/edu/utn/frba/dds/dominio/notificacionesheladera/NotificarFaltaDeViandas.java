package ar.edu.utn.frba.dds.dominio.notificacionesheladera;

import ar.edu.utn.frba.dds.dominio.ColaboradorHumano;
import ar.edu.utn.frba.dds.dominio.Heladera;

public class NotificarFaltaDeViandas implements SubscriptorCantidadDeViandas {
  private final ColaboradorHumano colaboradorInteresado;
  private final Integer cantidadSeteada;

  public NotificarFaltaDeViandas(ColaboradorHumano colaboradorInteresado, Integer cantidadSeteada) {
    this.colaboradorInteresado = colaboradorInteresado;
    this.cantidadSeteada = cantidadSeteada;
  }

  @Override
  public void notificar(Heladera heladera) {
    if (heladera.getCantidadDeViandas() <= cantidadSeteada) {
      colaboradorInteresado.notificar("Solo quedan " + heladera.getCantidadDeViandas()
          + " viandas en la heladera " + heladera.getNumeroDeSerie() + ".");
    }
  }
}
