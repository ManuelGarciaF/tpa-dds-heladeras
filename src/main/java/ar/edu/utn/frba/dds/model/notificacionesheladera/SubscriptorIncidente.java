package ar.edu.utn.frba.dds.model.notificacionesheladera;

import ar.edu.utn.frba.dds.PersistentEntity;
import ar.edu.utn.frba.dds.model.ColaboradorHumano;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class SubscriptorIncidente extends PersistentEntity {
  @ManyToOne
  private ColaboradorHumano colaboradorInteresado;

  public SubscriptorIncidente(ColaboradorHumano colaboradorInteresado) {
    this.colaboradorInteresado = colaboradorInteresado;
  }

  public SubscriptorIncidente() {
  }

  public ColaboradorHumano getColaboradorInteresado() {
    return colaboradorInteresado;
  }

  public void notificar(SugerenciaTrasladoDeViandas sugerenciaTrasladoDeViandas) {
    colaboradorInteresado.notificar("Se ha detectado un incidente en la heladera "
        + sugerenciaTrasladoDeViandas.getHeladeraRota().getNumeroDeSerie());
    colaboradorInteresado.agregarSugerenciaTrasladoDeViandas(sugerenciaTrasladoDeViandas);
  }
}
