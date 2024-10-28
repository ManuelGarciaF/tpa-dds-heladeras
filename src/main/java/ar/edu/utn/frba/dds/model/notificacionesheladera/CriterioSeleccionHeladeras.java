package ar.edu.utn.frba.dds.model.notificacionesheladera;

import ar.edu.utn.frba.dds.model.Heladera;
import java.util.List;

public interface CriterioSeleccionHeladeras {
  void sort(Heladera heladeraBase, List<Heladera> heladeras);
}
