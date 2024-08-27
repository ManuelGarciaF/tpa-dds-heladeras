package ar.edu.utn.frba.dds.dominio.tecnicos;

import ar.edu.utn.frba.dds.dominio.Heladera;
import java.time.LocalDateTime;

public record Visita(String reporteDeLaVisita, LocalDateTime fechaDeLaVisita,
                     Heladera heladeraAsistida,
                     String urlDeLaImagen) {
}
