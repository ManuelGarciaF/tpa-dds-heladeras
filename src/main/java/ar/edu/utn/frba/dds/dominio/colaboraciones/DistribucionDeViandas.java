package ar.edu.utn.frba.dds.dominio.colaboraciones;

import static java.util.Objects.requireNonNull;

import ar.edu.utn.frba.dds.dominio.Colaborador;
import ar.edu.utn.frba.dds.dominio.Heladera;
import ar.edu.utn.frba.dds.dominio.MotivoDeDistribucion;
import java.time.LocalDate;

public class DistribucionDeViandas implements Colaboracion {
  private MotivoDeDistribucion motivoDeDistribucion;
  private Heladera heladeraOrigen;
  private Heladera heladeraDestino;
  private Integer cantidadDeViandas;
  private LocalDate fechaDeLaDistribucion;

  private static final Double COEFICIENTE_PUNTAJE = 1.0;

  public DistribucionDeViandas(MotivoDeDistribucion motivoDeDistribucion,
                               LocalDate fechaDeLaDistribucion,
                               Integer cantidadDeViandas,
                               Heladera heladeraOrigen,
                               Heladera heladeraDestino) {
    this.motivoDeDistribucion = requireNonNull(motivoDeDistribucion);
    this.fechaDeLaDistribucion = requireNonNull(fechaDeLaDistribucion);
    this.cantidadDeViandas = requireNonNull(cantidadDeViandas);
    this.heladeraOrigen = heladeraOrigen;
    this.heladeraDestino = heladeraDestino;
  }

  public MotivoDeDistribucion getMotivoDeDistribucion() {
    return motivoDeDistribucion;
  }

  public Heladera getHeladeraOrigen() {
    return heladeraOrigen;
  }

  public Heladera getHeladeraDestino() {
    return heladeraDestino;
  }

  public Integer getCantidadDeViandas() {
    return cantidadDeViandas;
  }

  public LocalDate getFechaDeLaDistribucion() {
    return fechaDeLaDistribucion;
  }

  @Override
  public Double puntaje() {
    return cantidadDeViandas * COEFICIENTE_PUNTAJE;
  }

  @Override
  public boolean puedeSerRealizadaPor(Colaborador colaborador) {
    return true; // No tiene condiciones para ser realizada
  }
}
