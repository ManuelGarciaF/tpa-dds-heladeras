package ar.edu.utn.frba.dds;

import static java.util.Objects.requireNonNull;

import java.util.Date;
import java.util.List;

public class DistribucionDeViandas implements ColaboracionHumana {
  private MotivoDeDistribucion motivoDeDistribucion;
  private Heladera heladeraOrigen;
  private Heladera heladeraDestino;
  // este int no me convence, para mi es una lista de viandas y que cuando hagamos el translado de viandas vaya sacando de a una
  private Integer cantidadDeViandas;
  private Date fechaDeLaDistribucion;

  public DistribucionDeViandas(MotivoDeDistribucion motivoDeDistribucion,
                               Date fechaDeLaDistribucion,
                               Integer cantidadDeViandas,
                               Heladera heladeraDestino,
                               Heladera heladeraOrigen) {
    this.motivoDeDistribucion = requireNonNull(motivoDeDistribucion);
    this.fechaDeLaDistribucion = requireNonNull(fechaDeLaDistribucion);
    this.cantidadDeViandas = requireNonNull(cantidadDeViandas);
    this.heladeraDestino = requireNonNull(heladeraDestino);
    this.heladeraOrigen = requireNonNull(heladeraOrigen);
  }

  @Override
  public void realizarColaboracion() {
    List<Vianda> viandas = heladeraOrigen.sacarViandas(cantidadDeViandas);
    heladeraDestino.ingresarViandas(viandas);
  }
}
