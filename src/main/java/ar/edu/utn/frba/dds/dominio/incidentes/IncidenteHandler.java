package ar.edu.utn.frba.dds.dominio.incidentes;

import java.util.ArrayList;
import java.util.List;

public class IncidenteHandler {
  private List<IncidenteObserver> incidentesObservers;



  public void agregarObserver(IncidenteObserver observer) {
    incidentesObservers.add(observer);
  }

  public void quitarObserver(IncidenteObserver observer) {
    incidentesObservers.remove(observer);
  }

  public void notificar() {
    incidentesObservers.forEach(
        observer -> observer.avisar()
    );
  }


  //CONSTRUCTOR
  public IncidenteHandler(){
    incidentesObservers = new ArrayList<IncidenteObserver>();
  }
}
