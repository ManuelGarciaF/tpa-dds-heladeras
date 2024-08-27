package ar.edu.utn.frba.dds.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.edu.utn.frba.dds.dominio.colaboraciones.DistribucionDeViandas;
import ar.edu.utn.frba.dds.dominio.colaboraciones.DonacionDeDinero;
import ar.edu.utn.frba.dds.dominio.colaboraciones.DonacionDeVianda;
import ar.edu.utn.frba.dds.dominio.colaboraciones.RegistroDePersonaVulnerable;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ColaboradorHumanoTest {

  private ColaboradorHumano colaboradorHumano;
  private MapaHeladeras mapaHeladeras;

  @BeforeEach
  void setUp() {
    colaboradorHumano = new ColaboradorHumano("Mati",
        "Matias",
        LocalDate.of(1995, 10, 10),
        "Calle Falsa 123",
        new MedioDeContacto(null, "test@gmail.com", null),
        Set.of(FormaDeColaboracionHumana.DONACION_VIANDA),
        TipoDocumento.DNI,
        180924102
    );

    mapaHeladeras = new MapaHeladeras();
    mapaHeladeras.agregarHeladera(
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
            null));

    mapaHeladeras.agregarHeladera(
        new Heladera("heladera2",
            39,
            new Ubicacion(0.2, 0.0),
            "kd393j",
            LocalDate.now(),
            15.0,
            0.0,
            null,
            null,
            null,
            null,
            null,
            null));
  }

  @Test
  void puedeAgregarUnaColaboracion() {
    var colaboracion = new DonacionDeDinero(420, false, null);
    colaboradorHumano.colaborar(colaboracion);
    assertTrue(colaboradorHumano.getHistorialDeColaboraciones().contains(colaboracion));
  }

  @Test
  void elPuntajeSeCalculaCorrectamente() {
    colaboradorHumano.colaborar(new DistribucionDeViandas(
        MotivoDeDistribucion.FALTA_DE_VIANDAS,
        LocalDate.now(),
        10,
        null,
        null));
    colaboradorHumano.colaborar(new DonacionDeDinero(420, false, null));
    colaboradorHumano.colaborar(new DonacionDeVianda(
        new Vianda("Vianda", LocalDate.now().plusWeeks(2), 10, 10),
        null));

    var tarjeta = new TarjetaPersonaVulnerable("123", mapaHeladeras);
    var personaVulnerable = new PersonaVulnerable("Mati",
        "Calle Falsa 123",
        LocalDate.of(1892, 10, 10),
        LocalDate.now().minusMonths(10),
        100,
        tarjeta);
    personaVulnerable.agregarUsoTarjeta(mapaHeladeras.buscarHeladera("heladera1"));
    personaVulnerable.agregarUsoTarjeta(mapaHeladeras.buscarHeladera("heladera2"));

    colaboradorHumano.colaborar(new RegistroDePersonaVulnerable(personaVulnerable));

    Double valorEsperado = 10 * DistribucionDeViandas.COEFICIENTE_PUNTAJE
        + 420 * DonacionDeDinero.COEFICIENTE_PUNTAJE
        + 2 * DonacionDeVianda.COEFICIENTE_PUNTAJE
        + 10 * 2 * RegistroDePersonaVulnerable.COEFICIENTE_PUNTAJE;
    assertEquals(valorEsperado, colaboradorHumano.puntaje());
  }
}

