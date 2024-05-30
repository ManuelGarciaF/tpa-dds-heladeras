package ar.edu.utn.frba.dds.dominio;

import static java.util.Objects.requireNonNull;

import ar.edu.utn.frba.dds.dominio.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.exceptions.ColaboracionException;
import java.util.ArrayList;
import java.util.List;

public abstract class Colaborador {
  private final String direccion;
  private final MedioDeContacto medioDeContacto;
  private final List<Colaboracion> historialDeColaboraciones;

  public Colaborador(String direccion, MedioDeContacto medioDeContacto) {
    this.direccion = direccion;
    this.medioDeContacto = requireNonNull(medioDeContacto);
    this.historialDeColaboraciones = new ArrayList<Colaboracion>();
  }

  public List<Colaboracion> getHistorialDeColaboraciones() {
    return historialDeColaboraciones;
  }

  public void colaborar(Colaboracion unaColaboracion) {
    if (!unaColaboracion.puedeSerRealizadaPor(this)) {
      throw new ColaboracionException(
          "Esta colaboración no puede ser realizada por este colaborador");
    }
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
