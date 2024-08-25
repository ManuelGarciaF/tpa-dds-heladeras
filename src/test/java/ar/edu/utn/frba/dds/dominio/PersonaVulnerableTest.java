package ar.edu.utn.frba.dds.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ar.edu.utn.frba.dds.exceptions.UsoTarjetaException;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonaVulnerableTest {
  public PersonaVulnerable personaVulnerable;
  public MapaHeladeras mapaHeladeras;

  @BeforeEach
  void setUp() {
    mapaHeladeras = new MapaHeladeras();
    mapaHeladeras.agregarHeladera(
        new Heladera("heladera1",
            40,
            new Ubicacion(0.1, 0.0),
            "kd993j",
            LocalDate.now(),
            null,
            null,
            null,
            null,
            null,
            null));
    personaVulnerable = new PersonaVulnerable("Mati",
        "Calle Falsa 123",
        LocalDate.of(1892, 10, 10),
        LocalDate.now(),
        1,
        new TarjetaPersonaVulnerable("123", mapaHeladeras));
  }

  @Test
  void puedoAgregar2UsosConUnMenorACargo() {
    var heladera = mapaHeladeras.buscarHeladera("heladera1");
    personaVulnerable.agregarUsoTarjeta(heladera);
    personaVulnerable.agregarUsoTarjeta(heladera);
    assertEquals(2, personaVulnerable.usosTarjeta());
    assertEquals(2, heladera.usosDeTarjetaPersonaVulnerable("123").size());
  }

  @Test
  void noPuedoAgregar7UsosConUnMenorEnUnSoloDia() {
    var heladera = mapaHeladeras.buscarHeladera("heladera1");

    personaVulnerable.agregarUsoTarjeta(heladera);
    personaVulnerable.agregarUsoTarjeta(heladera);
    personaVulnerable.agregarUsoTarjeta(heladera);
    personaVulnerable.agregarUsoTarjeta(heladera);
    personaVulnerable.agregarUsoTarjeta(heladera);
    personaVulnerable.agregarUsoTarjeta(heladera);
    assertThrows(UsoTarjetaException.class, () -> personaVulnerable.agregarUsoTarjeta(heladera));

  }

}