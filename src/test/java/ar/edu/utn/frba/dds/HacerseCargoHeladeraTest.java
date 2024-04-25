package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HacerseCargoHeladeraTest {
  @Test
  void agregaLaHeladeraAlMapa() {
    var colaboracion = new HacerseCargoHeladera(
        "dfdasfs",
        584,
        new Ubicacion(-4.3489, 58.92340924));
    int numAntes = MapaHeladeras.instance().listarHeladeras().size();
    colaboracion.realizarColaboracion();
    assertEquals(numAntes + 1, MapaHeladeras.instance().listarHeladeras().size());
  }
}