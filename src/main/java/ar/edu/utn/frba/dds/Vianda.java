package ar.edu.utn.frba.dds;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

public class Vianda {
  private String tipoComida;
  private LocalDate fechaVencimiento;
  private LocalDate fechaDeDonacion;
  private PersonaHumana colaborador;
  private Integer calorias;
  private Integer peso;
  private Boolean fueEntregado = false; // Suponemos que por defecto no fue creada.

  public Vianda(LocalDate fechaVencimiento,
                LocalDate fechaDeDonacion,
                Heladera heladeraDondeEstaAlmacenada,
                PersonaHumana colaborador,
                Integer calorias,
                Integer peso) {
    this.fechaVencimiento = requireNonNull(fechaVencimiento);
    this.fechaDeDonacion = requireNonNull(fechaDeDonacion);
    this.colaborador = requireNonNull(colaborador);
    this.peso = peso;
    this.calorias = calorias;
  }

  public LocalDate getFechaVencimiento() {
    return fechaVencimiento;
  }

  public LocalDate getFechaDeDonacion() {
    return fechaDeDonacion;
  }

  public PersonaHumana getColaborador() {
    return colaborador;
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
}
