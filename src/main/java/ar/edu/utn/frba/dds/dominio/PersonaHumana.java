package ar.edu.utn.frba.dds.dominio;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class PersonaHumana extends Colaborador {
  private String nombre;
  private String apellido;
  private LocalDate fechaDeNacimiento;
  private Set<FormaDeColaboracionHumana> formasDeColaboracion;
  private TipoDocumento tipoDocumento;
  private Integer numeroDocumento;

  public PersonaHumana(String nombre,
                       String apellido,
                       LocalDate fechaDeNacimiento,
                       String direccion,
                       MedioDeContacto medioDeContacto,
                       Set<FormaDeColaboracionHumana> formasDeColaboracion,
                       TipoDocumento tipoDocumento,
                       Integer numeroDocumento) {
    super(direccion, medioDeContacto);
    this.nombre = requireNonNull(nombre);
    this.apellido = requireNonNull(apellido);
    this.fechaDeNacimiento = fechaDeNacimiento;
    this.formasDeColaboracion = requireNonNull(formasDeColaboracion);
    this.tipoDocumento = requireNonNull(tipoDocumento);
    this.numeroDocumento = requireNonNull(numeroDocumento);
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

  @Override
  public boolean esDeDocumento(TipoDocumento tipoDocumento, Integer numeroDocumento) {
    return this.tipoDocumento.equals(tipoDocumento) && this.numeroDocumento.equals(numeroDocumento);
  }
}
