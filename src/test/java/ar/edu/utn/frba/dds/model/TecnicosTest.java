package ar.edu.utn.frba.dds.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import ar.edu.utn.frba.dds.model.incidentes.AlertaFallaConexion;
import ar.edu.utn.frba.dds.model.incidentes.TipoDeFalla;
import ar.edu.utn.frba.dds.model.notificacionesheladera.NotificacionHeladeraHandler;
import ar.edu.utn.frba.dds.model.repositorios.MapaHeladeras;
import ar.edu.utn.frba.dds.model.repositorios.RepoColaboradores;
import ar.edu.utn.frba.dds.model.sensoresheladera.ProveedorCantidadDeViandasSensor;
import ar.edu.utn.frba.dds.model.sensoresheladera.ProveedorPesoSensor;
import ar.edu.utn.frba.dds.model.sensoresheladera.ProveedorTemperaturaSensor;
import ar.edu.utn.frba.dds.model.repositorios.RepoTecnicos;
import ar.edu.utn.frba.dds.model.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.model.tecnicos.Visita;
import ar.edu.utn.frba.dds.exceptions.VisitaTecnicoException;
import ar.edu.utn.frba.dds.externo.ControladorDeAcceso;
import ar.edu.utn.frba.dds.externo.LSensor;
import ar.edu.utn.frba.dds.externo.TSensor;
import ar.edu.utn.frba.dds.externo.WSensor;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TecnicosTest implements SimplePersistenceTest {
  private Heladera heladera;
  private TSensor tSensor;
  private WSensor wSensor;
  private LSensor lSensor;
  private ProveedorTemperaturaSensor proveedorTemperaturaSensor;
  private ControladorDeAcceso controladorDeAcceso;
  private ColaboradorHumano colaboradorHumano;

  private Tecnico ricardo;
  private Tecnico juan;
  private Tecnico ramon;

  @BeforeEach
  void setUp() {
    tSensor = mock(TSensor.class);
    wSensor = mock(WSensor.class);
    lSensor = mock(LSensor.class);
    controladorDeAcceso = mock(ControladorDeAcceso.class);

    proveedorTemperaturaSensor = new ProveedorTemperaturaSensor(tSensor, "kd993j");
    var proveedorCantidadDeViandasSensor = new ProveedorCantidadDeViandasSensor(lSensor);

    ricardo = new Tecnico("Ricardo Flores", new Ubicacion(0.3, 0.6));
    RepoTecnicos.getInstance().agregarTecnico(ricardo);
    juan = new Tecnico("Juan Perez", new Ubicacion(0.3, 0.0));
    RepoTecnicos.getInstance().agregarTecnico(juan);
    ramon = new Tecnico("Ramon Castillo", new Ubicacion(0.2, 0.6));
    RepoTecnicos.getInstance().agregarTecnico(ramon);

    heladera = new Heladera("heladera",
        40,
        new Ubicacion(0.0, 0.0),
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
    RepoColaboradores.getInstance().agregarColaborador(colaboradorHumano);
  }

  @Test
  void cuandoTengoUnIncedenteSeAsignaAlTecnicoMasCercano() {
    heladera.nuevoIncidente(new AlertaFallaConexion(LocalDateTime.now(), TipoDeFalla.SENSOR_DE_TEMPERATURA));

    //juan tiene la distancia mas corta
    assertTrue(juan.getVisitasPendientes().contains(heladera));
  }

  @Test
  void AlNoSolucionarElIncidenteLaHeladeraSigueInactiva() {
    heladera.nuevoIncidente(new AlertaFallaConexion(LocalDateTime.now(), TipoDeFalla.SENSOR_DE_TEMPERATURA));

    assertFalse(heladera.estaActiva());

    Visita visitaHeladera = new Visita(
        "se le cargo gas a la heladera",
        LocalDateTime.now(),
        heladera,
        null);
    juan.registrarVisita(visitaHeladera, false);

    assertEquals(0, juan.getVisitasPendientes().size());
    assertFalse(heladera.estaActiva());
  }

  @Test
  void AlSolucionarElIncidenteLaHeladeraVuelveAEstarActiva() {
    heladera.nuevoIncidente(new AlertaFallaConexion(LocalDateTime.now(), TipoDeFalla.SENSOR_DE_TEMPERATURA));

    assertFalse(heladera.estaActiva());

    Visita visitaHeladera = new Visita(
        "se le cargo gas a la heladera",
        LocalDateTime.now(),
        heladera,
        null);
    juan.registrarVisita(visitaHeladera, true);

    assertEquals(0, juan.getVisitasPendientes().size());
    assertTrue(heladera.estaActiva());
  }

  @Test
  void NoSePuedeRealizarUnaVisitaAUnaHeladeraQueNoFueAsignada() {
    Visita visitaHeladera = new Visita(
        "se le cargo gas a la heladera",
        LocalDateTime.now(),
        heladera,
        null);

    assertThrows(VisitaTecnicoException.class, () ->
        juan.registrarVisita(visitaHeladera, true));
  }
}