package ar.edu.utn.frba.dds.dominio.incidentes;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class MedicionDeTemperatura {
  private double temperatura;
  private OffsetDateTime fecha;

  void MedicionDeTemperatura(double temperatura) {
    this.temperatura = temperatura;
    fecha = OffsetDateTime.now();
  }

  public double getTemperatura() {
    return temperatura;
  }

  public OffsetDateTime getFecha() {
    return fecha;
  }
}
