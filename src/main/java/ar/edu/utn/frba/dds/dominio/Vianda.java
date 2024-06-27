package ar.edu.utn.frba.dds.dominio;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Vianda {
  private String tipoComida;
  private LocalDate fechaDeVencimiento;
  private LocalDate fechaDeDonacion;
  private Integer calorias;
  private Integer peso;
  private Boolean fueEntregado = false; // Suponemos que por defecto no fue entregada.

  public Vianda(String tipoComida,
                LocalDate fechaDeVencimiento,
                Integer calorias,
                Integer peso) {
    this.tipoComida = tipoComida;
    this.fechaDeVencimiento = requireNonNull(fechaDeVencimiento);
    this.fechaDeDonacion = LocalDate.now();
    this.peso = peso;
    this.calorias = calorias;
  }

  public LocalDate getFechaDeVencimiento() {
    return fechaDeVencimiento;
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
    return (int) ChronoUnit.WEEKS.between(fechaDeDonacion, fechaDeVencimiento);
  }
}
