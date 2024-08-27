package ar.edu.utn.frba.dds.dominio;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RepoSolicitudesTest {
  private ColaboradorHumano colaboradorHumano;
  private  RepoSolicitudes repoSolicitudes;

  @BeforeEach
  void setUp() {
    colaboradorHumano = new ColaboradorHumano("Mati",
        "Matias",
        LocalDate.of(1995, 10, 10),
        "Calle Falsa 123",
        new MedioDeContacto(null, "test@gmail.com", null),
        Set.of(FormaDeColaboracionHumana.DONACION_VIANDA),
        TipoDocumento.DNI,
        180924102,
        null
    );

    repoSolicitudes = new RepoSolicitudes();
  }

  @Test
  void puedoAgregarYAceptarUnaSolicitud() {
    var solicitud = new SolicitudTarjetaColaborador(colaboradorHumano, LocalDate.now());
    repoSolicitudes.agregarSolicitud(solicitud);

    assertNull(colaboradorHumano.getTarjetaColaborador());

    var tarjeta = new TarjetaColaborador("1234");
    repoSolicitudes.aceptar(solicitud, tarjeta);

    assertTrue(repoSolicitudes.getAceptadas().contains(solicitud));
    assertEquals(tarjeta, colaboradorHumano.getTarjetaColaborador());
  }

  @Test
  void puedoAgregarYRechazarUnaSolicitud() {
    var solicitud = new SolicitudTarjetaColaborador(colaboradorHumano, LocalDate.now());
    repoSolicitudes.agregarSolicitud(solicitud);

    repoSolicitudes.rechazar(solicitud);

    assertTrue(repoSolicitudes.getRechazadas().contains(solicitud));
    assertNull(colaboradorHumano.getTarjetaColaborador());
  }
}