package ar.edu.utn.frba.dds.dominio;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Vianda {
  private final String tipoComida;
  private final LocalDate fechaDeVencimiento;
  private final LocalDate fechaDeDonacion;
  private final Integer calorias;
  private final Integer peso;

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

  public String getTipoComida() {
    return tipoComida;
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

  public void marcarEntregado() {
    this.fueEntregado = true;
  }


  public int semanasFresca() {
    return (int) ChronoUnit.WEEKS.between(fechaDeDonacion, fechaDeVencimiento);
  }
}
