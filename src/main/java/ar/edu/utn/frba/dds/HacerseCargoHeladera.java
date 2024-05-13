package ar.edu.utn.frba.dds;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

public class HacerseCargoHeladera implements Colaboracion {
  private String nombreHeladera;
  private Integer capacidadViandas;
  private Ubicacion ubicacion;

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
  public void realizarColaboracion() {
    Heladera heladeraNueva = new Heladera(
        nombreHeladera,
        capacidadViandas,
        ubicacion);
    MapaHeladeras.instance().agregarHeladera(heladeraNueva);
  }
}

