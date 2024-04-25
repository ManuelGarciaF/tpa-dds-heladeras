package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DonacionDeViandaTest {
  private List<Vianda> viandas;
  private PersonaHumana colaborador;
  private Heladera heladera;

  @BeforeEach
  void setUp() {
    colaborador = new PersonaHumana("Mati",
        "Matias",
        LocalDate.of(1995, 10, 10),
        "Calle Falsa 123",
        new MedioDeContacto(null, "test@gmail.com", null),
        Set.of(FormaDeColaboracionHumana.DONACION_VIANDA),
        new Usuario("al;dfsafjl;dsfa10-23", "mati123facha"));

    viandas = List.of(
        new Vianda("Vianda 1", LocalDate.of(2029, 1, 1), colaborador, null, null),
        new Vianda("Vianda 1", LocalDate.of(2029, 1, 1), colaborador, null, null),
        new Vianda("Vianda 1", LocalDate.of(2029, 1, 1), colaborador, null, null),
        new Vianda("Vianda 1", LocalDate.of(2029, 1, 1), colaborador, null, null),
        new Vianda("Vianda 1", LocalDate.of(2029, 1, 1), colaborador, null, null));

    heladera = new Heladera(
        "Heladera de prueba",
        10,
        new Ubicacion(0.0, 0.0));
  }

  @Test
  void agregaLasViandasALaHeladera() {
    DonacionDeVianda donacion = new DonacionDeVianda(viandas, heladera);
    assertEquals(0, heladera.listarViandas().size());
    donacion.realizarColaboracion();
    assertEquals(viandas.size(), heladera.listarViandas().size());
  }

}