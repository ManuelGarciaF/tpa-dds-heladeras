package ar.edu.utn.frba.dds.model;

import java.time.LocalDate;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public final class UsoTarjetaPersonaVulnerable {
  @ManyToOne
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
