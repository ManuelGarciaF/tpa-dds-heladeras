package ar.edu.utn.frba.dds.dominio.colaboraciones;

import static java.util.Objects.requireNonNull;

import ar.edu.utn.frba.dds.dominio.Colaborador;
import ar.edu.utn.frba.dds.dominio.Heladera;
import ar.edu.utn.frba.dds.dominio.Vianda;
import java.util.List;

public class DonacionDeVianda implements Colaboracion {
  private Vianda vianda;
  private Heladera heladera;

  public static final double COEFICIENTE_PUNTAJE = 1.5;

  public DonacionDeVianda(Vianda vianda, Heladera heladera) {
    this.vianda = vianda;
    this.heladera = heladera;
  }

  public Heladera getHeladera() {
    return heladera;
  }

  @Override
  public Double puntaje() {
    return vianda.semanasFresca() * COEFICIENTE_PUNTAJE;
  }

  @Override
  public boolean puedeSerRealizadaPor(Colaborador colaborador) {
    return true; // No tiene condiciones para ser realizada
  }
}
