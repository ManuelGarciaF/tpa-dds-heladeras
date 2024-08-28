package ar.edu.utn.frba.dds.dominio.notificacionesheladera;

import ar.edu.utn.frba.dds.dominio.Heladera;
import java.util.List;

public interface CriterioSeleccionHeladeras {
  void sort(Heladera heladeraBase, List<Heladera> heladeras);
}
