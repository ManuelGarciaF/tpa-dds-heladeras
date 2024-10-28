package ar.edu.utn.frba.dds.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class AperturaHeladera {
  private static final Integer HORAS_MAXIMAS_APERTURA = 3;

  @ManyToOne
  private ColaboradorHumano colaboradorHumano;

  private LocalDateTime fechaSolicitud;
  private LocalDateTime fechaApertura;

  private boolean realizada = false;

  public AperturaHeladera(ColaboradorHumano colaboradorHumano, LocalDateTime fechaSolicitud) {
    if (colaboradorHumano == null || colaboradorHumano.getTarjetaColaborador() == null) {
      throw new IllegalArgumentException(
          "El colaborador humano debe tener una tarjeta de colaborador asociada"
      );
    }
    this.colaboradorHumano = colaboradorHumano;
    this.fechaSolicitud = fechaSolicitud;
  }

  public AperturaHeladera() {
  }

  public ColaboradorHumano getColaboradorHumano() {
    return colaboradorHumano;
  }

  public TarjetaColaborador getTarjetaColaborador() {
    return colaboradorHumano.getTarjetaColaborador();
  }

  public boolean esValida(LocalDateTime fechaApertura) {
    return ChronoUnit.HOURS.between(fechaSolicitud, fechaApertura) <= HORAS_MAXIMAS_APERTURA
        && !realizada;
  }

  public void realizar(LocalDateTime fechaApertura) {
    if (esValidaAhora()) {
      this.fechaApertura = fechaApertura;
      this.realizada = true;
    } else {
      throw new IllegalStateException("La apertura no es válida en este momento");
    }
  }

  public boolean esValidaAhora() {
    return esValida(LocalDateTime.now());
  }

  public LocalDateTime getFechaApertura() {
    return fechaApertura;
  }

  public boolean isRealizada() {
    return realizada;
  }
}
