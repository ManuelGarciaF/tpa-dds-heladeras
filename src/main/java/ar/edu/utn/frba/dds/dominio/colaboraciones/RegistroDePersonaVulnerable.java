package ar.edu.utn.frba.dds.dominio.colaboraciones;

import ar.edu.utn.frba.dds.dominio.Colaborador;
import ar.edu.utn.frba.dds.dominio.PersonaVulnerable;

public record RegistroDePersonaVulnerable(PersonaVulnerable personaRegistrada) implements Colaboracion {
  public static final Double COEFICIENTE_PUNTAJE = 2.0;

  @Override
  public Double puntaje() {
    return personaRegistrada.mesesActivos() * personaRegistrada.usosTarjeta() * COEFICIENTE_PUNTAJE;
  }

  @Override
  public boolean puedeSerRealizadaPor(Colaborador colaborador) {
    // El colaborador debe tener una dirección registrada
    return colaborador.getDireccion() != null;
  }
}
