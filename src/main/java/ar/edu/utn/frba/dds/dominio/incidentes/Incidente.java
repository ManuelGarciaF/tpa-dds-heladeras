package ar.edu.utn.frba.dds.dominio.incidentes;

import ar.edu.utn.frba.dds.dominio.Heladera;
import ar.edu.utn.frba.dds.dominio.Ubicacion;
import java.time.LocalDateTime;


public interface Incidente {
  LocalDateTime getFecha();

  String getDescripcionDelError();
  Ubicacion getUbicacionDelIncidente();
}
