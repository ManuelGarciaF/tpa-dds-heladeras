package ar.edu.utn.frba.dds.model.incidentes;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class AlertaFallaConexion extends Incidente {
  @Enumerated(EnumType.STRING)
  private TipoDeFalla tipo;

  public AlertaFallaConexion(LocalDateTime fecha, TipoDeFalla tipo) {
    super(fecha);
    this.tipo = tipo;
  }

  public AlertaFallaConexion() {
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

  @Override
  public String getTitulo() {
    return "Falla de conexión";
  }

  @Override
  public TipoIncidente getTipo() {
    return TipoIncidente.ALERTA_FALLA_CONEXION;
  }
}
