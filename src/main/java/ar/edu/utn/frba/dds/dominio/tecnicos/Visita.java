package ar.edu.utn.frba.dds.dominio.tecnicos;

import ar.edu.utn.frba.dds.dominio.Heladera;
import java.time.LocalDateTime;

public class Visita {

  private final String reporteDeLaVisita;
  private final LocalDateTime fechaDeLaVisita;
  private final Heladera heladeraAsistida;
  private final String urlDeLaImagen;

  public Visita(String reporteDeLaVisita,
                LocalDateTime fechaDeLaVisita,
                Heladera heladeraAsistida,
                String urlDeLaImagen) {
    this.reporteDeLaVisita = reporteDeLaVisita;
    this.fechaDeLaVisita = fechaDeLaVisita;
    this.heladeraAsistida = heladeraAsistida;
    this.urlDeLaImagen = urlDeLaImagen;
  }

  public String getReporteDeLaVisita() {
    return reporteDeLaVisita;
  }

  public Heladera getHeladeraAsistida() {
    return heladeraAsistida;
  }

  public LocalDateTime getFechaDeLaVisita() {
    return fechaDeLaVisita;
  }

  public String getUrlDeLaImagen() {
    return urlDeLaImagen;
  }

}
