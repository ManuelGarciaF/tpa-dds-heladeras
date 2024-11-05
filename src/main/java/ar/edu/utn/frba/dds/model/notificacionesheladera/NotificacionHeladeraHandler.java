package ar.edu.utn.frba.dds.model.notificacionesheladera;

import ar.edu.utn.frba.dds.model.ColaboradorHumano;
import ar.edu.utn.frba.dds.model.Heladera;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Embeddable
public class NotificacionHeladeraHandler {

  @OneToMany(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "heladeraId")
  private final List<SubscriptorCantidadDeViandas> subscriptoresCantidadDeViandas =
      new ArrayList<>();

  @OneToMany(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "heladeraId")
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

  public SubscriptorCantidadDeViandas buscarSubscriptorCantidadDeViandas(
      ColaboradorHumano colaboradorHumano) {
    return subscriptoresCantidadDeViandas.stream()
        .filter(subscriptor -> subscriptor.getColaboradorInteresado().equals(colaboradorHumano))
        .findFirst()
        .orElse(null);
  }

  public boolean estaSubscriptoCantidadDeViandas(ColaboradorHumano colaboradorHumano) {
    return subscriptoresCantidadDeViandas.stream()
        .anyMatch(subscriptor -> subscriptor.getColaboradorInteresado().equals(colaboradorHumano));
  }

  public void removerSubscriptorCantidadDeViandas(ColaboradorHumano colaboradorHumano) {
    subscriptoresCantidadDeViandas.removeIf(
        subscriptor -> subscriptor.getColaboradorInteresado().equals(colaboradorHumano)
    );
  }

  public boolean estaSubscriptoIncidentes(ColaboradorHumano colaboradorHumano) {
    return subscriptoresIncidentes.stream()
        .anyMatch(subscriptor -> subscriptor.getColaboradorInteresado().equals(colaboradorHumano));
  }

  public void removerSubscriptorIncidente(ColaboradorHumano colaboradorHumano) {
    subscriptoresIncidentes.removeIf(
        subscriptor -> subscriptor.getColaboradorInteresado().equals(colaboradorHumano)
    );
  }

}