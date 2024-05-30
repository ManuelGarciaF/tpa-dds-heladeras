package ar.edu.utn.frba.dds.dominio;

import static java.util.Objects.requireNonNull;

import ar.edu.utn.frba.dds.dominio.colaboraciones.Colaboracion;
import java.util.ArrayList;
import java.util.List;

public abstract class Colaborador {
  private final String direccion;
  private final MedioDeContacto medioDeContacto;
  private final List<Colaboracion> historialDeColaboraciones;

  public Colaborador(String direccion, MedioDeContacto medioDeContacto) {
    this.direccion = requireNonNull(direccion);
    this.medioDeContacto = requireNonNull(medioDeContacto);
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

  public Double puntaje() {
    return historialDeColaboraciones.stream()
        .mapToDouble(Colaboracion::puntaje).sum();
  }

  public abstract boolean esDeDocumento(TipoDocumento tipoDocumento, Integer numeroDocumento);
}
