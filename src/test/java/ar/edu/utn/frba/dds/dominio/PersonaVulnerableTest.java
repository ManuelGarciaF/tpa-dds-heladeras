package ar.edu.utn.frba.dds.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ar.edu.utn.frba.dds.dominio.tecnicos.RepoTecnicos;
import ar.edu.utn.frba.dds.exceptions.UsoTarjetaException;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonaVulnerableTest implements SimplePersistenceTest {
  public PersonaVulnerable personaVulnerable;

  @BeforeEach
  void setUp() {
    MapaHeladeras.getInstance().agregarHeladera(
        new Heladera("heladera1",
            40,
            new Ubicacion(0.1, 0.0),
            "kd993j",
            LocalDate.now(),
            15.0,
            0.0,
            null,
            null,
            null,
            null,
            null,
            RepoTecnicos.getInstance()));
    var tarjeta = new TarjetaPersonaVulnerable("123", MapaHeladeras.getInstance());
    entityManager().persist(tarjeta);
    personaVulnerable = new PersonaVulnerable("Mati",
        "Calle Falsa 123",
        LocalDate.of(1892, 10, 10),
        LocalDate.now(),
        1,
        tarjeta);
    entityManager().persist(personaVulnerable);
  }

  @Test
  void puedoAgregar2UsosConUnMenorACargo() {
    var heladera = MapaHeladeras.getInstance().buscarHeladera("heladera1");
    personaVulnerable.agregarUsoTarjeta(heladera);
    personaVulnerable.agregarUsoTarjeta(heladera);
    assertEquals(2, personaVulnerable.usosTarjeta());
    assertEquals(2, heladera.usosDeTarjetaPersonaVulnerable("123").size());
  }

  @Test
  void noPuedoAgregar7UsosConUnMenorEnUnSoloDia() {
    var heladera = MapaHeladeras.getInstance().buscarHeladera("heladera1");

    personaVulnerable.agregarUsoTarjeta(heladera);
    personaVulnerable.agregarUsoTarjeta(heladera);
    personaVulnerable.agregarUsoTarjeta(heladera);
    personaVulnerable.agregarUsoTarjeta(heladera);
    personaVulnerable.agregarUsoTarjeta(heladera);
    personaVulnerable.agregarUsoTarjeta(heladera);
    assertThrows(UsoTarjetaException.class, () -> personaVulnerable.agregarUsoTarjeta(heladera));
  }

}