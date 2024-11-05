package ar.edu.utn.frba.dds.model;

public enum TipoDocumento {
  LC,  // Libreta civica
  LE,  // Libreta de enrolamiento
  DNI; // Documento nacional de identidad

  public static TipoDocumento fromString(String tipoDocumento) {
    return switch (tipoDocumento) {
      case "LC" -> TipoDocumento.LC;
      case "LE" -> TipoDocumento.LE;
      case "DNI" -> TipoDocumento.DNI;
      default -> throw new IllegalArgumentException("Tipo de documento no válido");
    };
  }
}
