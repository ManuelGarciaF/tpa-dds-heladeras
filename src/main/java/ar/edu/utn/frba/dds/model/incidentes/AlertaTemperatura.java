package ar.edu.utn.frba.dds.model.incidentes;

import java.time.LocalDateTime;
import javax.persistence.Entity;

@Entity
public class AlertaTemperatura extends Incidente {
  public AlertaTemperatura(LocalDateTime fecha) {
    super(fecha);
  }

  public AlertaTemperatura() {
  }

  @Override
  public String getDescripcionDelError() {
    return "La temperatura de la heladera salio fuera de los limites permitidos.";
  }
}
