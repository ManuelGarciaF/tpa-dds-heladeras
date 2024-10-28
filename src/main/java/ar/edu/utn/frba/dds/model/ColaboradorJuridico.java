package ar.edu.utn.frba.dds.model;

import static java.util.Objects.requireNonNull;

import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class ColaboradorJuridico extends Colaborador {
  private String razonSocial;

  @Enumerated(EnumType.STRING)
  private TipoPersonaJuridica tipo;

  private String rubro;

  @ElementCollection
  @Enumerated(EnumType.STRING)
  private Set<FormaDeColaboracionJuridica> formasDeColaboracion;

  public ColaboradorJuridico(String razonSocial,
                             TipoPersonaJuridica tipo,
                             String rubro,
                             MedioDeContacto medioDeContacto,
                             Usuario usuario,
                             String direccion,
                             Set<FormaDeColaboracionJuridica> formasDeColaboracion) {
    super(direccion, medioDeContacto, usuario);
    this.razonSocial = requireNonNull(razonSocial);
    this.tipo = requireNonNull(tipo);
    this.rubro = requireNonNull(rubro);
    this.formasDeColaboracion = requireNonNull(formasDeColaboracion);
  }

  public ColaboradorJuridico() {
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

  @Override
  public boolean esDeDocumento(TipoDocumento tipoDocumento, Integer numeroDocumento) {
    // No tiene documento
    return false;
  }
}
