package ar.edu.utn.frba.dds;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

public class HacerseCargoHeladera implements Colaboracion {
  private String nombreHeladera;
  private Integer capacidadViandas;
  private Ubicacion ubicacion;

  private static final Double COEFICIENTE_PUNTAJE = 5.0;

  public HacerseCargoHeladera(String nombreHeladera,
                              Integer capacidadViandas,
                              Ubicacion ubicacion) {
    this.nombreHeladera = requireNonNull(nombreHeladera);
    this.capacidadViandas = requireNonNull(capacidadViandas);
    this.ubicacion = requireNonNull(ubicacion);
  }

  public String getNombreHeladera() {
    return nombreHeladera;
  }

  public Integer getCapacidadViandas() {
    return capacidadViandas;
  }

  public Ubicacion getUbicacion() {
    return ubicacion;
  }

  @Override
  public Double puntaje() {
    Heladera heladera = MapaHeladeras.instance().buscarHeladera(nombreHeladera);
    return heladera.mesesActivos() * heladera.getUsos() * COEFICIENTE_PUNTAJE;
  }
}

