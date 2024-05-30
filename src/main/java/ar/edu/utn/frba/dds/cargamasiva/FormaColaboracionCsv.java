package ar.edu.utn.frba.dds.cargamasiva;

public enum FormaColaboracionCsv {
  DINERO,
  DONACION_VIANDAS,
  REDISTRIBUCION_VIANDAS,
  ENTREGA_TARJETAS;

  public static FormaColaboracionCsv fromString(String formaColaboracion) {
    return switch (formaColaboracion) {
      case "DINERO" -> DINERO;
      case "DONACION_VIANDAS" -> DONACION_VIANDAS;
      case "REDISTRIBUCION_VIANDAS" -> REDISTRIBUCION_VIANDAS;
      case "ENTREGA_TARJETAS" -> ENTREGA_TARJETAS;
      default -> throw new IllegalArgumentException("Forma de colaboración inválida");
    };
  }
}
