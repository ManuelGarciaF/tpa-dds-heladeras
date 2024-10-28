package ar.edu.utn.frba.dds.dominio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ar.edu.utn.frba.dds.dominio.colaboraciones.DistribucionDeViandas;
import ar.edu.utn.frba.dds.dominio.colaboraciones.DonacionDeDinero;
import ar.edu.utn.frba.dds.dominio.colaboraciones.DonacionDeVianda;
import ar.edu.utn.frba.dds.dominio.colaboraciones.RegistroDePersonaVulnerable;
import ar.edu.utn.frba.dds.dominio.incidentes.AlertaFallaConexion;
import ar.edu.utn.frba.dds.dominio.incidentes.TipoDeFalla;
import ar.edu.utn.frba.dds.dominio.notificacionesheladera.NotificacionHeladeraHandler;
import ar.edu.utn.frba.dds.dominio.notificacionesheladera.ProveedorMensajesInstantaneos;
import ar.edu.utn.frba.dds.dominio.notificacionesheladera.SubscriptorCantidadDeViandas;
import ar.edu.utn.frba.dds.dominio.notificacionesheladera.SubscriptorIncidente;
import ar.edu.utn.frba.dds.dominio.sensoresheladera.ProveedorCantidadDeViandasSensor;
import ar.edu.utn.frba.dds.dominio.sensoresheladera.ProveedorPesoSensor;
import ar.edu.utn.frba.dds.dominio.sensoresheladera.ProveedorTemperaturaSensor;
import ar.edu.utn.frba.dds.dominio.tecnicos.RepoTecnicos;
import ar.edu.utn.frba.dds.dominio.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.externo.*;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ColaboradorHumanoTest implements SimplePersistenceTest {
  private Heladera heladera1;
  private ProveedorCantidadDeViandasSensor proveedorCantidadDeViandasSensor;
  private InstantMessageSender instantMessageSender;

  private ColaboradorHumano colaboradorHumano1;
  private ColaboradorHumano colaboradorHumano2;

  @BeforeEach
  void setUp() {
    TSensor tSensor = mock(TSensor.class);
    WSensor wSensor = mock(WSensor.class);
    LSensor lSensor = mock(LSensor.class);
    instantMessageSender = mock(InstantMessageSender.class);
    ControladorDeAcceso controladorDeAcceso = mock(ControladorDeAcceso.class);

    ProveedorTemperaturaSensor proveedorTemperaturaSensor = new ProveedorTemperaturaSensor(tSensor, "kd993j");
    proveedorCantidadDeViandasSensor = new ProveedorCantidadDeViandasSensor(lSensor);

    RepoTecnicos.getInstance().agregarTecnico(new Tecnico("Ricardo Flores", new Ubicacion(0.3, 0.6)));

    colaboradorHumano1 = new ColaboradorHumano("Mati",
        "Matias",
        LocalDate.of(1995, 10, 10),
        "Calle Falsa 123",
        new MedioDeContacto(null, "test@gmail.com", "123456789"),
        new Usuario("contraseniaMati", "mati"),
        null,
        TipoDocumento.DNI,
        180924102,
        new ProveedorMensajesInstantaneos(instantMessageSender,
            InstantMessageApp.WHATSAPP,
            "123456789")
    );
    entityManager().persist(colaboradorHumano1);

    colaboradorHumano2 = new ColaboradorHumano("Pedro",
        "Perez",
        LocalDate.of(1995, 10, 10),
        "Calle Falsa 124",
        new MedioDeContacto(null, "test@yahoo.com", "987654321"),
        new Usuario("contraseniaPedro", "pedro"),
        null,
        TipoDocumento.DNI,
        180924103,
        new ProveedorMensajesInstantaneos(instantMessageSender,
            InstantMessageApp.WHATSAPP,
            "9876454321")
    );
    entityManager().persist(colaboradorHumano2);

    heladera1 = new Heladera("heladera1",
        40,
        new Ubicacion(0.1, 0.0),
        "kd993j",
        LocalDate.now(),
        15.0,
        0.0,
        new ProveedorPesoSensor(wSensor),
        proveedorTemperaturaSensor,
        new AutorizadorAperturasActual(controladorDeAcceso),
        proveedorCantidadDeViandasSensor,
        new NotificacionHeladeraHandler(),
        RepoTecnicos.getInstance());
    MapaHeladeras.getInstance().agregarHeladera(heladera1);

    // Agregar un valor inicial para la cantidad de viandas
    proveedorCantidadDeViandasSensor.interpretarLectura(1);

    var lSensor2 = mock(LSensor.class);
    var proveedorCantidadDeViandasSensor2 = new ProveedorCantidadDeViandasSensor(lSensor2);
    MapaHeladeras.getInstance().agregarHeladera(
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
            proveedorCantidadDeViandasSensor2,
            new NotificacionHeladeraHandler(),
            RepoTecnicos.getInstance()));
    // Valor inicial para la cantidad de viandas
    proveedorCantidadDeViandasSensor2.interpretarLectura(0);
  }

  @Test
  void puedeAgregarUnaColaboracion() {
    var colaboracion = new DonacionDeDinero(420, false, null);
    colaboradorHumano1.colaborar(colaboracion);
    assertTrue(colaboradorHumano1.getHistorialDeColaboraciones().contains(colaboracion));
  }

  @Test
  void elPuntajeSeCalculaCorrectamente() {
    colaboradorHumano1.colaborar(new DistribucionDeViandas(
        MotivoDeDistribucion.FALTA_DE_VIANDAS,
        LocalDate.now(),
        10,
        null,
        null));
    colaboradorHumano1.colaborar(new DonacionDeDinero(420, false, null));
    colaboradorHumano1.colaborar(new DonacionDeVianda(
        new Vianda("Vianda", LocalDate.now().plusWeeks(2), 10, 10),
        null));

    var tarjeta = new TarjetaPersonaVulnerable("123", MapaHeladeras.getInstance());
    entityManager().persist(tarjeta);
    var personaVulnerable = new PersonaVulnerable("Mati",
        "Calle Falsa 123",
        LocalDate.of(1892, 10, 10),
        LocalDate.now().minusMonths(10),
        100,
        tarjeta);
    entityManager().persist(personaVulnerable);
    personaVulnerable.agregarUsoTarjeta(MapaHeladeras.getInstance().buscarHeladera("heladera1"));
    personaVulnerable.agregarUsoTarjeta(MapaHeladeras.getInstance().buscarHeladera("heladera2"));

    colaboradorHumano1.colaborar(new RegistroDePersonaVulnerable(personaVulnerable));

    Double valorEsperado = 10 * DistribucionDeViandas.COEFICIENTE_PUNTAJE
        + 420 * DonacionDeDinero.COEFICIENTE_PUNTAJE
        + 2 * DonacionDeVianda.COEFICIENTE_PUNTAJE
        + 10 * 2 * RegistroDePersonaVulnerable.COEFICIENTE_PUNTAJE;
    assertEquals(valorEsperado, colaboradorHumano1.puntaje());
  }

  @Test
  void puedeSubscribirseAUnaHeladeraYSerAlertadoPorFaltaDeViandas() {
    heladera1.getNotificacionHeladeraHandler().agregarSubscriptorCantidadDeViandas(
        new SubscriptorCantidadDeViandas(colaboradorHumano1, 10));

    // Se registra un cambio en la cantidad de viandas
    proveedorCantidadDeViandasSensor.interpretarLectura(0);

    verify(instantMessageSender).sendMessage(
        eq(InstantMessageApp.WHATSAPP),
        eq("123456789"),
        any(String.class));
  }

  @Test
  void puedeSubscribirseAUnaHeladeraYSerAlertadoPorUnIncidente() {
    var subscriptor = new SubscriptorIncidente(colaboradorHumano1);
    heladera1.getNotificacionHeladeraHandler().agregarSubscriptorIncidente(subscriptor);

    // Se registra un incidente
    heladera1.nuevoIncidente(
        new AlertaFallaConexion(LocalDateTime.now(), TipoDeFalla.SENSOR_DE_TEMPERATURA));


    verify(instantMessageSender).sendMessage(
        eq(InstantMessageApp.WHATSAPP),
        eq("123456789"),
        any(String.class));
  }

  @Test
  void alSerAlertadoPorUnIncidenteSeLeEnviaUnaSugerencia() {
    // Subscribirse y ser alertado.
    heladera1.getNotificacionHeladeraHandler()
        .agregarSubscriptorIncidente(new SubscriptorIncidente(colaboradorHumano1));
    heladera1.nuevoIncidente(
        new AlertaFallaConexion(LocalDateTime.now(), TipoDeFalla.SENSOR_DE_TEMPERATURA));

    assertEquals(1, colaboradorHumano1.getSugerenciasPendientes().size());
  }

  @Test
  void alAceptarLaSugerenciaSeLeAgregaUnaColaboracion() {
    // Agregar una vianda a la heladera rota
    heladera1.ingresarViandas(List.of(new Vianda("mm...food",
        LocalDate.now().plusWeeks(2),
        10,
        10)));
    // Subscribirse y ser alertado.
    heladera1.getNotificacionHeladeraHandler()
        .agregarSubscriptorIncidente(new SubscriptorIncidente(colaboradorHumano1));
    heladera1.nuevoIncidente(
        new AlertaFallaConexion(LocalDateTime.now(), TipoDeFalla.SENSOR_DE_TEMPERATURA));

    // Aceptar la sugerencia
    colaboradorHumano1.getSugerenciasPendientes().get(0).aceptar(colaboradorHumano1);

    assertEquals(1, colaboradorHumano1.getHistorialDeColaboraciones().size());
  }

  @Test
  void dosColaboradoresNoPuedenAceptarLaMismaSugerencia() {
    // Subscribirse y ser alertado.
    heladera1.getNotificacionHeladeraHandler()
        .agregarSubscriptorIncidente(new SubscriptorIncidente(colaboradorHumano1));
    heladera1.getNotificacionHeladeraHandler()
        .agregarSubscriptorIncidente(new SubscriptorIncidente(colaboradorHumano2));
    heladera1.nuevoIncidente(
        new AlertaFallaConexion(LocalDateTime.now(), TipoDeFalla.SENSOR_DE_TEMPERATURA));

    // El colaborador1 acepta la sugerencia
    colaboradorHumano1.getSugerenciasPendientes().get(0).aceptar(colaboradorHumano1);

    // El colaborador2 intenta aceptar la misma sugerencia
    assertThrows(RuntimeException.class,
        () -> colaboradorHumano2.getSugerenciasPendientes().get(0).aceptar(colaboradorHumano2));
  }
}

