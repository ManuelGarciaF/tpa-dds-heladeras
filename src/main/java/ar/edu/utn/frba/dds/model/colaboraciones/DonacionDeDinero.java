package ar.edu.utn.frba.dds.model.colaboraciones;

import static java.util.Objects.requireNonNull;

import ar.edu.utn.frba.dds.model.Colaborador;
import java.time.LocalDate;
import javax.persistence.Entity;

@Entity
public class DonacionDeDinero extends Colaboracion {
  private LocalDate fecha;
  private Integer monto;
  private Boolean donacionPeriodica;
  private Integer frecuenciaEnDias;

  public static final Double COEFICIENTE_PUNTAJE = 0.5;

  public DonacionDeDinero(
      LocalDate fecha,
      Integer monto,
      Boolean donacionPeriodica,
      Integer frecuenciaEnDias) {
    requireNonNull(monto);
    if (monto < 0) {
      throw new IllegalArgumentException("El monto de la donación no puede ser negativo");
    }

    this.fecha = fecha;
    this.monto = monto;
    this.donacionPeriodica = requireNonNull(donacionPeriodica);
    if (donacionPeriodica && frecuenciaEnDias == null) {
      throw new IllegalArgumentException(
          "La donacion es periodica pero no se indico la frecuencia");
    }
    this.frecuenciaEnDias = frecuenciaEnDias;
  }

  public DonacionDeDinero() {
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public Integer getMonto() {
    return monto;
  }

  public Boolean getDonacionPeriodica() {
    return donacionPeriodica;
  }

  public Integer getFrecuenciaEnDias() {
    return frecuenciaEnDias;
  }

  @Override
  public Double puntaje() {
    return monto.doubleValue() * COEFICIENTE_PUNTAJE;
  }

  @Override
  public boolean puedeSerRealizadaPor(Colaborador colaborador) {
    return true; // No tiene condiciones para ser realizada
  }
}
