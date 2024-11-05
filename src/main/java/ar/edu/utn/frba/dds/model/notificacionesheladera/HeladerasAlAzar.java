package ar.edu.utn.frba.dds.model.notificacionesheladera;

import ar.edu.utn.frba.dds.model.Heladera;
import java.util.Collections;
import java.util.List;

public class HeladerasAlAzar implements CriterioSeleccionHeladeras {
  @Override
  public void sort(Heladera heladeraBase, List<Heladera> heladeras) {
    Collections.shuffle(heladeras);
  }
}
