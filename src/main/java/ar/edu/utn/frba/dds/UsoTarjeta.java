package ar.edu.utn.frba.dds;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

public record UsoTarjeta(Heladera heladera, LocalDate fecha) {
  public UsoTarjeta(Heladera heladera, LocalDate fecha) {
    this.heladera = heladera;
    this.fecha = fecha;
  }

  public void incrementarUsosEnHeladera() {
    heladera.incrementarUsos();
  }
}
