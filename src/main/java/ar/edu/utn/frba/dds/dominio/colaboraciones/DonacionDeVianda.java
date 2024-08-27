package ar.edu.utn.frba.dds.dominio.colaboraciones;

import ar.edu.utn.frba.dds.dominio.Colaborador;
import ar.edu.utn.frba.dds.dominio.Heladera;
import ar.edu.utn.frba.dds.dominio.Vianda;

public record DonacionDeVianda(Vianda vianda, Heladera heladera) implements Colaboracion {
  public static final double COEFICIENTE_PUNTAJE = 1.5;

  @Override
  public Double puntaje() {
    return vianda.semanasFresca() * COEFICIENTE_PUNTAJE;
  }

  @Override
  public boolean puedeSerRealizadaPor(Colaborador colaborador) {
    return true; // No tiene condiciones para ser realizada
  }
}
