package ar.edu.utn.frba.dds.dominio.colaboraciones;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DonacionDeDineroTest {
  private DonacionDeDinero donacionDeDinero;

  @BeforeEach
  void setUp() {
    donacionDeDinero = new DonacionDeDinero( 500, false, null );
  }

  @Test
  void elPuntajeSeCalculaCorrectamente() {
    // [DINERO_DONADO] * 0,5 = 500 * 0,5 = 250
    assertEquals(250.0, donacionDeDinero.puntaje(), 0);
  }
}