package ar.edu.utn.frba.dds;


import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.Date;

public class DonacionDeDinero implements ColaboracionHumana, ColaboracionJuridica {

  private LocalDate fecha;
  private Integer monto;
  private Boolean donacionPeriodica;
  private Integer frecuenciaEnDias;

  public DonacionDeDinero(LocalDate fecha, Integer monto, Boolean donacionPeriodica, Integer frecuenciaEnDias) {
    this.fecha = requireNonNull(fecha);

    requireNonNull(monto);
    if (monto < 0) {
      throw new IllegalArgumentException("El monto de la donación no puede ser negativo");
    }

    this.monto = monto;
    this.donacionPeriodica = requireNonNull(donacionPeriodica);
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
  public void realizarColaboracion() {
    // Por ahora no hace nada solo queda registrada en el historial de colaboraciones.
  }
}
