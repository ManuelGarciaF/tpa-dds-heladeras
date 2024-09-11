package ar.edu.utn.frba.dds.dominio;

import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public final class Ubicacion {
  private Double latitud;
  private Double longitud;

  public Ubicacion(Double latitud, Double longitud) {
    this.latitud = latitud;
    this.longitud = longitud;
  }

  public Ubicacion() {
  }

  // Uso la formula simple, realmente habria que usar la formula de Haversine (pero es quilombo)
  public Double distanciaA(Ubicacion ubicacion) {
    return Math.sqrt(
        Math.pow(this.latitud - ubicacion.latitud(), 2)
            + Math.pow(this.longitud - ubicacion.longitud(), 2)
    );
  }

  public Double latitud() {
    return latitud;
  }

  public Double longitud() {
    return longitud;
  }
}
