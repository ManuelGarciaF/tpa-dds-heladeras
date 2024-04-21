package ar.edu.utn.frba.dds;


import static java.util.Objects.requireNonNull;

import java.util.Date;

public class DonacionDeDinero implements ColaboracionHumana {

  private Date fecha;
  private Integer monto;
  private Boolean donacionPeriodica;
  private Integer frecuenciaEnDias;

  public DonacionDeDinero(Date fecha, Integer monto, Boolean donacionPeriodica, Integer frecuenciaEnDias) {
    this.fecha = requireNonNull(fecha);

    requireNonNull(monto);
    if (monto < 0) {
      throw new IllegalArgumentException("El monto de la donación no puede ser negativo");
    }

    this.monto = monto;
    this.donacionPeriodica = requireNonNull(donacionPeriodica);
    this.frecuenciaEnDias = frecuenciaEnDias;
  }

  @Override
  public void realizarColaboracion() {
    // Por ahora no hace nada solo queda registrada en el historial de colaboraciones.
  }
}
