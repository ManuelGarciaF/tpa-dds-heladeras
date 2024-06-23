package ar.edu.utn.frba.dds.dominio.colaboraciones;

import static java.util.Objects.requireNonNull;

import ar.edu.utn.frba.dds.dominio.Colaborador;
import ar.edu.utn.frba.dds.dominio.Heladera;

public class HacerseCargoHeladera implements Colaboracion {
  private final Heladera heladera;

  private static final Double COEFICIENTE_PUNTAJE = 5.0;

  public HacerseCargoHeladera(Heladera heladera) {
    this.heladera = requireNonNull(heladera);
  }

  public Heladera getHeladera() {
    return heladera;
  }

  @Override
  public Double puntaje() {
    return heladera.mesesActivos() * heladera.cantidadUsos() * COEFICIENTE_PUNTAJE;
  }

  @Override
  public boolean puedeSerRealizadaPor(Colaborador colaborador) {
    return true; // No tiene condiciones para ser realizada
  }
}

