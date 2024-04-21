package ar.edu.utn.frba.dds;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class PersonaHumana {
  private String nombre;
  private String apellido;
  private Date fechaDeNacimiento;
  private String direccion;
  private MedioDeContacto medioDeContacto;

  private Set<FormaDeColaboracionHumana> formasDeColaboracion;
  private List<ColaboracionHumana> historialDeColaboraciones;

  public PersonaHumana(String nombre,
                       String apellido,
                       Date fechaDeNacimiento,
                       String direccion,
                       MedioDeContacto medioDeContacto,
                       Set<FormaDeColaboracionHumana> formasDeColaboracion) {
    this.nombre = requireNonNull(nombre);
    this.apellido = requireNonNull(apellido);
    this.fechaDeNacimiento = fechaDeNacimiento;
    this.direccion = direccion;
    this.medioDeContacto = requireNonNull(medioDeContacto);
    this.formasDeColaboracion = requireNonNull(formasDeColaboracion);
    this.historialDeColaboraciones = new ArrayList<>();
  }

  public String getNombre() {
    return nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public Date getFechaDeNacimiento() {
    return fechaDeNacimiento;
  }

  public String getDireccion() {
    return direccion;
  }

  public MedioDeContacto getMedioDeContacto() {
    return medioDeContacto;
  }

  public Set<FormaDeColaboracionHumana> getFormasDeColaboracion() {
    return formasDeColaboracion;
  }

  public List<ColaboracionHumana> getHistorialDeColaboraciones() {
    return historialDeColaboraciones;
  }

  public void colaborar(ColaboracionHumana unaColaboracion) {
    unaColaboracion.realizarColaboracion();
    historialDeColaboraciones.add(unaColaboracion);
  }
}
