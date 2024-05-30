package ar.edu.utn.frba.dds.dominio;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

public record UsoTarjeta(Heladera heladera, LocalDate fecha) {
  public void incrementarUsosEnHeladera() {
    heladera.incrementarUsos();
  }
}
