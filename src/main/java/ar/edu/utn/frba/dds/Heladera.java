package ar.edu.utn.frba.dds;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Heladera {
  private String nombre;
  private Integer capacidadViandas;
  private LocalDate fechaCreacion;
  private List<Vianda> viandas;
  private Ubicacion ubicacion;

  public Heladera(String nombre,
                  Integer capacidadViandas,
                  Ubicacion ubicacion) {
    this.nombre = requireNonNull(nombre);
    this.capacidadViandas = requireNonNull(capacidadViandas);
    this.fechaCreacion = LocalDate.now();
    this.viandas = new ArrayList<Vianda>();
    this.ubicacion = requireNonNull(ubicacion);
  }

  public void ingresarViandas(List<Vianda> viandas) {
    if (this.viandas.size() + viandas.size() > this.capacidadViandas) {
      throw new HeladeraException("Las viandas no entran, capacidad: " + this.capacidadViandas);
    }
    this.viandas.addAll(viandas);
  }

  public List<Vianda> sacarViandas(int cantidad) {
    if (cantidad > this.viandas.size()) {
      throw new HeladeraException("No hay suficientes viandas, cantidad: " + this.viandas.size());
    }
    List<Vianda> removidas = new ArrayList<>(this.viandas.subList(0, cantidad));
    this.viandas.subList(0, cantidad).clear();
    return removidas;
  }

  public List<Vianda> listarViandas() {
    return viandas;
  }

  public String getNombre() {
    return nombre;
  }

  public Integer getCapacidadViandas() {
    return capacidadViandas;
  }

  public LocalDate getFechaCreacion() {
    return fechaCreacion;
  }

  public Ubicacion getUbicacion() {
    return ubicacion;
  }
}
