package ar.edu.utn.frba.dds.dominio.notificacionesheladera;

import ar.edu.utn.frba.dds.dominio.ColaboradorHumano;
import ar.edu.utn.frba.dds.dominio.Heladera;

public class NotificarFaltaDeViandas implements SubscriptorCantidadDeViandas {
  private final ColaboradorHumano colaboradorInteresado;
  private final Integer cantidadMinima;

  public NotificarFaltaDeViandas(ColaboradorHumano colaboradorInteresado, Integer cantidadMinima) {
    this.colaboradorInteresado = colaboradorInteresado;
    this.cantidadMinima = cantidadMinima;
  }

  @Override
  public void notificar(Heladera heladera) {
    if (heladera.getCantidadDeViandas() <= cantidadMinima) {
      colaboradorInteresado.notificar("Solo quedan " + heladera.getCantidadDeViandas()
          + " viandas en la heladera " + heladera.getNumeroDeSerie() + ".");
    }
  }
}
