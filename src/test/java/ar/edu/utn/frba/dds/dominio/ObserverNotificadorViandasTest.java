package ar.edu.utn.frba.dds.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.dominio.incidentes.FallaTecnica;
import ar.edu.utn.frba.dds.dominio.notificacionesHeladera.NotificacionHeladeraHandler;
import ar.edu.utn.frba.dds.dominio.tecnicos.RepoTecnicos;
import ar.edu.utn.frba.dds.dominio.tecnicos.Tecnico;
import ar.edu.utn.frba.dds.dominio.tecnicos.Visita;
import ar.edu.utn.frba.dds.exceptions.VisitaTecnicoException;
import ar.edu.utn.frba.dds.externo.ControladorDeAcceso;
import ar.edu.utn.frba.dds.externo.LSensor;
import ar.edu.utn.frba.dds.externo.TSensor;
import ar.edu.utn.frba.dds.externo.WSensor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


//TODO: HAY QUE HACER UN SETUP DE LOS OBSERVERS
class ObserverNotificadorViandasTest {
  private Heladera heladera;
  private TSensor tSensor;
  private WSensor wSensor;
  private ProveedorTemperaturaSensor proveedorTemperaturaSensor;
  private ControladorDeAcceso controladorDeAcceso;
  private ColaboradorHumano colaboradorHumano;
  private ColaboradorHumano colaboradorHumano2;
  private RepoTecnicos repoTecnicos = new RepoTecnicos();
  private Tecnico ricardo = new Tecnico("Ricardo Flores", new Ubicacion(0.3, 0.6));
  private Tecnico juan = new Tecnico("Juan Perez", new Ubicacion(0.3, 0.1));
  private Tecnico ramon = new Tecnico("Ramon Castillo", new Ubicacion(0.2, 0.6));
  private FallaTecnica fallaTecnica;
  private LSensor lSensor;
  private NotificacionHeladeraHandler notificacionHeladeraHandler;

  @BeforeEach
  void setUp() {
    tSensor = mock(TSensor.class);
    wSensor = mock(WSensor.class);
    lSensor = mock(LSensor.class);
    notificacionHeladeraHandler = new NotificacionHeladeraHandler();
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
        new ProveedorCantidadDeViandasSensor(lSensor),
        notificacionHeladeraHandler);
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

    colaboradorHumano2 = new ColaboradorHumano("Ger",
        "Cavillio",
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
  void unUsuarioPuedeSuscribirseAUnaHeladeraYPonerCiertaCantidadDeViandas() {
    List<Vianda> viandas = new ArrayList<>();
    viandas.add(new Vianda("Milanesa de pollo", LocalDate.now(), 50, 200));
    viandas.add(new Vianda("Milanesa vegana", LocalDate.now(), 50000, 200));
    viandas.add(new Vianda("Milanesa de carne", LocalDate.now(), 50, 200));
    // un chequeo x las dudas
    assertEquals(3, viandas.size());
    // Hago que un colaborador se suscriba a la heladera y que me avise cuando haya 2 o menos viandas
    colaboradorHumano.suscribirseAHeladeraConCantidadDeViandas(heladera, 2);
    // Chequeo que se haya creado ese observer
    assertEquals(1, colaboradorHumano.getAlertasInteresadasPorElUsuario().size());
    // Ingreso las viandas
    heladera.ingresarViandas(viandas);
    // Chequeo que el sensor haya detectado el ingreso de esas viandas
    assertEquals(3, heladera.getCantidadDeViandas());
    // Cuando las saco tambien chequeo lo mismo, y me tendria que quedar una sola vianda, por ende activar la alerta
    heladera.sacarViandas(2);
    assertEquals(1, heladera.getCantidadDeViandas());
    //test, en realidad acá tendria que haber un coso de alertas enviadas usando las opciones que nos dieron en el enunciado
    assertTrue(colaboradorHumano.getAlertasEnviadas().contains("hey"));
  }

  @Test
  void VariosUsuariosSePuedenSuscribirseAUnaHeladeraYPonerCiertaCantidadDeViandas() {
    List<Vianda> viandas = new ArrayList<>();
    viandas.add(new Vianda("Milanesa de pollo", LocalDate.now(), 50, 200));
    viandas.add(new Vianda("Milanesa vegana", LocalDate.now(), 50000, 200));
    viandas.add(new Vianda("Milanesa de carne", LocalDate.now(), 50, 200));
    viandas.add(new Vianda("Milanesa de carne", LocalDate.now(), 50, 200));
    viandas.add(new Vianda("Milanesa de carne", LocalDate.now(), 50, 200));
    viandas.add(new Vianda("Milanesa de carne", LocalDate.now(), 50, 200));
    assertEquals(6, viandas.size());

    // Hago que un colaborador se suscriba a la heladera y que me avise cuando haya 2 o menos viandas
    colaboradorHumano.suscribirseAHeladeraConCantidadDeViandas(heladera, 2);
    assertEquals(1, colaboradorHumano.getAlertasInteresadasPorElUsuario().size());

    // Hago que un segundo colaborador se suscriba a la misma heladera y me avise cuando haya 4 o mas viandas
    colaboradorHumano2.suscribirseAHeladeraConCantidadDeViandas(heladera,4);
    assertEquals(1, colaboradorHumano2.getAlertasInteresadasPorElUsuario().size());

    // Ingreso las viandas
    heladera.ingresarViandas(viandas);
    assertEquals(6, heladera.getCantidadDeViandas());

    // Cuando las saco tambien chequeo lo mismo, y me tendria que quedar una sola vianda, por ende activar la alerta del 2 cola
    heladera.sacarViandas(2);
    assertEquals(4, heladera.getCantidadDeViandas());
    assertTrue(colaboradorHumano2.getAlertasEnviadas().contains("hey"));

    // Cuando las saco tambien chequeo lo mismo, y me tendria que quedar una sola vianda, por ende activar la alerta del 2 cola
    heladera.sacarViandas(3);
    assertEquals(1, heladera.getCantidadDeViandas());
    assertTrue(colaboradorHumano.getAlertasEnviadas().contains("hey"));
  }
}