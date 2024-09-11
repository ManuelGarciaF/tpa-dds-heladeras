package ar.edu.utn.frba.dds.dominio;

import ar.edu.utn.frba.dds.PersistentEntity;
import java.time.LocalDate;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public final class UsoTarjetaPersonaVulnerable extends PersistentEntity {
  @Embedded
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
