package ar.edu.utn.frba.dds.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.edu.utn.frba.dds.dominio.colaboraciones.DonacionDeDinero;
import ar.edu.utn.frba.dds.dominio.colaboraciones.HacerseCargoHeladera;
import ar.edu.utn.frba.dds.dominio.tecnicos.RepoTecnicos;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ColaboradorJuridicoTest implements SimplePersistenceTest {

  ColaboradorJuridico colaboradorJuridico;
  Heladera heladera;

  @BeforeEach
  void setUp() {
    colaboradorJuridico = new ColaboradorJuridico("Mati SRL",
        TipoPersonaJuridica.EMPRESA,
        "Automotores",
        new MedioDeContacto(null, "test@gmail.com", null),
        "Triunvirato 55894",
        Set.of(FormaDeColaboracionJuridica.DONACION_DINERO));

    heladera = new Heladera("heladera1",
        40,
        new Ubicacion(0.1, 0.0),
        "kd993j",
        LocalDate.now().minusMonths(4),
        15.0,
        0.0,
        null,
        null,
        null,
        null,
        null,
        RepoTecnicos.getInstance());
    MapaHeladeras.getInstance().agregarHeladera(heladera);
  }

  @Test
  void puedeAgregarUnaColaboracion() {
    var colaboracion = new DonacionDeDinero(420, false, null);
    colaboradorJuridico.colaborar(colaboracion);
    assertTrue(colaboradorJuridico.getHistorialDeColaboraciones().contains(colaboracion));
  }

  @Test
  void elPuntajeSeCalculaCorrectamente() {
    colaboradorJuridico.colaborar(new DonacionDeDinero(420, false, null));
    colaboradorJuridico.colaborar(new HacerseCargoHeladera(heladera));

    var tarjeta = new TarjetaPersonaVulnerable("123", MapaHeladeras.getInstance());
    entityManager().persist(tarjeta);
    var personaVulnerable = new PersonaVulnerable("Mati",
        "Calle Falsa 123",
        LocalDate.of(1892, 10, 10),
        LocalDate.now().minusMonths(10),
        100,
        tarjeta);
    entityManager().persist(personaVulnerable);

    personaVulnerable.agregarUsoTarjeta(heladera);
    personaVulnerable.agregarUsoTarjeta(heladera);

    Double valorEsperado = 420 * DonacionDeDinero.COEFICIENTE_PUNTAJE
        + 1 * 4 * 2 * HacerseCargoHeladera.COEFICIENTE_PUNTAJE;

    assertEquals(valorEsperado, colaboradorJuridico.puntaje());
  }
}

