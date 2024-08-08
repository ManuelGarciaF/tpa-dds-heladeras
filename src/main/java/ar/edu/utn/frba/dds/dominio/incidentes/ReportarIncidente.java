package ar.edu.utn.frba.dds.dominio.incidentes;

import ar.edu.utn.frba.dds.dominio.tecnicos.RepoTecnicos;

public class ReportarIncidente implements IncidenteObserver {
  //PARA TESTEAR
  private RepoTecnicos repositorio;
  @Override
  public void avisar(Incidente incidente) {
    //hacer al repositoriosDeTecnicos un singleton??
    repositorio.delegarIncidente(incidente);
  }
}
