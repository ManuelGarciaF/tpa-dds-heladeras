package ar.edu.utn.frba.dds.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UsuarioTest {
  @Test
  void noVerificaContraseniaIncorrecta() {
    var mati = new Usuario("fasdfdasff192431", "mati");
    assertFalse(mati.verificarContrasenia("mati123"));
  }

  @Test
  void VerificaContraseniaCorrecta() {
    var mati = new Usuario("fasdfdasff192431", "mati");
    assertTrue(mati.verificarContrasenia("fasdfdasff192431"));
  }
}