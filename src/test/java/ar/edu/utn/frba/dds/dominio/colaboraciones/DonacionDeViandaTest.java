package ar.edu.utn.frba.dds.dominio.colaboraciones;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ar.edu.utn.frba.dds.dominio.Heladera;
import ar.edu.utn.frba.dds.dominio.Ubicacion;
import ar.edu.utn.frba.dds.dominio.Vianda;

class DonacionDeViandaTest {
  private DonacionDeVianda donacionDeVianda;

  @BeforeEach
  void setUp() {
    donacionDeVianda = new DonacionDeVianda(
        List.of(
            vianda("Milanesa", 2, 500, 200),
            vianda("Ensalada", 1, 100, 100)
        ),
        new Heladera("Heladera Origen", 20, new Ubicacion(0.0, 0.0), "123", 5, null, null)
    );
  }


  @Test
  void elPuntajeSeCalculaCorrectamente() {
    // [VIANDAS_DONADAS] * [suma SEMANAS_FRESCA] * 1,5 = 2 * 3 * 1,5 = 9
    assertEquals(9.0, donacionDeVianda.puntaje(), 0);
  }


  private Vianda vianda(String nombre, int semanasFresca, int calorias, int peso) {
    var vencimiento = LocalDate.now().plusWeeks(semanasFresca);
    return new Vianda(nombre, vencimiento, calorias, peso);
  }
}