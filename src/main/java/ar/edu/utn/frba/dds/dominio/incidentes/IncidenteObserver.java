package ar.edu.utn.frba.dds.dominio.incidentes;

public interface IncidenteObserver {
  public void avisarTemperatura(/* firma 1 */);
  public void avisarFallaTecnica(/*firma 2*/);
  public void avisarFallConexion(/*firma 3*/);
  //implementar
}
