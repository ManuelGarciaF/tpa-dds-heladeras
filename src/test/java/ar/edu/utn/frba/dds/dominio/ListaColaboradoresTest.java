package ar.edu.utn.frba.dds.dominio;

import static org.junit.jupiter.api.Assertions.*;

import ar.edu.utn.frba.dds.exceptions.CsvInvalidoException;
import java.time.LocalDate;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ListaColaboradoresTest {
  ListaColaboradores listaColaboradores;

  @BeforeEach
  void setUp() {
    listaColaboradores = new ListaColaboradores();
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
        180924102
    );
    listaColaboradores.agregarColaborador(colaborador);
    assertTrue(listaColaboradores.getColaboradores().contains(colaborador));
  }

  @Test
  void puedoCargarColaboradoresDesdeCsv() {
    listaColaboradores.cargarDeCsv("src/test/resources/cargaMasivaCorrecta.csv");
    assertEquals(20, listaColaboradores.getColaboradores().size());
  }

  @Test
  void noPuedoCargarColaboradoresDesdeUnArchivoConErrores() {
    assertThrows(CsvInvalidoException.class,
       () -> listaColaboradores.cargarDeCsv("src/test/resources/cargaMasivaError.csv"));
    // Checkear que no hubo cambios en el sistema
    assertEquals(0, listaColaboradores.getColaboradores().size());
  }
}