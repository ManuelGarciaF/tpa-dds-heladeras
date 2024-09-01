package ar.edu.utn.frba.dds.dominio.colaboraciones;

import ar.edu.utn.frba.dds.dominio.Colaborador;
import ar.edu.utn.frba.dds.dominio.Heladera;
import ar.edu.utn.frba.dds.dominio.Vianda;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class DonacionDeVianda extends Colaboracion {
  public static final double COEFICIENTE_PUNTAJE = 1.5;

  @Transient // TODO
  private Vianda vianda;

  @Transient // TODO
  private Heladera heladera;

  public DonacionDeVianda(Vianda vianda, Heladera heladera) {
    this.vianda = vianda;
    this.heladera = heladera;
  }

  public DonacionDeVianda() {
  }

  @Override
  public Double puntaje() {
    assert vianda != null;
    return vianda.semanasFresca() * COEFICIENTE_PUNTAJE;
  }

  @Override
  public boolean puedeSerRealizadaPor(Colaborador colaborador) {
    return true; // No tiene condiciones para ser realizada
  }

  public Vianda vianda() {
    return vianda;
  }

  public Heladera heladera() {
    return heladera;
  }
}
