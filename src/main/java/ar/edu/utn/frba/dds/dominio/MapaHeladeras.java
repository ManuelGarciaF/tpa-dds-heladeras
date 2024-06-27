package ar.edu.utn.frba.dds.dominio;

import java.util.ArrayList;
import java.util.List;

public class MapaHeladeras {
  private final List<Heladera> heladeras;

  public MapaHeladeras() {
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

  public List<UsoTarjetaPersonaVulnerable> encontrarUsosDeTarjeta(String codigotarjeta) {
    return heladeras.stream()
        .flatMap(heladera -> heladera.usosDeTarjeta(codigotarjeta).stream())
        .toList();
  }
}
