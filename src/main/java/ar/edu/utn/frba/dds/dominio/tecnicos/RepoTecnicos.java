package ar.edu.utn.frba.dds.dominio.tecnicos;

import ar.edu.utn.frba.dds.dominio.Heladera;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RepoTecnicos implements WithSimplePersistenceUnit {
  private static final RepoTecnicos instance = new RepoTecnicos();

  public static RepoTecnicos getInstance() {
    return instance;
  }

  public List<Tecnico> obtenerTecnicos() {
    return entityManager().createQuery("from Tecnico", Tecnico.class).getResultList();
  }

  public void agregarTecnico(Tecnico tecnico) {
    persist(tecnico);
  }

  public void delegarReparacion(Heladera heladera) {
    var tecnicosPorDistancia = new ArrayList<>(obtenerTecnicos());

    if (tecnicosPorDistancia.isEmpty()) {
      throw new RuntimeException("No hay tecnicos disponibles");
    }

    // Menos ya que el tecnico mas cercano es el que tiene menor distancia.
    tecnicosPorDistancia.sort(
        Comparator.comparing(tecnico -> -tecnico.distanciaA(heladera.getUbicacion()))
    );
    // Estan ordenados por mayor distancia, el ultimo es el mas cercano.
    var tecnicoMasCercano = tecnicosPorDistancia.get(tecnicosPorDistancia.size() - 1);
    tecnicoMasCercano.asignarHeladera(heladera);
  }
}
