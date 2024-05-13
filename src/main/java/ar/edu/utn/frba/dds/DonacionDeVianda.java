package ar.edu.utn.frba.dds;

import static java.util.Objects.requireNonNull;

import java.util.List;

public class DonacionDeVianda implements Colaboracion {
  private List<Vianda> viandas;
  private Heladera heladera;

  public DonacionDeVianda(List<Vianda> viandas, Heladera heladera) {
    this.viandas = requireNonNull(viandas);
    this.heladera = requireNonNull(heladera);
  }

  public Heladera getHeladera() {
    return heladera;
  }
}
