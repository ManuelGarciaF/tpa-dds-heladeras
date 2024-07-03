package ar.edu.utn.frba.dds.dominio;

import java.time.LocalDate;

public record SolicitudTarjetaColaborador(
    ColaboradorHumano colaboradorHumano,
    LocalDate fechaSolicitud
) {
  public void asignarTarjeta(TarjetaColaborador tarjetaColaborador) {
    colaboradorHumano.setTarjetaColaborador(tarjetaColaborador);
  }
}
