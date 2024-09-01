package ar.edu.utn.frba.dds.dominio;

import java.time.LocalDate;
import javax.persistence.Embeddable;

@Embeddable
public record UsoTarjetaPersonaVulnerable(
    TarjetaPersonaVulnerable tarjetaPersonaVulnerable,
    LocalDate fecha) {
}
