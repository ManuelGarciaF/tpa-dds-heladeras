package ar.edu.utn.frba.dds.colaboraciones;


import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

public class DonacionDeDinero implements Colaboracion {

  private LocalDate fecha;
  private Integer monto;
  private Boolean donacionPeriodica;
  private Integer frecuenciaEnDias;

  private static final Double COEFICIENTE_PUNTAJE = 0.5;

  public DonacionDeDinero(Integer monto,
                         Boolean donacionPeriodica,
                         Integer frecuenciaEnDias) {
    this.fecha = LocalDate.now();

    requireNonNull(monto);
    if (monto < 0) {
      throw new IllegalArgumentException("El monto de la donación no puede ser negativo");
    }

    this.monto = monto;
    this.donacionPeriodica = requireNonNull(donacionPeriodica);
    if (donacionPeriodica && frecuenciaEnDias == null) {
      throw new IllegalArgumentException(
          "La donacion es periodica pero no se indico la frecuencia");
    }
    this.frecuenciaEnDias = frecuenciaEnDias;
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
}