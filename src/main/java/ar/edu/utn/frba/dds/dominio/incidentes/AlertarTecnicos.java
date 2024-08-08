package ar.edu.utn.frba.dds.dominio.incidentes;

import ar.edu.utn.frba.dds.dominio.tecnicos.RepoTecnicos;

public class AlertarTecnicos implements IncidenteObserver {
  @Override
  public void avisar(Incidente incidente) {
    //TODO: llamar al repo de tecnicos, averiguar cual esta mas cerca y avisarle (que tenga una lista de incidentes
    // pendientes o algo por el estilos)
  }
}
