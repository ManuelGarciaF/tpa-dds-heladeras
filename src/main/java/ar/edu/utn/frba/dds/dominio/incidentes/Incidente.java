package ar.edu.utn.frba.dds.dominio.incidentes;

import ar.edu.utn.frba.dds.dominio.Heladera;
import java.time.LocalDate;

public interface Incidente {

  public LocalDate getFecha();

  public Heladera getHeladera();
}
