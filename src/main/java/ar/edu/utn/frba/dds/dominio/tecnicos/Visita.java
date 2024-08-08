package ar.edu.utn.frba.dds.dominio.tecnicos;

import ar.edu.utn.frba.dds.dominio.Heladera;
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

  public Visita(String reporteDeLaVisita, LocalDateTime fechaDeLaVisita, Heladera heladeraAsistida, Boolean incidenteSolucionado) {
    this.reporteDeLaVisita = reporteDeLaVisita;
    this.fechaDeLaVisita = fechaDeLaVisita;
    this.heladeraAsistida = heladeraAsistida;
    this.incidenteSolucionado = incidenteSolucionado;

    //Si el sistema fue solucionado se le borra el incidente???
    if(incidenteSolucionado) activarHeladera(heladeraAsistida);
  }

  public void activarHeladera(Heladera heladera) {
    //remover de los incidentes activos a esta heladera (consultar si puedo hacer un repo de incidentes)
  }

}
