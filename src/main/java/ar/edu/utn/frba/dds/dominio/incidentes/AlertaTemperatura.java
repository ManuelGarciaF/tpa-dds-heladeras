package ar.edu.utn.frba.dds.dominio.incidentes;

import java.time.LocalDateTime;

public class AlertaTemperatura extends Incidente {
  public AlertaTemperatura(LocalDateTime fecha) {
    super(fecha);
  }

  @Override
  public String getDescripcionDelError() {
    return "La temperatura de la heladera salio fuera de los limites permitidos.";
  }
}
