package ar.edu.utn.frba.dds;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;

public class PersonaVulnerable {
  private String nombre;
  private LocalDate fechaNacimiento;
  private LocalDate fechaRegistro;
  private String domicilio;
  private Integer menoresAcargo;
  private String codigoTarjeta;
  private List<UsoTarjeta> usosTarjeta;

  public PersonaVulnerable(String nombre,
                           String domicilio,
                           LocalDate fechaNacimiento,
                           Integer menoresAcargo,
                           String codigoTarjeta,
                           List<UsoTarjeta> usosTarjeta) {
    this.nombre = requireNonNull(nombre);
    this.domicilio = domicilio;
    this.fechaNacimiento = requireNonNull(fechaNacimiento);
    this.fechaRegistro = LocalDate.now();
    this.menoresAcargo = requireNonNull(menoresAcargo);
    this.codigoTarjeta = requireNonNull(codigoTarjeta);
    this.usosTarjeta = requireNonNull(usosTarjeta);
  }

  public Integer usosMaximosDiarios() {
    return 4 + menoresAcargo * 2;
  }

  public Boolean tieneUsosDisponibles() {
    return usosHoy() < usosMaximosDiarios();
  }

  private int usosHoy() {
    return (int) usosTarjeta.stream().filter(u -> u.getFecha().equals(LocalDate.now())).count();
  }

  public void agregarUsoTarjeta(UsoTarjeta usoTarjeta) {
    this.usosTarjeta.add(usoTarjeta);
    usoTarjeta.incrementarUsosEnHeladera();
  }

  public Integer puntajeBaseColaboracion() {
    return mesesActivos() * usosTarjeta.size();
  }

  private Integer mesesActivos() {
    return (int) (fechaRegistro.toEpochDay() - fechaNacimiento.toEpochDay()) / 30;
  }

}
