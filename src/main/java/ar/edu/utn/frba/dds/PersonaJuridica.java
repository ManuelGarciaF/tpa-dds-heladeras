package ar.edu.utn.frba.dds;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PersonaJuridica extends Colaborador {
  private String razonSocial;
  private TipoPersonaJuridica tipo;
  private String rubro;
  private Set<FormaDeColaboracionJuridica> formasDeColaboracion;

  public PersonaJuridica(String razonSocial,
                         TipoPersonaJuridica tipo,
                         String rubro,
                         MedioDeContacto medioDeContacto,
                         String direccion,
                         Set<FormaDeColaboracionJuridica> formasDeColaboracion,
                         Usuario usuario) {
    super(direccion, medioDeContacto, usuario);
    this.razonSocial = requireNonNull(razonSocial);
    this.tipo = requireNonNull(tipo);
    this.rubro = requireNonNull(rubro);
    this.formasDeColaboracion = requireNonNull(formasDeColaboracion);
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

  public Set<FormaDeColaboracionJuridica> getFormasDeColaboracion() {
    return formasDeColaboracion;
  }
  
}
