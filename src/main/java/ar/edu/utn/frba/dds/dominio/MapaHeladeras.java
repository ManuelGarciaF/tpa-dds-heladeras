package ar.edu.utn.frba.dds.dominio;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;


public class MapaHeladeras implements WithSimplePersistenceUnit {
  private static final MapaHeladeras instance = new MapaHeladeras();

  public static MapaHeladeras getInstance() {
    return instance;
  }

  public List<Heladera> listarHeladeras() {
    return entityManager().createQuery("from Heladera", Heladera.class).getResultList();
  }

  public void agregarHeladera(Heladera heladera) {
    entityManager().persist(heladera);
  }

  public Heladera buscarHeladera(String nombreHeladera) {
    return entityManager().createQuery(
            "from Heladera where nombre = :nombreHeladera",
            Heladera.class)
        .setParameter("nombreHeladera", nombreHeladera)
        .getSingleResult();
  }

  // TODO hacer esto con una query
  public List<UsoTarjetaPersonaVulnerable> encontrarUsosDeTarjeta(String codigotarjeta) {
    return this.listarHeladeras().stream()
        .flatMap(heladera -> heladera.usosDeTarjetaPersonaVulnerable(codigotarjeta).stream())
        .toList();
  }

  public void revisarSensoresDeTemperatura() {
    this.listarHeladeras().forEach(Heladera::checkearDesconexionSensorTemperatura);
  }
}
