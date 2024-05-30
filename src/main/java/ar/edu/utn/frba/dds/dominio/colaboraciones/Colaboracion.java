package ar.edu.utn.frba.dds.dominio.colaboraciones;

import ar.edu.utn.frba.dds.dominio.Colaborador;

public interface Colaboracion {
  Double puntaje();

  boolean puedeSerRealizadaPor(Colaborador colaborador);
}
