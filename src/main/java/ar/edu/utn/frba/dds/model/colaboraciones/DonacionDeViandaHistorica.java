package ar.edu.utn.frba.dds.model.colaboraciones;

import ar.edu.utn.frba.dds.model.Colaborador;
import javax.persistence.Entity;

@Entity
public class DonacionDeViandaHistorica extends Colaboracion {
  private static final Integer SEMANAS_FRESCAS_PROMEDIO = 2;
  public static final Double COEFICIENTE_PUNTAJE = 1.5;

  private Integer cantidad;

  public DonacionDeViandaHistorica(Integer cantidad) {
    this.cantidad = cantidad;
  }

  public DonacionDeViandaHistorica() {
  }

  @Override
  public Double puntaje() {
    assert cantidad != null;
    return cantidad * SEMANAS_FRESCAS_PROMEDIO * COEFICIENTE_PUNTAJE;
  }

  @Override
  public boolean puedeSerRealizadaPor(Colaborador colaborador) {
    return true; // No tiene condiciones para ser realizada
  }

  public Integer cantidad() {
    return cantidad;
  }
}
