package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UsuarioTest {
  @Test
  void fallaConContraseniaComun() {
    assertThrows(ContraseniaInseguraException.class, () -> {
      new Usuario("123456789", "mati");
    });
  }

  @Test
  void fallaConContraseniaCorta() {
    assertThrows(ContraseniaInseguraException.class, () -> {
      new Usuario("5/sIj*", "mati");
    });
  }

  @Test
  void noFallaConContraseniaSegura() {
    assertDoesNotThrow(() -> {
      new Usuario("fasdfdasff192431", "mati");
    });
  }
}