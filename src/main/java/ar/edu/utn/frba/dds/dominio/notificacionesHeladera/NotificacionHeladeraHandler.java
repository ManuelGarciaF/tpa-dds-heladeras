package ar.edu.utn.frba.dds.dominio.notificacionesHeladera;
import ar.edu.utn.frba.dds.dominio.*;
import java.util.ArrayList;
import java.util.List;

public class NotificacionHeladeraHandler {
  private static List<NotificacionHeladeraObserver> heladerasObservers = new ArrayList<NotificacionHeladeraObserver>();


  public void agregarObserver(NotificacionHeladeraObserver observer) {
    heladerasObservers.add(observer);
  }

  public void quitarObserver(NotificacionHeladeraObserver observer) {
    heladerasObservers.remove(observer);
  }

  public void notificar(Heladera heladera) {
    heladerasObservers.forEach(
        observer -> observer.notificar(heladera)
    );
  }

  public List<NotificacionHeladeraObserver> getHeladerasObservers() {
    return heladerasObservers;
  }
}