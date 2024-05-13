package ar.edu.utn.frba.dds;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class PersonaHumana extends Colaborador {
  private String nombre;
  private String apellido;
  private LocalDate fechaDeNacimiento;
  private Set<FormaDeColaboracionHumana> formasDeColaboracion;

  public PersonaHumana(String nombre,
                       String apellido,
                       LocalDate fechaDeNacimiento,
                       String direccion,
                       MedioDeContacto medioDeContacto,
                       Set<FormaDeColaboracionHumana> formasDeColaboracion,
                       Usuario usuario) {
    super(direccion, medioDeContacto, usuario);
    this.nombre = requireNonNull(nombre);
    this.apellido = requireNonNull(apellido);
    this.fechaDeNacimiento = fechaDeNacimiento;
    this.formasDeColaboracion = requireNonNull(formasDeColaboracion);
  }

  public String getNombre() {
    return nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public LocalDate getFechaDeNacimiento() {
    return fechaDeNacimiento;
  }

  public Set<FormaDeColaboracionHumana> getFormasDeColaboracion() {
    return formasDeColaboracion;
  }

}
