package ar.edu.utn.frba.dds.dominio;

import javax.persistence.Embeddable;

@Embeddable
public record Ubicacion(Double latitud, Double longitud) {
  // Uso la formula simple, realmente habria que usar la formula de Haversine (pero es quilombo)
  public Double distanciaA(Ubicacion ubicacion) {
    return Math.sqrt(
        Math.pow(this.latitud - ubicacion.latitud(), 2)
            + Math.pow(this.longitud - ubicacion.longitud(), 2)
    );
  }
}
