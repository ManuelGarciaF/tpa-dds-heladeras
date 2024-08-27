package ar.edu.utn.frba.dds.dominio;

import static java.util.Objects.requireNonNull;

import ar.edu.utn.frba.dds.externo.ControladorDeAcceso;

public class AutorizadorAperturasActual implements AutorizadorAperturas {
  ControladorDeAcceso api;

  public AutorizadorAperturasActual(ControladorDeAcceso api) {
    this.api = requireNonNull(api);
  }

  @Override
  public void habilitarTarjeta(TarjetaColaborador tarjetaColaborador) {
    api.notificarTarjetasColaboradoraHabilitada(tarjetaColaborador.codigoTarjeta());
  }
}
