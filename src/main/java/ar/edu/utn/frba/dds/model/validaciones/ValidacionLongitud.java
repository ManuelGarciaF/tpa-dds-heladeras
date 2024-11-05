package ar.edu.utn.frba.dds.model.validaciones;

import static java.util.Objects.requireNonNull;

public class ValidacionLongitud implements ValidacionContrasenia {
  private final Integer minimo;

  public ValidacionLongitud(Integer minimo) {
    this.minimo = requireNonNull(minimo);
  }

  @Override
  public Boolean validar(String contrasenia) {
    return contrasenia.length() >= minimo;
  }
}
