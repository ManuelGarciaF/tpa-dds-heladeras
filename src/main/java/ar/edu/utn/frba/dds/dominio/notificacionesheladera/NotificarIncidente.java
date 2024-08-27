package ar.edu.utn.frba.dds.dominio.notificacionesheladera;

import ar.edu.utn.frba.dds.dominio.ColaboradorHumano;

public class NotificarIncidente implements SubscriptorIncidente {
  private final ColaboradorHumano colaboradorInteresado;

  public NotificarIncidente(ColaboradorHumano colaboradorInteresado) {
    this.colaboradorInteresado = colaboradorInteresado;
  }

  @Override
  public void notificar(SugerenciaTrasladoDeViandas sugerenciaTrasladoDeViandas) {
    colaboradorInteresado.notificar("Se ha detectado un incidente en la heladera "
        + sugerenciaTrasladoDeViandas.getHeladeraRota().getNumeroDeSerie());
    colaboradorInteresado.agregarSugerenciaTrasladoDeViandas(sugerenciaTrasladoDeViandas);
  }
}
