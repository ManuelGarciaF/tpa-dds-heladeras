package ar.edu.utn.frba.dds.dominio.colaboraciones;

import static java.util.Objects.requireNonNull;

import ar.edu.utn.frba.dds.dominio.Colaborador;
import ar.edu.utn.frba.dds.dominio.PersonaVulnerable;
import java.util.List;

public class RegistroDePersonaVulnerable implements Colaboracion {
  private final PersonaVulnerable personaRegistrada;

  private static final Double COEFICIENTE_PUNTAJE = 2.0;

  public RegistroDePersonaVulnerable(PersonaVulnerable personaRegistrada) {
    this.personaRegistrada = requireNonNull(personaRegistrada);
  }

  @Override
  public Double puntaje() {
    return personaRegistrada.puntajeBaseColaboracion() * COEFICIENTE_PUNTAJE;
  }

  public PersonaVulnerable getpersonaRegistradas() {
    return personaRegistrada;
  }

  @Override
  public boolean puedeSerRealizadaPor(Colaborador colaborador) {
    // El colaborador debe tener una dirección registrada
    return colaborador.getDireccion() != null;
  }
}
