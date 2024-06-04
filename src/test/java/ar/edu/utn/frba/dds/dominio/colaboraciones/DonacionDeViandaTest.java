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
        vianda("Milanesa", 2, 500, 200),
        new Heladera(
            "Heladera Origen",
            20,
            new Ubicacion(0.0, 0.0),
            "123",
            5,
            null,
            null,
            null
        )
    );
  }


  @Test
  void elPuntajeSeCalculaCorrectamente() {
    assertEquals(3.0, donacionDeVianda.puntaje(), 0);
  }

  // Devuelve una vianda con los datos ingresados
  private Vianda vianda(String nombre, int semanasFresca, int calorias, int peso) {
    var vencimiento = LocalDate.now().plusWeeks(semanasFresca);
    return new Vianda(nombre, vencimiento, calorias, peso);
  }
}