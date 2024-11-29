package ar.edu.utn.frba.dds.model.sensoresheladera;

import static java.util.Objects.requireNonNull;

import ar.edu.utn.frba.dds.exceptions.HeladeraException;
import ar.edu.utn.frba.dds.exceptions.LecturaInvalidaException;
import ar.edu.utn.frba.dds.externo.Reading;
import ar.edu.utn.frba.dds.externo.WSensor;
import ar.edu.utn.frba.dds.model.Heladera;
import ar.edu.utn.frba.dds.model.incidentes.AlertaFallaConexion;
import ar.edu.utn.frba.dds.model.incidentes.TipoDeFalla;
import java.time.LocalDateTime;

public class ProveedorPesoSensor implements ProveedorPeso {
  private final WSensor api;

  public ProveedorPesoSensor(WSensor api) {
    this.api = requireNonNull(api);
  }

  @Override
  public Double obtenerPesoGramos(Heladera heladera) {
    try {
      Reading reading = api.getWeight(heladera.getNumeroDeSerie());
      return switch (reading.unit()) {
        case "KG" -> reading.value() * 1000;
        case "lbs" -> reading.value() * 0.45359237 * 1000; // Pasar a kg y multiplicar por 1000
        default -> {
          throw new LecturaInvalidaException(
              "El sensor de temperatura no devolvió un valor válido"
          );
        }
      };
    } catch (RuntimeException e) { // No sabemos el tipo
      heladera.nuevoIncidente(
          new AlertaFallaConexion(LocalDateTime.now(), TipoDeFalla.SENSOR_DE_PESO)
      );
      throw new HeladeraException("Fallo el sensor de peso"/*, e*/);
    }
  }

  // Entrega 3
  private Heladera heladera;

  public void suscribirHeladera(Heladera heladera) {
    this.heladera = heladera;
  }

  public void desuscribirHeladera() {
    heladera = null;
  }


}
