package ar.edu.utn.frba.dds.dominio.tecnicos;

import ar.edu.utn.frba.dds.dominio.Heladera;
import ar.edu.utn.frba.dds.dominio.incidentes.Incidente;
import java.time.LocalDateTime;

public class Visita {
  private final Incidente incidenteProblematico;
  private final String reporteDeLaVisita;
  private LocalDateTime fechaDeLaVisita;
  private Heladera heladeraAsistida;
  private String urlDeLaImagen;

  //En ves de heladera se podria poner el numero de serie de la heladera y trabajar con esta o asi esta bien?
  //sacar los incidentes y que se arregle directamente la heladera TODO, para sacar la doble referencia
  public Visita(Incidente incidenteProblematico, String reporteDeLaVisita, LocalDateTime fechaDeLaVisita, Heladera heladeraAsistida, Boolean incidenteSolucionado) {
    this.reporteDeLaVisita = reporteDeLaVisita;
    this.fechaDeLaVisita = fechaDeLaVisita;
    this.heladeraAsistida = heladeraAsistida;
    this.incidenteProblematico = incidenteProblematico;
    //Si el sistema fue solucionado se le borra el incidente de la heladera, esta se va a activar
    //si esta no tiene mas incidentes por resolver
    if(incidenteSolucionado) heladeraAsistida.retirarIncidente(incidenteProblematico);
  }

  public Incidente getIncidenteProblematico() {
    return incidenteProblematico;
  }

  public String getReporteDeLaVisita() {
    return reporteDeLaVisita;
  }
}
