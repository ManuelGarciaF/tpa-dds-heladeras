package ar.edu.utn.frba.dds.dominio;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class AperturaHeladera {
  private static final Integer HORAS_MAXIMAS_APERTURA = 3;

  private final ColaboradorHumano colaboradorHumano;
  private final LocalDateTime fechaSolicitud;
  private LocalDateTime fechaApertura;

  public AperturaHeladera(ColaboradorHumano colaboradorHumano, LocalDateTime fechaSolicitud) {
    if (colaboradorHumano == null || colaboradorHumano.getTarjetaColaborador() == null) {
      throw new IllegalArgumentException(
          "El colaborador humano debe tener una tarjeta de colaborador asociada"
      );
    }
    this.colaboradorHumano = colaboradorHumano;
    this.fechaSolicitud = fechaSolicitud;
  }

  public void setFechaApertura(LocalDateTime fechaApertura) {
    this.fechaApertura = fechaApertura;
  }

  public ColaboradorHumano getColaboradorHumano() {
    return colaboradorHumano;
  }

  public TarjetaColaborador getTarjetaColaborador() {
    return colaboradorHumano.getTarjetaColaborador();
  }

  public boolean esValida(LocalDateTime fechaApertura) {
    return ChronoUnit.HOURS.between(fechaSolicitud, fechaApertura) <= HORAS_MAXIMAS_APERTURA;
  }

  public boolean esValidaAhora() {
    return esValida(LocalDateTime.now());
  }
}
