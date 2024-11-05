package ar.edu.utn.frba.dds.cargamasiva;

import static org.junit.jupiter.api.Assertions.*;

import ar.edu.utn.frba.dds.model.TipoDocumento;
import ar.edu.utn.frba.dds.exceptions.CsvInvalidoException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class FilaCsvTest {
  @Test
  void parsea1LineaCorrectamente() {
    String linea = "LE,13129390,Amalia,Stonehouse,astonehouse2@theglobeandmail.com,"
        + "14/02/2006,ENTREGA_TARJETAS,36";

    FilaCsv fila = FilaCsv.fromString(linea);

    assertEquals(TipoDocumento.LE, fila.tipoDocumento());
    assertEquals(13129390, fila.numeroDocumento());
    assertEquals("Amalia", fila.nombre());
    assertEquals("Stonehouse", fila.apellido());
    assertEquals("astonehouse2@theglobeandmail.com", fila.mail());
    assertEquals(LocalDate.of(2006, 2, 14), fila.fechaColaboracion());
    assertEquals(FormaColaboracionCsv.ENTREGA_TARJETAS, fila.formaColaboracion());
    assertEquals(36, fila.cantidad());
  }

  @Test
  void fallaSiFaltaUnCampo() {
    String linea = "LE,13129390,Amalia,Stonehouse,astonehouse2@theglobeandmail.com,"
        + "14/02/2006,ENTREGA_TARJETAS,";

    assertThrows(CsvInvalidoException.class, () -> FilaCsv.fromString(linea));
  }

  @Test
  void fallaSiUnCampoEsInvalido() {
    String linea = "Pepito,13129390,Amalia,Stonehouse,astonehouse2@theglobeandmail.com,"
        + "14/02/2006,ENTREGA_TARJETAS,26";

    assertThrows(CsvInvalidoException.class, () -> FilaCsv.fromString(linea));
  }

  @Test
  void puedeLeerUnArchivoCompleto() {
    List<FilaCsv> filas = FilaCsv.fromCsv("src/test/resources/cargaMasivaCorrecta.csv");
    assertFalse(filas.isEmpty());
  }

  @Test
  void fallaAlLeerUnArchivoConErrores() {
    assertThrows(CsvInvalidoException.class,
        () -> FilaCsv.fromCsv("src/test/resources/cargaMasivaError.csv"));
  }
}