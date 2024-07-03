package ar.edu.utn.frba.dds.dominio.incidentes;

import java.time.LocalDateTime;

public class AlertaFallaConexion implements Incidente {
  private LocalDateTime fecha;
  private TipoDeFalla tipo;

  // CONSTRUCTOR
  public AlertaFallaConexion(LocalDateTime fecha, TipoDeFalla tipo) {
    this.fecha = fecha;
    this.tipo = tipo;
  }

  @Override
  public LocalDateTime getFecha() {
    return this.fecha;
  }

  @Override
  public String getDescripcion() {
    return switch (this.tipo) {
      case SENSOR_DE_PESO ->
          "El sensor de peso de la heladera no esta funcionando correctamente.";
      case SENSOR_DE_TEMPERATURA ->
          "El sensor de temperatura de la heladera no esta funcionando correctamente.";
    };
  }
}
