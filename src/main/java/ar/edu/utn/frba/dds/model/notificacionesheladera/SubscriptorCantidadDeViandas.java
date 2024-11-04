package ar.edu.utn.frba.dds.model.notificacionesheladera;

import ar.edu.utn.frba.dds.PersistentEntity;
import ar.edu.utn.frba.dds.model.ColaboradorHumano;
import ar.edu.utn.frba.dds.model.Heladera;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class SubscriptorCantidadDeViandas extends PersistentEntity {
  private Integer cantidadMinima;
  @ManyToOne
  private ColaboradorHumano colaboradorInteresado;

  public SubscriptorCantidadDeViandas(
      ColaboradorHumano colaboradorInteresado,
      Integer cantidadMinima) {
    this.colaboradorInteresado = colaboradorInteresado;
    this.cantidadMinima = cantidadMinima;
  }

  public SubscriptorCantidadDeViandas() {

  }

  public ColaboradorHumano getColaboradorInteresado() {
    return colaboradorInteresado;
  }

  public Integer getCantidadMinima() {
    return cantidadMinima;
  }

  public void notificar(Heladera heladera) {
    if (heladera.getCantidadDeViandas() <= cantidadMinima) {
      colaboradorInteresado.notificar("Solo quedan " + heladera.getCantidadDeViandas()
          + " viandas en la heladera " + heladera.getNumeroDeSerie() + ".");
    }
  }
}