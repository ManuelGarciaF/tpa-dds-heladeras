package ar.edu.utn.frba.dds.model.colaboraciones;

import ar.edu.utn.frba.dds.model.Colaborador;
import ar.edu.utn.frba.dds.model.Heladera;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class HacerseCargoHeladera extends Colaboracion {
  public static final Double COEFICIENTE_PUNTAJE = 5.0;

  @OneToOne
  private Heladera heladera;

  public HacerseCargoHeladera(Heladera heladera) {
    this.heladera = heladera;
  }

  public HacerseCargoHeladera() {
  }

  @Override
  public Double puntaje() {
    assert heladera != null;
    return heladera.mesesActivos() * heladera.cantidadUsos() * COEFICIENTE_PUNTAJE;
  }

  @Override
  public boolean puedeSerRealizadaPor(Colaborador colaborador) {
    return true; // No tiene condiciones para ser realizada
  }

  public Heladera heladera() {
    return heladera;
  }
}

