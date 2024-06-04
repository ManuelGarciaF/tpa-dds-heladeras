package ar.edu.utn.frba.dds.dominio.colaboraciones;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ar.edu.utn.frba.dds.dominio.PersonaVulnerable;
import ar.edu.utn.frba.dds.dominio.UsoTarjeta;

class RegistroDePersonaVulnerableTest {
  private RegistroDePersonaVulnerable registroDePersonaVulnerable;
  private PersonaVulnerable persona1;

  @BeforeEach
  void setUp() {
    persona1 = new PersonaVulnerable("Juan",
        null,
        LocalDate.of(1990, 1, 1),
        LocalDate.now().minusMonths(2),
        0,
        "fasfsd",
        new ArrayList<>());
    registroDePersonaVulnerable = new RegistroDePersonaVulnerable(persona1);
  }

  @Test
  void elPuntajeSeCalculaCorrectamente() {
    persona1.agregarUsoTarjeta(new UsoTarjeta(null, LocalDate.now()));
    persona1.agregarUsoTarjeta(new UsoTarjeta(null, LocalDate.now()));
    persona1.agregarUsoTarjeta(new UsoTarjeta(null, LocalDate.now()));

    // persona 1 tiene 3 usos y 2 meses activa
    Assertions.assertEquals(12.0, registroDePersonaVulnerable.puntaje(), 0);
  }
}