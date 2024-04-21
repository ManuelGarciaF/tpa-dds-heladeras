package ar.edu.utn.frba.dds;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

public class PersonaVulnerable {
  private String nombre;
  private String domicilio;
  private LocalDate fechaNacimiento;
  private LocalDate fechaRegistro;
  private Integer menoresAcargo;
  private Usuario usuario;

  public PersonaVulnerable(String nombre,
                           String domicilio,
                           LocalDate fechaNacimiento,
                           Integer menoresAcargo,
                           Usuario usuario) {
    this.nombre = requireNonNull(nombre);
    this.domicilio = domicilio;
    this.usuario = requireNonNull(usuario);
    this.fechaNacimiento = requireNonNull(fechaNacimiento);
    this.fechaRegistro = LocalDate.now();
    this.menoresAcargo = requireNonNull(menoresAcargo);
  }

  public String getNombre() {
    return nombre;
  }

  public String getDomicilio() {
    return domicilio;
  }

  public LocalDate getFechaNacimiento() {
    return fechaNacimiento;
  }

  public Integer getMenoresAcargo() {
    return menoresAcargo;
  }
}
