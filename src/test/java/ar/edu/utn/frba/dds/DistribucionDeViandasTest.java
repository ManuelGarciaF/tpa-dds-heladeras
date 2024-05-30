package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.*;

import ar.edu.utn.frba.dds.colaboraciones.DistribucionDeViandas;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DistribucionDeViandasTest {
  private DistribucionDeViandas distribucionDeViandas;

  @BeforeEach
  void setUp() {
    Heladera heladeraOrigen = new Heladera("Origen", 40, new Ubicacion(0.1, 0.0), "kd993j", 23, null, null);
    Heladera heladeraDestino = new Heladera("Origen", 40, new Ubicacion(0.1, 0.0), "kd993j", 23, null, null);
    distribucionDeViandas = new DistribucionDeViandas(
        MotivoDeDistribucion.DESPERFECTO_HELADERA,
        LocalDate.now(),
        10,
        heladeraOrigen,
        heladeraDestino
    );
  }

  @Test
  void elPuntajeSeCalculaCorrectamente() {
    // El puntaje de la distribucion es cantidad * 1
    assertEquals(10.0, distribucionDeViandas.puntaje(), 0);
  }
}