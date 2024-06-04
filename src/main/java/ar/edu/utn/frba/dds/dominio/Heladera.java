package ar.edu.utn.frba.dds.dominio;

import static java.util.Objects.requireNonNull;

import ar.edu.utn.frba.dds.exceptions.HeladeraException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Heladera {
  private static final Double PESO_ESTANDAR_VIANDA_GRAMOS = 400.0;

  private final String nombre;
  private final Integer capacidadViandas;
  private final LocalDate fechaCreacion;
  private final List<Vianda> viandas;
  private final Ubicacion ubicacion;
  private final String numeroDeSerie;
  private final Integer temperaturaMaximaAceptable;
  private final ProveedorPeso proveedorPeso;
  private final ProveedorTemperatura proveedorTemperatura;
  private Integer usos;

  public Heladera(String nombre,
                  Integer capacidadViandas,
                  Ubicacion ubicacion,
                  String numeroDeSerie,
                  Integer temperaturaMaximaAceptable,
                  ProveedorPeso proveedorPeso,
                  ProveedorTemperatura proveedorTemperatura) {
    this.nombre = requireNonNull(nombre);
    this.capacidadViandas = requireNonNull(capacidadViandas);
    this.ubicacion = requireNonNull(ubicacion);
    this.numeroDeSerie = requireNonNull(numeroDeSerie);
    this.temperaturaMaximaAceptable = requireNonNull(temperaturaMaximaAceptable);
    this.proveedorPeso = proveedorPeso;
    this.proveedorTemperatura = proveedorTemperatura;

    this.fechaCreacion = LocalDate.now();
    this.viandas = new ArrayList<Vianda>();
    this.usos = 0;
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

  public void incrementarUsos() {
    this.usos++;
  }

  public boolean tieneNombre(String nombreHeladera) {
    return this.nombre.equals(nombreHeladera);
  }

  public Integer getUsos() {
    return this.usos;
  }

  public Ubicacion getUbicacion() {
    return this.ubicacion;
  }

  public Double mesesActivos() {
    return (double) (LocalDate.now().toEpochDay() - fechaCreacion.toEpochDay()) / 30;
  }

  public NivelLlenado nivelLlenado() {
    Double capacidadTotalGramos = capacidadViandas * PESO_ESTANDAR_VIANDA_GRAMOS;
    return NivelLlenado.of(proveedorPeso.obtenerPesoGramos(numeroDeSerie), capacidadTotalGramos);
  }

  public boolean requiereAtencion() {
    List<Double> temperaturas = proveedorTemperatura.ultimas3Temperaturas();

    // Si tenemos al menos 3 temperaturas y todas son mayores a la maxima
    return temperaturas.size() >= 3
        && temperaturas.stream().allMatch(temperatura -> temperatura > temperaturaMaximaAceptable);
  }
}
