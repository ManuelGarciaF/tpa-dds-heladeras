package ar.edu.utn.frba.dds.dominio.incidentes;

import ar.edu.utn.frba.dds.dominio.Ubicacion;
import java.time.LocalDateTime;

public class AlertaFallaConexion implements Incidente {
  private LocalDateTime fecha;
  private TipoDeFalla tipo;
  private Ubicacion ubicacionDelIncidente;

  // CONSTRUCTOR
  public AlertaFallaConexion(LocalDateTime fecha, TipoDeFalla tipo, Ubicacion ubicacionDelIncidente) {
    this.fecha = fecha;
    this.tipo = tipo;
    this.ubicacionDelIncidente = ubicacionDelIncidente;
  }

  @Override
  public LocalDateTime getFecha() {
    return this.fecha;
  }

  public Ubicacion getUbicacionDelIncidente() {
    return ubicacionDelIncidente;
  }

  @Override
  public String getDescripcionDelError() {
    return switch (this.tipo) {
      case SENSOR_DE_PESO ->
          "El sensor de peso de la heladera no esta funcionando correctamente.";
      case SENSOR_DE_TEMPERATURA ->
          "El sensor de temperatura de la heladera no esta funcionando correctamente.";
    };
  }
}
