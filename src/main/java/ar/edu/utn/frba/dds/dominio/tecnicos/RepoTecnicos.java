package ar.edu.utn.frba.dds.dominio.tecnicos;

import ar.edu.utn.frba.dds.dominio.Heladera;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RepoTecnicos {
  private final List<Tecnico> tecnicos = new ArrayList<>();

  public List<Tecnico> obtenerTecnicos() {
    return tecnicos;
  }

  public void agregarTecnico(Tecnico tecnico) {
    this.tecnicos.add(tecnico);
  }

  public void delegarReparacion(Heladera heladera) {
    var tecnicosPorDistancia = new ArrayList<>(tecnicos);

    // Menos ya que el tecnico mas cercano es el que tiene menor distancia.
    tecnicosPorDistancia.sort(
        Comparator.comparing(tecnico -> -tecnico.distanciaA(heladera.getUbicacion()))
    );
    // Estan ordenados por mayor distancia, el ultimo es el mas cercano.
    var tecnicoMasCercano = tecnicosPorDistancia.get(tecnicosPorDistancia.size() - 1);
    tecnicoMasCercano.asignarHeladera(heladera);
  }
}
