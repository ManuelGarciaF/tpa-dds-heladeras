package ar.edu.utn.frba.dds.dominio.colaboraciones;

import ar.edu.utn.frba.dds.dominio.Colaborador;
import ar.edu.utn.frba.dds.dominio.Heladera;
import ar.edu.utn.frba.dds.dominio.MotivoDeDistribucion;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class DistribucionDeViandas extends Colaboracion {
  public static final Double COEFICIENTE_PUNTAJE = 1.0;

  @Enumerated(EnumType.STRING)
  private MotivoDeDistribucion motivoDeDistribucion;

  private LocalDate fechaDeLaDistribucion;
  private Integer cantidadDeViandas;

  @ManyToOne
  private Heladera heladeraOrigen;
  @ManyToOne
  private Heladera heladeraDestino;

  public DistribucionDeViandas(MotivoDeDistribucion motivoDeDistribucion,
                               LocalDate fechaDeLaDistribucion,
                               Integer cantidadDeViandas,
                               Heladera heladeraOrigen,
                               Heladera heladeraDestino) {
    this.motivoDeDistribucion = motivoDeDistribucion;
    this.fechaDeLaDistribucion = fechaDeLaDistribucion;
    this.cantidadDeViandas = cantidadDeViandas;
    this.heladeraOrigen = heladeraOrigen;
    this.heladeraDestino = heladeraDestino;
  }

  public DistribucionDeViandas() {
  }

  @Override
  public Double puntaje() {
    return cantidadDeViandas * COEFICIENTE_PUNTAJE;
  }

  @Override
  public boolean puedeSerRealizadaPor(Colaborador colaborador) {
    return true; // No tiene condiciones para ser realizada
  }

  public MotivoDeDistribucion motivoDeDistribucion() {
    return motivoDeDistribucion;
  }

  public LocalDate fechaDeLaDistribucion() {
    return fechaDeLaDistribucion;
  }

  public Integer cantidadDeViandas() {
    return cantidadDeViandas;
  }

  public Heladera heladeraOrigen() {
    return heladeraOrigen;
  }

  public Heladera heladeraDestino() {
    return heladeraDestino;
  }
}
