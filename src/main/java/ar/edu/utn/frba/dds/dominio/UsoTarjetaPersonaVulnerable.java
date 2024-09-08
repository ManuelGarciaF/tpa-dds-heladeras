package ar.edu.utn.frba.dds.dominio;

import java.time.LocalDate;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public final class UsoTarjetaPersonaVulnerable {
  @ManyToOne(cascade = javax.persistence.CascadeType.ALL)
  private TarjetaPersonaVulnerable tarjetaPersonaVulnerable;

  private LocalDate fecha;

  public UsoTarjetaPersonaVulnerable(
      TarjetaPersonaVulnerable tarjetaPersonaVulnerable,
      LocalDate fecha) {
    this.tarjetaPersonaVulnerable = tarjetaPersonaVulnerable;
    this.fecha = fecha;
  }

  public UsoTarjetaPersonaVulnerable() {
  }

  public TarjetaPersonaVulnerable tarjetaPersonaVulnerable() {
    return tarjetaPersonaVulnerable;
  }

  public LocalDate fecha() {
    return fecha;
  }
}
