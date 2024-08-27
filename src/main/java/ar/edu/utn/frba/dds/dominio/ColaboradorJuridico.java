package ar.edu.utn.frba.dds.dominio;

import static java.util.Objects.requireNonNull;

import java.util.Set;

public class ColaboradorJuridico extends Colaborador {
  private final String razonSocial;
  private final TipoPersonaJuridica tipo;
  private final String rubro;
  private final Set<FormaDeColaboracionJuridica> formasDeColaboracion;

  public ColaboradorJuridico(String razonSocial,
                             TipoPersonaJuridica tipo,
                             String rubro,
                             MedioDeContacto medioDeContacto,
                             String direccion,
                             Set<FormaDeColaboracionJuridica> formasDeColaboracion) {
    super(direccion, medioDeContacto);
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

  @Override
  public boolean esDeDocumento(TipoDocumento tipoDocumento, Integer numeroDocumento) {
    // No tiene documento
    return false;
  }
}
