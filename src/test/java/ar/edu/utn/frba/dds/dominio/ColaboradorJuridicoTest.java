package ar.edu.utn.frba.dds.dominio;

import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.edu.utn.frba.dds.dominio.colaboraciones.DonacionDeDinero;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ColaboradorJuridicoTest {

  ColaboradorJuridico colaboradorJuridico;

  @BeforeEach
  void setUp() {
    colaboradorJuridico = new ColaboradorJuridico("Mati SRL",
        TipoPersonaJuridica.EMPRESA,
        "Automotores",
        new MedioDeContacto(null, "test@gmail.com", null),
        "Triunvirato 55894",
        Set.of(FormaDeColaboracionJuridica.DONACION_DINERO));
  }

  @Test
  void puedeAgregarUnaColaboracion() {
    var colaboracion = new DonacionDeDinero(420, false, null);
    colaboradorJuridico.colaborar(colaboracion);
    assertTrue(colaboradorJuridico.getHistorialDeColaboraciones().contains(colaboracion));
  }
}

