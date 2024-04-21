package ar.edu.utn.frba.dds;

import static java.util.Objects.requireNonNull;

public class Ubicacion {
  private int latitud;
  private int longitud;

  public Ubicacion(int latitud, int longitud) {
    this.latitud = requireNonNull(latitud);
    this.longitud = requireNonNull(longitud);
  }

  public int getLatitud() {
    return latitud;
  }

  public int getLongitud() {
    return longitud;
  }
}
