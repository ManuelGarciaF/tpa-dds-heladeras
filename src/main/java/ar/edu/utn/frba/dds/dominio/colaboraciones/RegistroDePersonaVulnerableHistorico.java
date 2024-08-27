package ar.edu.utn.frba.dds.dominio.colaboraciones;

import ar.edu.utn.frba.dds.dominio.Colaborador;

public record RegistroDePersonaVulnerableHistorico(Integer cantidad) implements Colaboracion {
  private static final Integer USOS_PROMEDIO = 2;
  private static final Integer MESES_ACTIVAS_PROMEDIO = 2;
  public static final Double COEFICIENTE_PUNTAJE = 2.0;

  @Override
  public Double puntaje() {
    return cantidad * USOS_PROMEDIO * MESES_ACTIVAS_PROMEDIO * COEFICIENTE_PUNTAJE;
  }

  @Override
  public boolean puedeSerRealizadaPor(Colaborador colaborador) {
    return true; // No tiene condiciones para ser realizada
  }
}
