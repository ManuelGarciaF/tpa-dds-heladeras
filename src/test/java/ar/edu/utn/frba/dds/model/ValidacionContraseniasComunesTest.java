package ar.edu.utn.frba.dds.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.edu.utn.frba.dds.model.validaciones.ValidacionContraseniasComunes;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ValidacionContraseniasComunesTest {
  private Random random;
  private ValidacionContraseniasComunes validacionContraseniasComunes;

  @BeforeEach
  void setUp() {
    random = new Random();
    validacionContraseniasComunes = new ValidacionContraseniasComunes("contraseniasComunes.txt");
  }

  @Test
  void fallaConContraseniaComun() {
    var contrasenia = "manu777";
    assertFalse(validacionContraseniasComunes.validar(contrasenia));
  }

  @Test
  void noFallaConContraseniaQueNoEstaEnElTop10K() {
    assertTrue(validacionContraseniasComunes.validar("95jxc9tnsk"));
  }
}