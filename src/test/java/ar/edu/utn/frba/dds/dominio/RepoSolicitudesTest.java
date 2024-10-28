package ar.edu.utn.frba.dds.dominio;

import static org.junit.jupiter.api.Assertions.*;

import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RepoSolicitudesTest implements SimplePersistenceTest {
  private ColaboradorHumano colaboradorHumano;

  @BeforeEach
  void setUp() {
    colaboradorHumano = new ColaboradorHumano("Mati",
        "Matias",
        LocalDate.of(1995, 10, 10),
        "Calle Falsa 123",
        new MedioDeContacto(null, "test@gmail.com", null),
        new Usuario("contraseniaMati", "mati"),
        Set.of(FormaDeColaboracionHumana.DONACION_VIANDA),
        TipoDocumento.DNI,
        180924102,
        null
    );
    RepoColaboradores.getInstance().agregarColaborador(colaboradorHumano);
  }

  @Test
  void puedoAgregarYAceptarUnaSolicitud() {
    var solicitud = new SolicitudTarjetaColaborador(colaboradorHumano, LocalDate.now());
    RepoSolicitudes.getInstance().agregarSolicitud(solicitud);

    assertNull(colaboradorHumano.getTarjetaColaborador());

    var tarjeta = new TarjetaColaborador("1234");
    RepoSolicitudes.getInstance().aceptar(solicitud, tarjeta);

    assertTrue(RepoSolicitudes.getInstance().getAceptadas().contains(solicitud));
    assertEquals(tarjeta, colaboradorHumano.getTarjetaColaborador());
  }

  @Test
  void puedoAgregarYRechazarUnaSolicitud() {
    var solicitud = new SolicitudTarjetaColaborador(colaboradorHumano, LocalDate.now());
    RepoSolicitudes.getInstance().agregarSolicitud(solicitud);

    RepoSolicitudes.getInstance().rechazar(solicitud);

    assertTrue(RepoSolicitudes.getInstance().getRechazadas().contains(solicitud));
    assertNull(colaboradorHumano.getTarjetaColaborador());
  }
}