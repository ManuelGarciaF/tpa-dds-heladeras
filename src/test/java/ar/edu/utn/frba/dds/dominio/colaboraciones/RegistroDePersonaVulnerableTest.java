package ar.edu.utn.frba.dds.dominio.colaboraciones;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.dominio.MapaHeladeras;
import ar.edu.utn.frba.dds.dominio.Tarjeta;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ar.edu.utn.frba.dds.dominio.PersonaVulnerable;
import ar.edu.utn.frba.dds.dominio.UsoTarjeta;

class RegistroDePersonaVulnerableTest {
  private RegistroDePersonaVulnerable registroDePersonaVulnerable;
  private MapaHeladeras mapaHeladeras;
  private Tarjeta tarjeta;

  @BeforeEach
  void setUp() {
    mapaHeladeras = mock(MapaHeladeras.class);
    tarjeta = new Tarjeta("123", mapaHeladeras);

    PersonaVulnerable persona = new PersonaVulnerable("Juan",
        null,
        LocalDate.of(1990, 1, 1),
        LocalDate.now().minusMonths(2),
        0,
        tarjeta);

    registroDePersonaVulnerable = new RegistroDePersonaVulnerable(persona);
  }

  @Test
  void elPuntajeSeCalculaCorrectamente() {
    // Hacer que el mock devuelva 3 usos
    when(mapaHeladeras.encontrarUsosDeTarjeta("123")).thenReturn(List.of(
        new UsoTarjeta(tarjeta, LocalDate.now()),
        new UsoTarjeta(tarjeta, LocalDate.now()),
        new UsoTarjeta(tarjeta, LocalDate.now())
    ));

    // persona 1 tiene 3 usos y 2 meses activa
    Assertions.assertEquals(12.0, registroDePersonaVulnerable.puntaje(), 0);
  }
}