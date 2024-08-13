package ar.edu.utn.frba.dds.dominio.tecnicos;

import ar.edu.utn.frba.dds.dominio.Ubicacion;
import ar.edu.utn.frba.dds.dominio.incidentes.Incidente;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepoTecnicos {
  //TODO: graficar DC
  private List<Tecnico> tecnicos;

  public RepoTecnicos(List<Tecnico> tecnicos) {
    this.tecnicos = new ArrayList<>();
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
  //hay que filtrar ubicaciones, y luego darsela al que este mas proximo
  //Por el momento se me ocurrió usar un sistema de coordenadas plano (luego veré si uso una api de google maps o una formula mas avanzada para usar coordenadas reales)
  /*public double calcularDistanciaEntreUbicaciones(Ubicacion ubicacion1, Ubicacion ubicacion2){

    double diferenciaEntreLatitud = Math.pow((ubicacion2.getLatitud() - ubicacion1.getLatitud()),2);
    double diferenciaEntreLongitud = Math.pow((ubicacion2.getLongitud() - ubicacion1.getLongitud()),2);
    return Math.sqrt(diferenciaEntreLongitud + diferenciaEntreLatitud);
  }*/

  //para comparar, voy a agarrar el repo, hacer un foreach con la distancia y el que tenga menor distancia va a ser notificado
}
