package ar.edu.utn.frba.dds.colaboraciones;

import static java.util.Objects.requireNonNull;

public class RegistroDePersonaVulnerableHistorico implements Colaboracion {
  private Integer cantidad;

  private static final Integer USOS_PROMEDIO = 2;
  private static final Integer MESES_ACTIVAS_PROMEDIO = 2;
  private static final Double COEFICIENTE_PUNTAJE = 2.0;

  public RegistroDePersonaVulnerableHistorico(Integer cantidad) {
    this.cantidad = requireNonNull(cantidad);
  }

  @Override
  public Double puntaje() {
    return cantidad * USOS_PROMEDIO * MESES_ACTIVAS_PROMEDIO * COEFICIENTE_PUNTAJE;
  }
}
