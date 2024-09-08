package ar.edu.utn.frba.dds.dominio.tecnicos;

import ar.edu.utn.frba.dds.dominio.Heladera;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Embeddable
public final class Visita {
  private String reporteDeLaVisita;
  private LocalDateTime fechaDeLaVisita;
  private String urlDeLaImagen;

  @ManyToOne
  private Heladera heladeraAsistida;

  public Visita(String reporteDeLaVisita, LocalDateTime fechaDeLaVisita,
                Heladera heladeraAsistida,
                String urlDeLaImagen) {
    this.reporteDeLaVisita = reporteDeLaVisita;
    this.fechaDeLaVisita = fechaDeLaVisita;
    this.heladeraAsistida = heladeraAsistida;
    this.urlDeLaImagen = urlDeLaImagen;
  }

  public Visita() {
  }

  public String reporteDeLaVisita() {
    return reporteDeLaVisita;
  }

  public LocalDateTime fechaDeLaVisita() {
    return fechaDeLaVisita;
  }

  public Heladera heladeraAsistida() {
    return heladeraAsistida;
  }

  public String urlDeLaImagen() {
    return urlDeLaImagen;
  }
}
