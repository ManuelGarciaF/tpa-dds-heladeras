package ar.edu.utn.frba.dds.cargamasiva;

import ar.edu.utn.frba.dds.dominio.MotivoDeDistribucion;
import ar.edu.utn.frba.dds.dominio.TipoDocumento;
import ar.edu.utn.frba.dds.dominio.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.dominio.colaboraciones.DistribucionDeViandas;
import ar.edu.utn.frba.dds.dominio.colaboraciones.DonacionDeDinero;
import ar.edu.utn.frba.dds.dominio.colaboraciones.DonacionDeViandaHistorica;
import ar.edu.utn.frba.dds.dominio.colaboraciones.RegistroDePersonaVulnerableHistorico;
import ar.edu.utn.frba.dds.exceptions.CsvInvalidoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public record FilaCsv(
    TipoDocumento tipoDocumento,
    Integer numeroDocumento,
    String nombre,
    String apellido,
    String mail,
    LocalDate fechaColaboracion,
    FormaColaboracionCsv formaColaboracion,
    Integer cantidad
) {
  private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  public Colaboracion crearColaboracion() {
    return switch (formaColaboracion) {
      case DINERO -> new DonacionDeDinero(cantidad, false, null);
      case DONACION_VIANDAS -> new DonacionDeViandaHistorica(cantidad);
      case REDISTRIBUCION_VIANDAS -> new DistribucionDeViandas(
          MotivoDeDistribucion.NO_ESPECIFICADO,
          fechaColaboracion,
          cantidad,
          null,
          null);
      case ENTREGA_TARJETAS -> new RegistroDePersonaVulnerableHistorico(cantidad);
    };
  }

  public static FilaCsv fromString(String fila) {
    String[] campos = fila.split(",");

    if (campos.length != 8 || Arrays.stream(campos).anyMatch(String::isBlank)) {
      throw new CsvInvalidoException("El archivo de colaboraciones tiene un formato incorrecto");
    }

    try {
      return new FilaCsv(
          TipoDocumento.fromString(campos[0]),
          Integer.parseInt(campos[1]),
          campos[2],
          campos[3],
          campos[4],
          LocalDate.parse(campos[5], FORMATO_FECHA),
          FormaColaboracionCsv.fromString(campos[6]),
          Integer.parseInt(campos[7])
      );
    } catch (IllegalArgumentException | DateTimeException e) {
      throw new CsvInvalidoException("El archivo de colaboraciones tiene un formato incorrecto", e);
    }
  }

  public static List<FilaCsv> fromCsv(String path) {
    List<FilaCsv> filas = new ArrayList<>();
    try {
      Stream<String> stream = Files.lines(Paths.get(path));
      stream.forEach(linea -> {
        FilaCsv fila = FilaCsv.fromString(linea);
        filas.add(fila);
      });
      stream.close();
    } catch (IOException e) {
      throw new RuntimeException("Hubo un error al leer el archivo de colaboraciones: " + path, e);
    }
    return filas;
  }
}
