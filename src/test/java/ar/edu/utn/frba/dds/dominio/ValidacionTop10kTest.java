package ar.edu.utn.frba.dds.dominio;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.edu.utn.frba.dds.dominio.validaciones.ValidacionTop10k;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
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
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream("10k-most-common.txt");
    if (inputStream == null) {
      throw new RuntimeException("No se pudo abrir el archivo de contrasenias");
    }
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    return reader.lines().toList().get(random.nextInt(10001));
  }
}