package ar.edu.utn.frba.dds.dominio.tecnicos;

import ar.edu.utn.frba.dds.dominio.Ubicacion;
import ar.edu.utn.frba.dds.dominio.incidentes.Incidente;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepoTecnicos {
  //TODO: graficar DC
  private List<Tecnico> tecnicos;

  public RepoTecnicos() {
    this.tecnicos = new ArrayList<Tecnico>();
  }

  public List<Tecnico> obtenerTecnicos() {
    return tecnicos;
  }

  public void agregarTecnico(Tecnico tecnico) {
    this.tecnicos.add(tecnico);
  }

  //TODO terminar
  public void delegarIncidente(Incidente incidente) {
    tecnicos.forEach(tecnico -> {tecnico.calcularDistancia(incidente.getUbicacionDelIncidente());});
    Collections.sort(tecnicos);
    Collections.reverse(tecnicos);
    Tecnico tecnicoASignar = tecnicos.get(0);
    tecnicoASignar.asignarIncidenteParaResolver(incidente);
  }
}
