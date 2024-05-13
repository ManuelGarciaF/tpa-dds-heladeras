package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ValidacionLongitudTest {

  private ValidacionLongitud validacionLongitud;

  @BeforeEach
  void setUp() {
    validacionLongitud = new ValidacionLongitud(8);
  }

  @Test
  void fallaConContraseniaCorta() {
    assertFalse(validacionLongitud.validar("132"));
  }

  @Test
  void noFallaConContraseniaLarga() {
    assertTrue(validacionLongitud.validar("contraseniaMuyLargaYMuySegura"));
  }
}