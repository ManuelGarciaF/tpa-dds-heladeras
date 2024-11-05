package ar.edu.utn.frba.dds.model.notificacionesheladera;

import ar.edu.utn.frba.dds.model.Heladera;
import java.util.Comparator;
import java.util.List;

public class HeladerasMasVacias implements CriterioSeleccionHeladeras {
  @Override
  public void sort(Heladera heladeraBase, List<Heladera> heladeras) {
    heladeras.sort(Comparator.comparingDouble(Heladera::espacioRestante));
  }
}
