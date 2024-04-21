package ar.edu.utn.frba.dds;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PersonaJuridica {
  private String razonSocial;
  private TipoPersonaJuridica tipo;
  private String rubro;
  private MedioDeContacto medioDeContacto;
  private String direccion;
  private Set<FormaDeColaboracionJuridica> formasDeColaboracion;
  private List<ColaboracionJuridica> historialDeColaboraciones;
  private Usuario usuario;

  public PersonaJuridica(String razonSocial,
                         TipoPersonaJuridica tipo,
                         String rubro,
                         MedioDeContacto medioDeContacto,
                         String direccion,
                         Set<FormaDeColaboracionJuridica> formasDeColaboracion,
                         Usuario usuario) {
    this.razonSocial = requireNonNull(razonSocial);
    this.tipo = requireNonNull(tipo);
    this.rubro = requireNonNull(rubro);
    this.medioDeContacto = requireNonNull(medioDeContacto);
    this.direccion = direccion;
    this.formasDeColaboracion = requireNonNull(formasDeColaboracion);
    this.usuario = requireNonNull(usuario);
    this.historialDeColaboraciones = new ArrayList<>();
  }

  public String getRazonSocial() {
    return razonSocial;
  }

  public TipoPersonaJuridica getTipo() {
    return tipo;
  }

  public String getRubro() {
    return rubro;
  }

  public MedioDeContacto getMedioDeContacto() {
    return medioDeContacto;
  }

  public String getDireccion() {
    return direccion;
  }

  public Set<FormaDeColaboracionJuridica> getFormasDeColaboracion() {
    return formasDeColaboracion;
  }

  public List<ColaboracionJuridica> getHistorialDeColaboraciones() {
    return historialDeColaboraciones;
  }

  public void colaborar(ColaboracionJuridica unaColaboracion) {
    unaColaboracion.realizarColaboracion();
    historialDeColaboraciones.add(unaColaboracion);
  }
}
