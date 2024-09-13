package ar.edu.utn.frba.dds.dominio.notificacionesheladera;

import ar.edu.utn.frba.dds.PersistentEntity;
import ar.edu.utn.frba.dds.dominio.Heladera;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Embeddable
public class NotificacionHeladeraHandler {

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "heladera_id")
  private final List<SubscriptorCantidadDeViandas> subscriptoresCantidadDeViandas =
      new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "heladera_id")
  private final List<SubscriptorIncidente> subscriptoresIncidentes = new ArrayList<>();

  public NotificacionHeladeraHandler() {
  }

  public void agregarSubscriptorCantidadDeViandas(
      SubscriptorCantidadDeViandas subscriptorCantidadDeViandas
  ) {
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
    var sugerenciaTrasladoDeViandas = new SugerenciaTrasladoDeViandas(heladera);
    subscriptoresIncidentes.forEach(
        observer -> observer.notificar(sugerenciaTrasladoDeViandas)
    );
  }

}