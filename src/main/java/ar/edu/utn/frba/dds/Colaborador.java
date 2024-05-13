package ar.edu.utn.frba.dds;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

public abstract class Colaborador {
  private String direccion;
  private MedioDeContacto medioDeContacto;
  private List<Colaboracion> historialDeColaboraciones;
  private Usuario usuario;

  public Colaborador(String direccion, MedioDeContacto medioDeContacto, Usuario usuario) {
    this.direccion = requireNonNull(direccion);
    this.medioDeContacto = requireNonNull(medioDeContacto);
    this.usuario = requireNonNull(usuario);
    this.historialDeColaboraciones = new ArrayList<Colaboracion>();
  }

  public List<Colaboracion> getHistorialDeColaboraciones() {
    return historialDeColaboraciones;
  }

  public void colaborar(Colaboracion unaColaboracion) {
    historialDeColaboraciones.add(unaColaboracion);
  }

  public String getDireccion() {
    return direccion;
  }

  public MedioDeContacto getMedioDeContacto() {
    return medioDeContacto;
  }
}
