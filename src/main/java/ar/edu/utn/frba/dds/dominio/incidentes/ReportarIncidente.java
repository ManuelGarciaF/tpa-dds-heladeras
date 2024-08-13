package ar.edu.utn.frba.dds.dominio.incidentes;

import ar.edu.utn.frba.dds.dominio.tecnicos.RepoTecnicos;

//interpreto que esto se hace cuando un usuario reporta una heladera
public class ReportarIncidente implements IncidenteObserver {
  //PARA TESTEAR puede ser un singleton?
  private RepoTecnicos repositorio;

  public ReportarIncidente(RepoTecnicos repositorio) {
    this.repositorio = repositorio;
  }

  //voy a suponer que esta es la clase colaborador, y voy a reportar una heladera con cierto tipo de error
  public void reportarIncidente(FallaTecnica fallaTecnica){
    //reportarIncidente.avisar(fallaTecnica);
    //this.avisar(fallaTecnica);
    fallaTecnica.getHeladeraDefectuosa().nuevoIncidente(fallaTecnica);
  }

  @Override
  public void avisar(Incidente incidente) {
    repositorio.delegarIncidente(incidente);
  }
}
