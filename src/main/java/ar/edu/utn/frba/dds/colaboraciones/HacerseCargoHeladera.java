package ar.edu.utn.frba.dds.colaboraciones;

import static java.util.Objects.requireNonNull;

import ar.edu.utn.frba.dds.Heladera;
import ar.edu.utn.frba.dds.MapaHeladeras;
import ar.edu.utn.frba.dds.Ubicacion;

public class HacerseCargoHeladera implements Colaboracion {
  private final String nombreHeladera;
  private final Integer capacidadViandas;
  private final Ubicacion ubicacion;

  // Lo inyectamos para poder mockear el singleton (no se si esta bien)
  private final MapaHeladeras mapaHeladeras;

  private static final Double COEFICIENTE_PUNTAJE = 5.0;

  public HacerseCargoHeladera(String nombreHeladera,
                              Integer capacidadViandas,
                              Ubicacion ubicacion,
                              MapaHeladeras mapaHeladeras) {
    this.nombreHeladera = requireNonNull(nombreHeladera);
    this.capacidadViandas = requireNonNull(capacidadViandas);
    this.ubicacion = requireNonNull(ubicacion);
    this.mapaHeladeras = requireNonNull(mapaHeladeras);
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
    Heladera heladera = mapaHeladeras.buscarHeladera(nombreHeladera);
    return heladera.mesesActivos() * heladera.getUsos() * COEFICIENTE_PUNTAJE;
  }
}

