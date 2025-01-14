package ar.edu.utn.frba.dds.model.colaboraciones;

import static org.junit.jupiter.api.Assertions.*;

import ar.edu.utn.frba.dds.model.repositorios.RepoTecnicos;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ar.edu.utn.frba.dds.model.Heladera;
import ar.edu.utn.frba.dds.model.Ubicacion;
import ar.edu.utn.frba.dds.model.Vianda;

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
            LocalDate.now(),
            15.0,
            0.0,
            null,
            null,
            null,
            null,
            null,
            RepoTecnicos.getInstance())
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