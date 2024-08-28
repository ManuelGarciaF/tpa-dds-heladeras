package ar.edu.utn.frba.dds.dominio.notificacionesheladera;

import ar.edu.utn.frba.dds.dominio.Heladera;
import java.util.List;

public class HeladerasMasCercanas implements CriterioSeleccionHeladeras {
  @Override
  public void sort(Heladera heladeraBase, List<Heladera> heladeras) {
    heladeras.sort((h1, h2) -> Double.compare(
        h2.distanciaA(heladeraBase), h1.distanciaA(heladeraBase)
    ));
  }
}
