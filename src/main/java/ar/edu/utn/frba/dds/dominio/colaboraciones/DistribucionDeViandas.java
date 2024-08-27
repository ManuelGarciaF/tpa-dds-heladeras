package ar.edu.utn.frba.dds.dominio.colaboraciones;

import ar.edu.utn.frba.dds.dominio.Colaborador;
import ar.edu.utn.frba.dds.dominio.Heladera;
import ar.edu.utn.frba.dds.dominio.MotivoDeDistribucion;
import java.time.LocalDate;

public record DistribucionDeViandas(MotivoDeDistribucion motivoDeDistribucion,
                                    LocalDate fechaDeLaDistribucion, Integer cantidadDeViandas,
                                    Heladera heladeraOrigen,
                                    Heladera heladeraDestino) implements Colaboracion {
  public static final Double COEFICIENTE_PUNTAJE = 1.0;

  @Override
  public Double puntaje() {
    return cantidadDeViandas * COEFICIENTE_PUNTAJE;
  }

  @Override
  public boolean puedeSerRealizadaPor(Colaborador colaborador) {
    return true; // No tiene condiciones para ser realizada
  }
}
