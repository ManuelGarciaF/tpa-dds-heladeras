package ar.edu.utn.frba.dds.dominio.colaboraciones;

import static org.junit.jupiter.api.Assertions.*;

import ar.edu.utn.frba.dds.dominio.UsoTarjeta;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ar.edu.utn.frba.dds.dominio.Heladera;
import ar.edu.utn.frba.dds.dominio.Ubicacion;

class HacerseCargoHeladeraTest {
  private HacerseCargoHeladera hacerseCargoHeladera;
  private Heladera heladera;

  @BeforeEach
  void setUp() {
    heladera = new Heladera("heladera",
        40,
        new Ubicacion(0.1, 0.0),
        "kd993j",
        23,
        null,
        null,
        LocalDate.now().minusMonths(2));
    hacerseCargoHeladera = new HacerseCargoHeladera(heladera);
  }

  @Test
  void elPuntajeSeCalculaCorrectamente() {
    heladera.agregarUso(new UsoTarjeta(null, LocalDate.now()));
    heladera.agregarUso(new UsoTarjeta(null, LocalDate.now()));
    heladera.agregarUso(new UsoTarjeta(null, LocalDate.now()));

    // El puntaje de hacerse cargo de una heladera debe ser mesesActivos * usos * 5
    assertEquals(30.0, hacerseCargoHeladera.puntaje(), 0);
  }
}