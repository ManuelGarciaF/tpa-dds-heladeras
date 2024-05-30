package ar.edu.utn.frba.dds.dominio;

public enum NivelLlenado {
  BAJO, MEDIO, ALTO;

  public static NivelLlenado of(Double pesoActual, Double pesoMaximo) {
    if (pesoActual <= pesoMaximo * 0.30) {
      return BAJO;
    } else if (pesoActual <= pesoMaximo * 0.70) {
      return MEDIO;
    } else {
      return ALTO;
    }
  }
}
