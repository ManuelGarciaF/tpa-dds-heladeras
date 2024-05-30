package ar.edu.utn.frba.dds.dominio.colaboraciones;

import static java.util.Objects.requireNonNull;

public class DonacionDeViandaHistorica implements Colaboracion {
  private Integer cantidad;

  private static final Integer SEMANAS_FRESCAS_PROMEDIO = 2;
  private static final Double COEFICIENTE_PUNTAJE = 1.5;

  public DonacionDeViandaHistorica(Integer cantidad) {
    this.cantidad = requireNonNull(cantidad);
  }

  @Override
  public Double puntaje() {
    return cantidad * SEMANAS_FRESCAS_PROMEDIO * COEFICIENTE_PUNTAJE;
  }
}
