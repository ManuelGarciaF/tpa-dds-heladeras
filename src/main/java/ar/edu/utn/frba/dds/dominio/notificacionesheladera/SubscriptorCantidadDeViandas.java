package ar.edu.utn.frba.dds.dominio.notificacionesheladera;

import ar.edu.utn.frba.dds.PersistentEntity;
import ar.edu.utn.frba.dds.dominio.ColaboradorHumano;
import ar.edu.utn.frba.dds.dominio.Heladera;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class SubscriptorCantidadDeViandas extends PersistentEntity {
  private Integer cantidadMinima;
  @ManyToOne
  private ColaboradorHumano colaboradorInteresado;

  public SubscriptorCantidadDeViandas(ColaboradorHumano colaboradorInteresado, Integer cantidadMinima) {
    this.colaboradorInteresado = colaboradorInteresado;
    this.cantidadMinima = cantidadMinima;
  }

  public SubscriptorCantidadDeViandas() {

  }

  public void notificar(Heladera heladera) {
    if (heladera.getCantidadDeViandas() <= cantidadMinima) {
      colaboradorInteresado.notificar("Solo quedan " + heladera.getCantidadDeViandas()
          + " viandas en la heladera " + heladera.getNumeroDeSerie() + ".");
    }
  }
}