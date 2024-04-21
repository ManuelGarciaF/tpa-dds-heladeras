package ar.edu.utn.frba.dds;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Heladera {
  //ubicacion
  private String nombre;
  private String capacidadViandas;
  private Date fechaCreacion;
  private List<Vianda> viandas;
  private Ubicacion ubicacion;

  //ojo, aca la lista está vacía
  public Heladera(String nombre, String capacidadViandas, Date fechaCreacion, Ubicacion ubicacion) {
    this.nombre = requireNonNull(nombre);
    this.capacidadViandas = requireNonNull(capacidadViandas);
    this.fechaCreacion = requireNonNull(fechaCreacion);
    this.viandas = new ArrayList<Vianda>();
    this.ubicacion = requireNonNull(ubicacion);
  }

  public void ingresarViandas(List<Vianda> viandas) {
    this.viandas.addAll(viandas);
  }

  public List<Vianda> sacarViandas(int cantidad) {
    List<Vianda> removidas = new ArrayList<>(this.viandas.subList(0, cantidad));
    this.viandas.subList(0, cantidad).clear();
    return removidas;
  }

  public String getNombre() {
    return nombre;
  }

  public String getCapacidadViandas() {
    return capacidadViandas;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public Ubicacion getUbicacion() {
    return ubicacion;
  }
}
