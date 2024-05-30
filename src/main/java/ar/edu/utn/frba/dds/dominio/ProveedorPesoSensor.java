package ar.edu.utn.frba.dds.dominio;

import static java.util.Objects.requireNonNull;

import ar.edu.utn.frba.dds.exceptions.LecturaInvalidaException;
import ar.edu.utn.frba.dds.externo.Reading;
import ar.edu.utn.frba.dds.externo.WSensor;

public class ProveedorPesoSensor implements ProveedorPeso {
  private final WSensor api;

  public ProveedorPesoSensor(WSensor api) {
    this.api = requireNonNull(api);
  }

  @Override
  public Double obtenerPesoGramos(String numeroDeSerie) {
    Reading reading = api.getWeight(numeroDeSerie);
    return switch (reading.unit()) {
      case "KG" -> reading.value() * 1000;
      case "lbs" -> reading.value() * 0.45359237 * 1000; // Pasar a kg y multiplicar por 1000
      default -> throw new LecturaInvalidaException(
          "El sensor de temperatura no devolvió un valor válido");
    };
  }
}
