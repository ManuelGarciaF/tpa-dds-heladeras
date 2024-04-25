package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DistribucionDeViandasTest {
  private Heladera heladera1;
  private Heladera heladera2;
  private List<Vianda> viandas;

  @BeforeEach
  void setUp() {
    var colaborador = new PersonaHumana("Mati",
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

    heladera1 = new Heladera(
        "Heladera 1",
        10,
        new Ubicacion(0.0, 0.0));
    heladera1.ingresarViandas(viandas);

    heladera2 = new Heladera(
        "Heladera 2",
        10,
        new Ubicacion(0.1, 0.0));
  }

  @Test
  void mueveLasViandas() {
    DistribucionDeViandas distribucion = new DistribucionDeViandas(
        MotivoDeDistribucion.FALTA_DE_VIANDAS,
        LocalDate.now(),
        2,
        heladera1,
        heladera2);

    assertEquals(viandas.size(), heladera1.listarViandas().size());
    assertEquals(0, heladera2.listarViandas().size());

    distribucion.realizarColaboracion();

    assertEquals(viandas.size() - 2, heladera1.listarViandas().size());
    assertEquals(2, heladera2.listarViandas().size());
  }
}