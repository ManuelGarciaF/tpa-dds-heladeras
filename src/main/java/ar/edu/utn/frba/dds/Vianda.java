package ar.edu.utn.frba.dds;

import static java.util.Objects.requireNonNull;

import java.util.Date;

public class Vianda {
  private String tipoComida;
  private Date fechaVencimiento;
  private Date fechaDeDonacion;
  private PersonaHumana colaborador;
  private Integer calorias;
  private Integer peso;
  private Boolean fueEntregado = false; // Suponemos que por defecto no fue creada.

  public Vianda(Date fechaVencimiento,
                Date fechaDeDonacion,
                Heladera heladeraDondeEstaAlmacenada,
                PersonaHumana colaborador,
                int calorias,
                int peso) {
    this.fechaVencimiento = requireNonNull(fechaVencimiento);
    this.fechaDeDonacion = requireNonNull(fechaDeDonacion);
    this.colaborador = requireNonNull(colaborador);
    this.peso = peso;
    this.calorias = calorias;
  }

  public Date getFechaVencimiento() {
    return fechaVencimiento;
  }

  public Date getFechaDeDonacion() {
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
