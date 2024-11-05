package ar.edu.utn.frba.dds.model.colaboraciones;

import ar.edu.utn.frba.dds.model.Colaborador;
import javax.persistence.Entity;

@Entity
public class RegistroDePersonaVulnerableHistorico extends Colaboracion {
  private static final Integer USOS_PROMEDIO = 2;
  private static final Integer MESES_ACTIVAS_PROMEDIO = 2;
  public static final Double COEFICIENTE_PUNTAJE = 2.0;

  private Integer cantidad;

  public RegistroDePersonaVulnerableHistorico(Integer cantidad) {
    this.cantidad = cantidad;
  }

  public RegistroDePersonaVulnerableHistorico() {
  }

  @Override
  public Double puntaje() {
    assert cantidad != null;
    return cantidad * USOS_PROMEDIO * MESES_ACTIVAS_PROMEDIO * COEFICIENTE_PUNTAJE;
  }

  @Override
  public boolean puedeSerRealizadaPor(Colaborador colaborador) {
    return true; // No tiene condiciones para ser realizada
  }

  public Integer cantidad() {
    return cantidad;
  }
}
