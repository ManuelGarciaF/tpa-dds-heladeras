package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.colaboraciones.RegistroDePersonaVulnerable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistroDePersonaVulnerableTest {
  private RegistroDePersonaVulnerable registroDePersonaVulnerable;
  private PersonaVulnerable persona1;
  private PersonaVulnerable persona2;

  @BeforeEach
  void setUp() {
    persona1 = new PersonaVulnerable("Juan",
        null,
        LocalDate.of(1990, 1, 1),
        LocalDate.now().minusMonths(2),
        0,
        "fasfsd",
        new ArrayList<>());
    persona2 = new PersonaVulnerable("Juana",
        null,
        LocalDate.of(1991, 1, 1),
        LocalDate.now().minusMonths(3),
        0,
        "fasfsddafs",
        new ArrayList<>());
    registroDePersonaVulnerable = new RegistroDePersonaVulnerable(List.of(persona1, persona2));
  }

  @Test
  void elPuntajeSeCalculaCorrectamente() {
    persona1.agregarUsoTarjeta(new UsoTarjeta(null, LocalDate.now()));
    persona1.agregarUsoTarjeta(new UsoTarjeta(null, LocalDate.now()));
    persona1.agregarUsoTarjeta(new UsoTarjeta(null, LocalDate.now()));
    persona2.agregarUsoTarjeta(new UsoTarjeta(null, LocalDate.now()));

    // persona 1 tiene 3 usos y 2 meses, persona 2 tiene 1 uso y 3 meses
    Assertions.assertEquals(18.0, registroDePersonaVulnerable.puntaje(), 0);
  }
}