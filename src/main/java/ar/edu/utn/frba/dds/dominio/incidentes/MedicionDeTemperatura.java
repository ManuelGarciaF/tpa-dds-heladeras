package ar.edu.utn.frba.dds.dominio.incidentes;

import java.time.LocalDate;

public class MedicionDeTemperatura {
  private double temperatura;
  private LocalDate fecha;

  void MedicionDeTemperatura(double temperatura) {
    this.temperatura = temperatura;
    fecha = LocalDate.now();
  }

  public double getTemperatura() {
    return temperatura;
  }

  public LocalDate getFecha() {
    return fecha;
  }
}
