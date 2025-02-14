package ar.edu.utn.frba.dds.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ar.edu.utn.frba.dds.model.incidentes.FallaTecnica;
import ar.edu.utn.frba.dds.model.notificacionesheladera.NotificacionHeladeraHandler;
import ar.edu.utn.frba.dds.model.repositorios.MapaHeladeras;
import ar.edu.utn.frba.dds.model.sensoresheladera.ProveedorCantidadDeViandasSensor;
import ar.edu.utn.frba.dds.model.sensoresheladera.ProveedorPesoSensor;
import ar.edu.utn.frba.dds.model.sensoresheladera.ProveedorTemperaturaSensor;
import ar.edu.utn.frba.dds.model.repositorios.RepoTecnicos;
import ar.edu.utn.frba.dds.model.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.exceptions.HeladeraException;
import ar.edu.utn.frba.dds.externo.*;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HeladeraTest implements SimplePersistenceTest {
  private Heladera heladera;
  private TSensor tSensor;
  private WSensor wSensor;
  private LSensor lSensor;
  private ProveedorTemperaturaSensor proveedorTemperaturaSensor;
  private ControladorDeAcceso controladorDeAcceso;
  private ColaboradorHumano colaboradorHumano;

  @BeforeEach
  void setUp() {
    tSensor = mock(TSensor.class);
    wSensor = mock(WSensor.class);
    lSensor = mock(LSensor.class);
    controladorDeAcceso = mock(ControladorDeAcceso.class);

    proveedorTemperaturaSensor = new ProveedorTemperaturaSensor(tSensor, "kd993j");
    var proveedorCantidadDeViandasSensor = new ProveedorCantidadDeViandasSensor(lSensor);
    RepoTecnicos.getInstance().agregarTecnico(new Tecnico("Ricardo Flores", new Ubicacion(0.3, 0.6)));

    heladera = new Heladera("heladera",
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
    MapaHeladeras.getInstance().agregarHeladera(heladera);

    // Agregar un valor inicial para la cantidad de viandas
    proveedorCantidadDeViandasSensor.interpretarLectura(1);

    colaboradorHumano = new ColaboradorHumano("Mati",
        "Matias",
        LocalDate.of(1995, 10, 10),
        "Calle Falsa 123",
        new MedioDeContacto(null, "test@gmail.com", null),
        null,
        Set.of(FormaDeColaboracionHumana.DONACION_VIANDA),
        TipoDocumento.DNI,
        180924102,
        null
    );
    entityManager().persist(colaboradorHumano);
  }

  @Test
  void requiereAtencionCuandoLasUltimasTresTemperaturasEstanSobreTemperaturaMaxima() {
    proveedorTemperaturaSensor.agregarLectura(24.0);
    proveedorTemperaturaSensor.agregarLectura(25.0);
    proveedorTemperaturaSensor.agregarLectura(123.0);

    assertTrue(heladera.requiereAtencion());
  }

  @Test
  void noRequiereAtencionCuandoLasUltimasTresTemperaturasNoEstanSobreTemperaturaMaxima() {
    proveedorTemperaturaSensor.agregarLectura(20.0);
    proveedorTemperaturaSensor.agregarLectura(10.0);
    proveedorTemperaturaSensor.agregarLectura(123.0);

    assertFalse(heladera.requiereAtencion());
  }

  @Test
  void nivelLlenadoEsBajoCuandoHay1kgDeViandas() {
    when(wSensor.getWeight("kd993j")).thenReturn(new Reading(1.0, "KG"));
    assertEquals(NivelLlenado.BAJO, heladera.nivelLlenado());
  }

  @Test
  void nivelLlenadoEsMedioCuandoHay6kgDeViandas() {
    when(wSensor.getWeight("kd993j")).thenReturn(new Reading(6.0, "KG"));
    assertEquals(NivelLlenado.MEDIO, heladera.nivelLlenado());
  }

  @Test
  void nivelLlenadoEsAltoCuandoHay200kgDeViandas() {
    when(wSensor.getWeight("kd993j")).thenReturn(new Reading(200.0, "KG"));
    assertEquals(NivelLlenado.ALTO, heladera.nivelLlenado());
  }

  @Test
  void seLeAvisaAlControladorDeAccesoAlAgregarUnaSolicitudDeApertura() {
    // Darle una tarjeta al colaborador
    colaboradorHumano.setTarjetaColaborador(new TarjetaColaborador("1234"));
    var apertura = new AperturaHeladera(colaboradorHumano, LocalDateTime.now());
    heladera.agregarSolicitudApertura(apertura);

    verify(controladorDeAcceso).notificarTarjetasColaboradoraHabilitada("1234");
    assertTrue(heladera.getAperturasPendientes().contains(apertura));
  }

  @Test
  void puedoRegistrarUnaAperturaSiHayUnaSolicitudValida() {
    // Darle una tarjeta al colaborador
    colaboradorHumano.setTarjetaColaborador(new TarjetaColaborador("1234"));
    var apertura = new AperturaHeladera(colaboradorHumano, LocalDateTime.now());
    heladera.agregarSolicitudApertura(apertura);

    // No pasaron 3 hs
    assertDoesNotThrow(() -> heladera.registrarApertura(colaboradorHumano, LocalDateTime.now()));
    assertTrue(heladera.getAperturasCompletadas().contains(apertura));
  }

  @Test
  void noPuedoRegistrarUnaAperturaSiLaAperturaVence() {
    // Darle una tarjeta al colaborador
    colaboradorHumano.setTarjetaColaborador(new TarjetaColaborador("1234"));
    var apertura = new AperturaHeladera(colaboradorHumano, LocalDateTime.now());
    heladera.agregarSolicitudApertura(apertura);

    // Deberia vencerse en 10000 anios
    assertThrows(HeladeraException.class, () -> heladera.registrarApertura(colaboradorHumano, LocalDateTime.now().plusYears(10000)));
  }

  @Test
  void noPuedoRegistrarUnaAperturaSiNoHaySolicitudes() {
    assertThrows(HeladeraException.class, () -> heladera.registrarApertura(colaboradorHumano, LocalDateTime.now()));
  }

  @Test
  void puedoAgregarUnIncidenteYLaHeladeraDejaDeEstarActiva() {
    heladera.nuevoIncidente(new FallaTecnica(colaboradorHumano, LocalDateTime.now(), "Falla tecnica", "url"));
    assertFalse(heladera.estaActiva());
  }

  @Test
  void laHeladeraEstaActivaSiNoHayIncidentes() {
    assertTrue(heladera.estaActiva());
  }

  @Test
  void seGeneraUnIncidenteCuandoElSensorDePesoLanzaUnaExcepcion() {
    when(wSensor.getWeight("kd993j")).thenThrow(new RuntimeException("Error"));

    try {
      heladera.nivelLlenado(); // Operacion que requiere obtener el peso
    } catch (HeladeraException e) {
      // Ignorada
    }

    assertEquals(1, heladera.getIncidentesActivos().size());
  }

  @Test
  void seGeneraUnIncidenteCuandoHay3MedicionesAltas() {
    proveedorTemperaturaSensor.agregarLectura(24.0);
    proveedorTemperaturaSensor.agregarLectura(25.0);
    proveedorTemperaturaSensor.agregarLectura(123.0);

    assertEquals(1, heladera.getIncidentesActivos().size());
  }
}
