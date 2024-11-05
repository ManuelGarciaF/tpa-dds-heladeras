package ar.edu.utn.frba.dds.model;

import static java.util.Objects.requireNonNull;

import ar.edu.utn.frba.dds.PersistentEntity;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.persistence.Entity;

@Entity
public class Vianda extends PersistentEntity {
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

  public Vianda() {
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
