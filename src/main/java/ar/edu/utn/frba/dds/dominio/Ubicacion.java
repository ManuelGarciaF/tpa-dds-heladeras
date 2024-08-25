package ar.edu.utn.frba.dds.dominio;

import static java.util.Objects.requireNonNull;

public class Ubicacion {
  private Double latitud;
  private Double longitud;

  public Ubicacion(Double latitud, Double longitud) {
    this.latitud = requireNonNull(latitud);
    this.longitud = requireNonNull(longitud);
  }

  public Double getLatitud() {
    return latitud;
  }

  public Double getLongitud() {
    return longitud;
  }

  public Double distanciaA(Ubicacion ubicacion) {
    return Math.sqrt(Math.pow(this.latitud - ubicacion.getLatitud(), 2) + Math.pow(this.longitud - ubicacion.getLongitud(), 2));
  }
}
