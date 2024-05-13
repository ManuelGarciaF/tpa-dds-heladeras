package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ValidacionTop10kTest {
  private Random random;
  private ValidacionTop10k validacionTop10k;

  @BeforeEach
  void setUp() {
    random = new Random();
    validacionTop10k = new ValidacionTop10k();
  }

  @Test
  void fallaConContraseniaComun() {
    var contrasenia = obtenerContraseniaTop10k();
    assertFalse(validacionTop10k.validar(contrasenia));
  }

  @Test
  void noFallaConContraseniaQueNoEstaEnElTop10K() {
    assertTrue(validacionTop10k.validar("95jxc9tnsk"));
  }

  private String obtenerContraseniaTop10k() {
    try {
      // Obtener contrasenia random del archivo
      return Files.lines(Paths.get("./resources/10k-most-common.txt"))
          .toList().get(random.nextInt(10001));
    } catch (IOException e) {
      throw new RuntimeException("Hubo un error al leer el archivo de contrasenias");
    }
  }
}