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

  public void colaborar(ColaboracionHumana formaDeColaborar) {
    historialDeColaboraciones.add(formaDeColaborar);
    formaDeColaborar.realizarColaboracion();
  }
}
