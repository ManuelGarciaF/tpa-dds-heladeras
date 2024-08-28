package ar.edu.utn.frba.dds.dominio.notificacionesheladera;

import ar.edu.utn.frba.dds.dominio.Heladera;
import java.util.Collections;
import java.util.List;

public class HeladerasAlAzar implements CriterioSeleccionHeladeras {
  @Override
  public void sort(Heladera heladeraBase, List<Heladera> heladeras) {
    Collections.shuffle(heladeras);
  }
}
