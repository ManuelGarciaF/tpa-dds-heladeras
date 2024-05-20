package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonaJuridicaTest {

  PersonaJuridica personaJuridica;

  @BeforeEach
  void setUp() {
    personaJuridica = new PersonaJuridica("Mati SRL",
        TipoPersonaJuridica.EMPRESA,
        "Automotores",
        new MedioDeContacto(null, "test@gmail.com", null),
        "Triunvirato 55894",
        Set.of(FormaDeColaboracionJuridica.DONACION_DINERO));
  }

  @Test
  void puedeAgregarUnaColaboracion() {
    var colaboracion = new DonacionDeDinero(420, false, null);
    personaJuridica.colaborar(colaboracion);
    assertTrue(personaJuridica.getHistorialDeColaboraciones().contains(colaboracion));
  }
}

