package ar.edu.utn.frba.dds.dominio.colaboraciones;

import ar.edu.utn.frba.dds.dominio.Colaborador;

public record DonacionDeViandaHistorica(Integer cantidad) implements Colaboracion {
  private static final Integer SEMANAS_FRESCAS_PROMEDIO = 2;
  public static final Double COEFICIENTE_PUNTAJE = 1.5;

  @Override
  public Double puntaje() {
    return cantidad * SEMANAS_FRESCAS_PROMEDIO * COEFICIENTE_PUNTAJE;
  }

  @Override
  public boolean puedeSerRealizadaPor(Colaborador colaborador) {
    return true; // No tiene condiciones para ser realizada
  }
}
