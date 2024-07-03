package ar.edu.utn.frba.dds.dominio.incidentes;

import java.util.ArrayList;
import java.util.List;

public class IncidenteHandler {
  private final List<IncidenteObserver> incidentesObservers = new ArrayList<IncidenteObserver>();

  public void agregarObserver(IncidenteObserver observer) {
    incidentesObservers.add(observer);
  }

  public void quitarObserver(IncidenteObserver observer) {
    incidentesObservers.remove(observer);
  }

  public void notificar(Incidente incidente) {
    incidentesObservers.forEach(
        observer -> observer.avisar(incidente)
    );
  }

}
