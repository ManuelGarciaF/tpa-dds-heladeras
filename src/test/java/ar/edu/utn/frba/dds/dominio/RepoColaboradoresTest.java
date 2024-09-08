package ar.edu.utn.frba.dds.dominio;

import static org.junit.jupiter.api.Assertions.*;

import ar.edu.utn.frba.dds.exceptions.CsvInvalidoException;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class RepoColaboradoresTest implements SimplePersistenceTest {

  @Test
  void puedeAgregarUnColaborador() {
    var colaborador = new ColaboradorHumano("Mati",
        "Matias",
        LocalDate.of(1995, 10, 10),
        "Calle Falsa 123",
        new MedioDeContacto(null, "mati@gmail.com", null),
        null,
        TipoDocumento.DNI,
        180924102,
        null
    );
    RepoColaboradores.getInstance().agregarColaborador(colaborador);
    assertTrue(RepoColaboradores.getInstance().getColaboradores().contains(colaborador));
  }

  @Test
  void puedoCargarColaboradoresDesdeCsv() {
    RepoColaboradores.getInstance().cargarDeCsv("src/test/resources/cargaMasivaCorrecta.csv");
    assertEquals(20, RepoColaboradores.getInstance().getColaboradores().size());
  }

  @Test
  void noPuedoCargarColaboradoresDesdeUnArchivoConErrores() {
    assertThrows(CsvInvalidoException.class,
       () -> RepoColaboradores.getInstance().cargarDeCsv("src/test/resources/cargaMasivaError.csv"));
    // Checkear que no hubo cambios en el sistema
    assertEquals(0, RepoColaboradores.getInstance().getColaboradores().size());
  }
}