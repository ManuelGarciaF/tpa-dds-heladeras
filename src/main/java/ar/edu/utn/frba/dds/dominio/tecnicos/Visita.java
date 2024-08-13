package ar.edu.utn.frba.dds.dominio.tecnicos;

import ar.edu.utn.frba.dds.dominio.Heladera;
import ar.edu.utn.frba.dds.dominio.incidentes.Incidente;
import java.time.LocalDateTime;

public class Visita {
  /*La visita se efectua cuando el tecnico acude a la heladera
  la información de la visita debe estar compuesta por:
  - un breve parrafo comentando lo que hizo
  - fecha de la visita
  - heladera a la que visito (si es que tenemos un registro de visitas en el tecnico)
  - una foto (link o archivo)
  - indicar si puedo solucionar el incidente
  * */
  //TODO: graficarlo en el DC

  private String reporteDeLaVisita;
  private LocalDateTime fechaDeLaVisita;
  private Heladera heladeraAsistida;
  private String urlDeLaImagen; // podriamos crear una clase foto?? (asi el usuario puede subir un link o subir la foto en el sistema)
  private Boolean incidenteSolucionado;
  private Incidente incidenteProblematico;

  public Visita(Incidente incidenteProblematico, String reporteDeLaVisita, LocalDateTime fechaDeLaVisita, Heladera heladeraAsistida, Boolean incidenteSolucionado) {
    this.reporteDeLaVisita = reporteDeLaVisita;
    this.fechaDeLaVisita = fechaDeLaVisita;
    this.heladeraAsistida = heladeraAsistida;
    this.incidenteSolucionado = incidenteSolucionado;
    this.incidenteProblematico = incidenteProblematico;
    //Si el sistema fue solucionado se le borra el incidente de la heladera, esta se va a activar
    //cuando se solucionen todos los incidentes que tiene
    if(incidenteSolucionado) heladeraAsistida.retirarIncidente(incidenteProblematico);
  }

  public Incidente getIncidenteProblematico() {
    return incidenteProblematico;
  }

  public String getReporteDeLaVisita() {
    return reporteDeLaVisita;
  }
}
