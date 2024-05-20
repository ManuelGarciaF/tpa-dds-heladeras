package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonaHumanaTest {

  PersonaHumana personaHumana;

  @BeforeEach
  void setUp() {
    personaHumana = new PersonaHumana("Mati",
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
    personaHumana.colaborar(colaboracion);
    assertTrue(personaHumana.getHistorialDeColaboraciones().contains(colaboracion));
  }
}

