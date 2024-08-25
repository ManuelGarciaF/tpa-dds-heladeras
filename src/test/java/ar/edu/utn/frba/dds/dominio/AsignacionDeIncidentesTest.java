package ar.edu.utn.frba.dds.dominio;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.dominio.incidentes.FallaTecnica;
import ar.edu.utn.frba.dds.dominio.tecnicos.RepoTecnicos;
import ar.edu.utn.frba.dds.dominio.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.dominio.tecnicos.Visita;
import ar.edu.utn.frba.dds.exceptions.VisitaTecnicoException;
import ar.edu.utn.frba.dds.externo.ControladorDeAcceso;
import ar.edu.utn.frba.dds.externo.TSensor;
import ar.edu.utn.frba.dds.externo.WSensor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;


//TODO: HAY QUE HACER UN SETUP DE LOS OBSERVERS
class AsignacionDeIncidentesTest {
  private Heladera heladera;
  private TSensor tSensor;
  private WSensor wSensor;
  private ProveedorTemperaturaSensor proveedorTemperaturaSensor;
  private ControladorDeAcceso controladorDeAcceso;
  private ColaboradorHumano colaboradorHumano;
  private RepoTecnicos repoTecnicos = new RepoTecnicos();
  private Tecnico ricardo = new Tecnico("Ricardo Flores", new Ubicacion(0.3, 0.6));
  private Tecnico juan = new Tecnico("Juan Perez", new Ubicacion(0.3, 0.1));
  private Tecnico ramon = new Tecnico("Ramon Castillo", new Ubicacion(0.2, 0.6));
  private FallaTecnica fallaTecnica;

  @BeforeEach
  void setUp() {
    tSensor = mock(TSensor.class);
    wSensor = mock(WSensor.class);
    proveedorTemperaturaSensor = new ProveedorTemperaturaSensor(tSensor, "kd993j", 23.0, 3.0);
    controladorDeAcceso = mock(ControladorDeAcceso.class);

    heladera = new Heladera("heladera",
        40,
        new Ubicacion(0.1, 0.0),
        "kd993j",
        LocalDate.now(),
        new ProveedorPesoSensor(wSensor),
        proveedorTemperaturaSensor,
        new AutorizadorAperturasActual(controladorDeAcceso),
        repoTecnicos,
        null,
        null);
    proveedorTemperaturaSensor.setHeladera(heladera);

    colaboradorHumano = new ColaboradorHumano("Mati",
        "Matias",
        LocalDate.of(1995, 10, 10),
        "Calle Falsa 123",
        new MedioDeContacto(null, "test@gmail.com", null),
        Set.of(FormaDeColaboracionHumana.DONACION_VIANDA),
        TipoDocumento.DNI,
        180924102
    );

    fallaTecnica = new FallaTecnica(colaboradorHumano, LocalDateTime.now(), heladera,
        "La heladera no tiene gas",
        "www.heladera.com"
    );

    //Agregando los tecnicos al repo
    repoTecnicos.agregarTecnico(ricardo);
    repoTecnicos.agregarTecnico(juan);
    repoTecnicos.agregarTecnico(ramon);
  }


  @Test
  void puedoAgregarUnIncidenteYLaHeladeraDejaDeEstarActiva() {
    heladera.nuevoIncidente(new FallaTecnica(colaboradorHumano, LocalDateTime.now(), heladera,"Falla tecnica", "url"));
    assertFalse(heladera.estaActiva());
  }

  @Test
  void laHeladeraEstaActivaSiNoHayIncidentes() {
    assertTrue(heladera.estaActiva());
  }

  @Test
  void seGeneraUnIncidenteCuandoElSensorDePesoLanzaUnaExcepcion() {
    when(wSensor.getWeight("kd993j")).thenThrow(new RuntimeException("Error"));

    heladera.nivelLlenado(); // Operacion que requiere obtener el peso

    assertEquals(1, heladera.getIncidentesActivos().size());
  }

  @Test
  void seGeneraUnIncidenteCuandoHay3MedicionesAltas() {
    proveedorTemperaturaSensor.agregarLectura(24.0);
    proveedorTemperaturaSensor.agregarLectura(25.0);
    proveedorTemperaturaSensor.agregarLectura(123.0);

    assertEquals(1, heladera.getIncidentesActivos().size());
  }

  //quiero testear si efectivamente se le asigna el incidente al tecnico mas cercano

  @Test
  void cuandoTengoUnIncedenteSeAsignaAlTecnicoMasCercano() {
    proveedorTemperaturaSensor.agregarLectura(24.0);
    proveedorTemperaturaSensor.agregarLectura(25.0);
    proveedorTemperaturaSensor.agregarLectura(123.0);

    assertEquals(3, repoTecnicos.obtenerTecnicos().size());
    assertEquals(1, heladera.getIncidentesActivos().size());
    assertEquals(1, juan.getVisitasPendientes().size()); //juan tiene la distancia mas corta
  }


  //Cuando reporto un incidente como usuario, quiero que se le notifique al tecnico mas cercano
  @Test
  void cuandoReportoUnIncedenteSeAsignaAlTecnicoMasCercano() {

    //En el caso en el que se reporte una heladera
    colaboradorHumano.reportarIncidente(fallaTecnica);

    assertEquals(3, repoTecnicos.obtenerTecnicos().size());
    assertEquals(1, heladera.getIncidentesActivos().size());
    assertEquals(1, juan.getVisitasPendientes().size());
    assertEquals("La heladera no tiene gas", juan.getVisitasPendientes().get(0).getDescripcionDelError());
  }

  @Test
  void AlEfectuarUnaVisitaSeTieneQueActivarLaHeladera() {

    //En el caso en el que se reporte una heladera
    colaboradorHumano.reportarIncidente(fallaTecnica);

    assertEquals("La heladera no tiene gas", juan.getVisitasPendientes().get(0).getDescripcionDelError());
    assertFalse(heladera.estaActiva());
    Visita visitaHeladera = new Visita(fallaTecnica, "se ha remplazado una manguera de gas", LocalDateTime.now(), heladera, true);
    juan.registrarVisita(visitaHeladera);
    assertEquals(0, juan.getVisitasPendientes().size());
    assertEquals("se ha remplazado una manguera de gas", juan.getVisitas().get(0).getReporteDeLaVisita());
    assertTrue(heladera.estaActiva());
  }

  @Test
  void HayUnaExcepcionAlEfectuarUnaVisitaQueNoCorresponde() {

    colaboradorHumano.reportarIncidente(fallaTecnica);
    Visita visitaHeladera = new Visita(null, "se ha remplazado una manguera de gas", LocalDateTime.now(), heladera, true);
    assertThrows(VisitaTecnicoException.class,() -> juan.registrarVisita(visitaHeladera));
  }

  @Test
  void SiUnTecnicoSolucionaUnSoloErrorLaHeladeraSigueEstandoInactiva() {
    FallaTecnica fallaTecnica2 = new FallaTecnica(
        colaboradorHumano,
        LocalDateTime.now(),
        heladera,
        "La heladera no tiene aire",
        "instagram.com/uwu"
    );

    //En el caso en el que se reporte una heladera
    colaboradorHumano.reportarIncidente(fallaTecnica);
    colaboradorHumano.reportarIncidente(fallaTecnica2);

    assertEquals(3, repoTecnicos.obtenerTecnicos().size());
    assertEquals(2, heladera.getIncidentesActivos().size());
    assertEquals(2, juan.getVisitasPendientes().size());
    assertEquals("La heladera no tiene aire", juan.getVisitasPendientes().get(1).getDescripcionDelError());
    assertFalse(heladera.estaActiva());
    Visita visitaHeladera = new Visita(fallaTecnica, "se ha remplazado una manguera de aire", LocalDateTime.now(), heladera, true);
    juan.registrarVisita(visitaHeladera);
    assertEquals(1, juan.getVisitasPendientes().size());
    assertEquals(1, heladera.getIncidentesActivos().size());
    assertEquals("se ha remplazado una manguera de aire", juan.getVisitas().get(0).getReporteDeLaVisita());
    assertFalse(heladera.estaActiva());
  }
}