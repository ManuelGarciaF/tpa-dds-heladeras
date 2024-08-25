package ar.edu.utn.frba.dds.dominio.tecnicos;

import ar.edu.utn.frba.dds.dominio.Ubicacion;
import ar.edu.utn.frba.dds.dominio.incidentes.Incidente;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepoTecnicos {
  private final List<Tecnico> tecnicos;

  public RepoTecnicos() {
    this.tecnicos = new ArrayList<Tecnico>();
  }

  public List<Tecnico> obtenerTecnicos() {
    return tecnicos;
  }

  public void agregarTecnico(Tecnico tecnico) {
    this.tecnicos.add(tecnico);
  }

  //Poner como la sugerencia TODO
  public void delegarIncidente(Incidente incidente) {
    //Primero se obtiene todas las distancias de los tecnicos al incidente
    tecnicos.forEach(tecnico -> {tecnico.calcularDistancia(incidente.getUbicacionDelIncidente());});
    //Se ordena de mayor a menor
    Collections.sort(tecnicos);
    //Se invierte el orden para elegir al que esté más cerca
    Collections.reverse(tecnicos);
    //Se obtiene el primero
    Tecnico tecnicoElegido = tecnicos.get(0);
    //Se asigna el incidente
    tecnicoElegido.asignarIncidenteParaResolver(incidente);
  }
}
