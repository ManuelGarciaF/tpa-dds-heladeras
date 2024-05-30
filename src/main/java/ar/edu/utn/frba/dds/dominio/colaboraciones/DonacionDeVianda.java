package ar.edu.utn.frba.dds.dominio.colaboraciones;

import static java.util.Objects.requireNonNull;

import ar.edu.utn.frba.dds.dominio.Heladera;
import ar.edu.utn.frba.dds.dominio.Vianda;
import java.util.List;

public class DonacionDeVianda implements Colaboracion {
  private List<Vianda> viandas;
  private Heladera heladera;

  private static final double COEFICIENTE_PUNTAJE = 1.5;

  public DonacionDeVianda(List<Vianda> viandas, Heladera heladera) {
    this.viandas = requireNonNull(viandas);
    this.heladera = requireNonNull(heladera);
  }

  public Heladera getHeladera() {
    return heladera;
  }

  @Override
  public Double puntaje() {
    return viandas.size()
        * viandas.stream().mapToInt(Vianda::semanasFresca).sum()
        * COEFICIENTE_PUNTAJE;
  }
}
