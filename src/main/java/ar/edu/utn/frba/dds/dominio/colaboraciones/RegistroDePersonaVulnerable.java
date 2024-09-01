package ar.edu.utn.frba.dds.dominio.colaboraciones;

import ar.edu.utn.frba.dds.dominio.Colaborador;
import ar.edu.utn.frba.dds.dominio.PersonaVulnerable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class RegistroDePersonaVulnerable extends Colaboracion {
  public static final Double COEFICIENTE_PUNTAJE = 2.0;

  @Transient // TODO
  private PersonaVulnerable personaRegistrada;

  public RegistroDePersonaVulnerable(
      PersonaVulnerable personaRegistrada) {
    this.personaRegistrada = personaRegistrada;
  }

  public RegistroDePersonaVulnerable() {
  }

  @Override
  public Double puntaje() {
    assert personaRegistrada != null;
    return personaRegistrada.mesesActivos() * personaRegistrada.usosTarjeta() * COEFICIENTE_PUNTAJE;
  }

  @Override
  public boolean puedeSerRealizadaPor(Colaborador colaborador) {
    // El colaborador debe tener una dirección registrada
    return colaborador.getDireccion() != null;
  }

  public PersonaVulnerable personaRegistrada() {
    return personaRegistrada;
  }
}
