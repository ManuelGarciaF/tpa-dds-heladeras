package ar.edu.utn.frba.dds;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

public class Vianda {
  private String tipoComida;
  private LocalDate fechaVencimiento;
  private LocalDate fechaDeDonacion;
  private Integer calorias;
  private Integer peso;
  private Boolean fueEntregado = false; // Suponemos que por defecto no fue entregada.

  public Vianda(String tipoComida,
                LocalDate fechaVencimiento,
                Integer calorias,
                Integer peso) {
    this.tipoComida = tipoComida;
    this.fechaVencimiento = requireNonNull(fechaVencimiento);
    this.fechaDeDonacion = LocalDate.now();
    this.peso = peso;
    this.calorias = calorias;
  }

  public LocalDate getFechaVencimiento() {
    return fechaVencimiento;
  }

  public LocalDate getFechaDeDonacion() {
    return fechaDeDonacion;
  }

  public Integer getCalorias() {
    return calorias;
  }

  public Integer getPeso() {
    return peso;
  }

  public Boolean getFueEntregado() {
    return fueEntregado;
  }

  public int semanasFresca() {
    return (int) (fechaVencimiento.toEpochDay() - fechaDeDonacion.toEpochDay()) / 7;
  }
}
