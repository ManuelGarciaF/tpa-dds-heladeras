package ar.edu.utn.frba.dds.colaboraciones;

import static java.util.Objects.requireNonNull;

import ar.edu.utn.frba.dds.PersonaVulnerable;
import java.util.List;

public class RegistroDePersonaVulnerable implements Colaboracion {
  private final List<PersonaVulnerable> personasRegistradas;

  private static final Double COEFICIENTE_PUNTAJE = 2.0;

  public RegistroDePersonaVulnerable(List<PersonaVulnerable> personasRegistradas) {
    this.personasRegistradas = requireNonNull(personasRegistradas);
  }

  @Override
  public Double puntaje() {
    Double puntajeBase = personasRegistradas.stream()
        .mapToDouble(PersonaVulnerable::puntajeBaseColaboracion)
        .sum();

    return puntajeBase * COEFICIENTE_PUNTAJE;
  }

  public List<PersonaVulnerable> getPersonasRegistradas() {
    return personasRegistradas;
  }
}
