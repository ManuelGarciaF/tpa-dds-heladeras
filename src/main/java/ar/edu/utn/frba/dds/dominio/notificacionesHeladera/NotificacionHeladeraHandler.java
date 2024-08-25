package ar.edu.utn.frba.dds.dominio.notificacionesHeladera;
import ar.edu.utn.frba.dds.dominio.Heladera;
import ar.edu.utn.frba.dds.dominio.MapaHeladeras;
import java.util.ArrayList;
import java.util.List;

public class NotificacionHeladeraHandler {
  private final List<SubscriptorCantidadDeViandas> subscriptoresCantidadDeViandas = new ArrayList<>();
  private final List<SubscriptorIncidente> subscriptoresIncidentes = new ArrayList<>();
  private final MapaHeladeras mapaHeladeras;

  public NotificacionHeladeraHandler(MapaHeladeras mapaHeladeras) {
    this.mapaHeladeras = mapaHeladeras;
  }

  public void agregarSubscriptorCantidadDeViandas(SubscriptorCantidadDeViandas subscriptorCantidadDeViandas) {
    subscriptoresCantidadDeViandas.add(subscriptorCantidadDeViandas);
  }

  public void agregarSubscriptorIncidente(SubscriptorIncidente subscriptorIncidente) {
    subscriptoresIncidentes.add(subscriptorIncidente);
  }

  public void notificarCambioCantidadDeViandas(Heladera heladera) {
    subscriptoresCantidadDeViandas.forEach(
        observer -> observer.notificar(heladera)
    );
  }

  public void notificarIncidente(Heladera heladera) {
    // Creamos una sola sugerencia para compartir entre todos los subscriptores
    var sugerenciaTrasladoDeViandas = new SugerenciaTrasladoDeViandas(heladera, mapaHeladeras);
    subscriptoresIncidentes.forEach(
        observer -> observer.notificar(sugerenciaTrasladoDeViandas)
    );
  }

}