package ar.edu.utn.frba.dds.dominio.colaboraciones;

import static java.util.Objects.requireNonNull;

import ar.edu.utn.frba.dds.dominio.Colaborador;
import ar.edu.utn.frba.dds.dominio.Heladera;

public record HacerseCargoHeladera(Heladera heladera) implements Colaboracion {
  public static final Double COEFICIENTE_PUNTAJE = 5.0;

  @Override
  public Double puntaje() {
    return heladera.mesesActivos() * heladera.cantidadUsos() * COEFICIENTE_PUNTAJE;
  }

  @Override
  public boolean puedeSerRealizadaPor(Colaborador colaborador) {
    return true; // No tiene condiciones para ser realizada
  }
}

