package ar.edu.utn.frba.dds.dominio.colaboraciones;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ar.edu.utn.frba.dds.dominio.Heladera;
import ar.edu.utn.frba.dds.dominio.MapaHeladeras;
import ar.edu.utn.frba.dds.dominio.Ubicacion;

class HacerseCargoHeladeraTest {
  private HacerseCargoHeladera hacerseCargoHeladera;
  private MapaHeladeras mapaHeladeras;
  private Heladera heladera;

  @BeforeEach
  void setUp() {
    mapaHeladeras = mock(MapaHeladeras.class);
    heladera = mock(Heladera.class);
    hacerseCargoHeladera = new HacerseCargoHeladera("Heladera1",
        10,
        new Ubicacion(0.0, 0.0),
        mapaHeladeras);
  }

  @Test
  void elPuntajeSeCalculaCorrectamente() {
    when(mapaHeladeras.buscarHeladera("Heladera1")).thenReturn(heladera);
    when(heladera.mesesActivos()).thenReturn(2.0);
    when(heladera.getUsos()).thenReturn(13);

    // El puntaje de hacerse cargo de una heladera debe ser mesesActivos * usos * 5
    assertEquals(130.0, hacerseCargoHeladera.puntaje(), 0);
  }
}