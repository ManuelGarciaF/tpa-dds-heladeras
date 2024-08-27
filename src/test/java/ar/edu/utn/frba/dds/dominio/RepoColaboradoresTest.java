package ar.edu.utn.frba.dds.dominio;

import static org.junit.jupiter.api.Assertions.*;

import ar.edu.utn.frba.dds.exceptions.CsvInvalidoException;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RepoColaboradoresTest {
  RepoColaboradores repoColaboradores;

  @BeforeEach
  void setUp() {
    repoColaboradores = new RepoColaboradores();
  }

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
    repoColaboradores.agregarColaborador(colaborador);
    assertTrue(repoColaboradores.getColaboradores().contains(colaborador));
  }

  @Test
  void puedoCargarColaboradoresDesdeCsv() {
    repoColaboradores.cargarDeCsv("src/test/resources/cargaMasivaCorrecta.csv");
    assertEquals(20, repoColaboradores.getColaboradores().size());
  }

  @Test
  void noPuedoCargarColaboradoresDesdeUnArchivoConErrores() {
    assertThrows(CsvInvalidoException.class,
       () -> repoColaboradores.cargarDeCsv("src/test/resources/cargaMasivaError.csv"));
    // Checkear que no hubo cambios en el sistema
    assertEquals(0, repoColaboradores.getColaboradores().size());
  }
}