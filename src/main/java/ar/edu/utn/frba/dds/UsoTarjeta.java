package ar.edu.utn.frba.dds;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

public class UsoTarjeta {
  private Heladera heladera;
  private LocalDate fecha;

  public UsoTarjeta(Heladera heladera, LocalDate fecha) {
    this.heladera = requireNonNull(heladera);
    this.fecha = requireNonNull(fecha);
  }

  public Heladera getHeladera() {
    return heladera;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public void incrementarUsosEnHeladera() {
    heladera.incrementarUsos();
  }
}
