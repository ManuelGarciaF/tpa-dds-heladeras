package ar.edu.utn.frba.dds.dominio.notificacionesHeladera;

import ar.edu.utn.frba.dds.dominio.Heladera;
import java.util.Comparator;

public class CriteriosComparacionHeladera {
  public static Comparator<Heladera> masCercana(Heladera heladeraBase) {
    return (h1, h2) -> Double.compare(
      h2.distanciaA(heladeraBase), h1.distanciaA(heladeraBase)
    );
  }

  public static Comparator<Heladera> masVacia() {
    return Comparator.comparingDouble(Heladera::espacioRestante);
  }

  public static Comparator<Heladera> alAzar() {
    return (h1, h2) -> Math.random() < 0.5 ? -1 : 1;
  }
}
