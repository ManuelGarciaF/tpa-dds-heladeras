package ar.edu.utn.frba.dds.dominio.incidentes;


import ar.edu.utn.frba.dds.dominio.Heladera;
import ar.edu.utn.frba.dds.dominio.Ubicacion;
import java.time.LocalDateTime;

public class AlertaTemperatura implements Incidente {
  private LocalDateTime fecha;
  private Ubicacion ubicacionDelIncidente;

  //CONSTRUCTOR
  public AlertaTemperatura(LocalDateTime fecha, Ubicacion ubicacionDelIncidente) {
    this.fecha = fecha;
    this.ubicacionDelIncidente = ubicacionDelIncidente;
  }

  public Ubicacion getUbicacionDelIncidente() {
    return ubicacionDelIncidente;
  }

  @Override
  public LocalDateTime getFecha() {
    return fecha;
  }

  @Override
  public String getDescripcion() {
    return "La temperatura de la heladera salio afuera de los limites permitidos.";
  }
}
