package ar.edu.utn.frba.dds;

import java.util.ArrayList;
import java.util.List;

public class MapaHeladeras {
  private List<Heladera> heladeras;
  private static final MapaHeladeras INSTANCE = new MapaHeladeras();

  public static MapaHeladeras instance() {
    return INSTANCE;
  }

  private MapaHeladeras() {
    this.heladeras = new ArrayList<>();
  }

  public List<Heladera> listarHeladeras() {
    return heladeras;
  }

  public void agregarHeladera(Heladera heladera) {
    this.heladeras.add(heladera);
  }

  public void quitarHeladera(Heladera heladera) {
    this.heladeras.remove(heladera);
  }

  public Heladera buscarHeladera(String nombreHeladera) {
    return heladeras.stream()
        .filter(heladera -> heladera.tieneNombre(nombreHeladera))
        .findFirst()
        .orElse(null);
  }
}
