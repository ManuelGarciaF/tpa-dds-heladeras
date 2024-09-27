package ar.edu.utn.frba.dds.dominio.colaboraciones;

import ar.edu.utn.frba.dds.dominio.Heladera;
import ar.edu.utn.frba.dds.dominio.MapaHeladeras;
import ar.edu.utn.frba.dds.dominio.PersonaVulnerable;
import ar.edu.utn.frba.dds.dominio.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.dominio.Ubicacion;
import ar.edu.utn.frba.dds.dominio.UsoTarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.dominio.tecnicos.RepoTecnicos;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistroDePersonaVulnerableTest implements SimplePersistenceTest {
  private RegistroDePersonaVulnerable registroDePersonaVulnerable;
  private TarjetaPersonaVulnerable tarjetaPersonaVulnerable;

  @BeforeEach
  void setUp() {
    tarjetaPersonaVulnerable = new TarjetaPersonaVulnerable("123", MapaHeladeras.getInstance());

    PersonaVulnerable persona = new PersonaVulnerable("Juan",
        null,
        LocalDate.of(1990, 1, 1),
        LocalDate.now().minusMonths(2),
        0,
        tarjetaPersonaVulnerable);
    entityManager().persist(persona);

    registroDePersonaVulnerable = new RegistroDePersonaVulnerable(persona);
  }

  @Test
  void elPuntajeSeCalculaCorrectamente() {
    // Devolver 3 usos
    var heladera = new Heladera(
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
        RepoTecnicos.getInstance());
    MapaHeladeras.getInstance().agregarHeladera(heladera);
    heladera.registrarUso(new UsoTarjetaPersonaVulnerable(tarjetaPersonaVulnerable, LocalDate.now()));
    heladera.registrarUso(new UsoTarjetaPersonaVulnerable(tarjetaPersonaVulnerable, LocalDate.now()));
    heladera.registrarUso(new UsoTarjetaPersonaVulnerable(tarjetaPersonaVulnerable, LocalDate.now()));

    // persona 1 tiene 3 usos y 2 meses activa
    Assertions.assertEquals(12.0, registroDePersonaVulnerable.puntaje(), 0);
  }
}