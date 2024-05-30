package ar.edu.utn.frba.dds.dominio;

import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.edu.utn.frba.dds.dominio.colaboraciones.DonacionDeDinero;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ColaboradorHumanoTest {

  ColaboradorHumano colaboradorHumano;

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
  }

  @Test
  void puedeAgregarUnaColaboracion() {
    var colaboracion = new DonacionDeDinero(420, false, null);
    colaboradorHumano.colaborar(colaboracion);
    assertTrue(colaboradorHumano.getHistorialDeColaboraciones().contains(colaboracion));
  }
}

