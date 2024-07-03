package ar.edu.utn.frba.dds.dominio;

import java.time.LocalDate;

public record UsoTarjetaPersonaVulnerable(
    TarjetaPersonaVulnerable tarjetaPersonaVulnerable,
    LocalDate fecha) {
}
