package ar.edu.utn.frba.dds.cargamasiva;

import ar.edu.utn.frba.dds.MotivoDeDistribucion;
import ar.edu.utn.frba.dds.TipoDocumento;
import ar.edu.utn.frba.dds.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.colaboraciones.DistribucionDeViandas;
import ar.edu.utn.frba.dds.colaboraciones.DonacionDeDinero;
import ar.edu.utn.frba.dds.colaboraciones.DonacionDeViandaHistorica;
import ar.edu.utn.frba.dds.colaboraciones.RegistroDePersonaVulnerableHistorico;
import ar.edu.utn.frba.dds.exceptions.CsvInvalidoException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

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
}