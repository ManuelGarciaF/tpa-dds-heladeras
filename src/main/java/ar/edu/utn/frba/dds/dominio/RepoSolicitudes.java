package ar.edu.utn.frba.dds.dominio;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;

public class RepoSolicitudes implements WithSimplePersistenceUnit {

  private static final RepoSolicitudes instance = new RepoSolicitudes();

  public static RepoSolicitudes getInstance() {
    return instance;
  }

  public void agregarSolicitud(SolicitudTarjetaColaborador solicitud) {
    entityManager().persist(solicitud);
  }

  public void aceptar(SolicitudTarjetaColaborador solicitud, TarjetaColaborador tarjetaARegistrar) {
    solicitud.aceptar(tarjetaARegistrar);
    entityManager().merge(solicitud);
  }

  public void rechazar(SolicitudTarjetaColaborador solicitud) {
    solicitud.rechazar();
    entityManager().merge(solicitud);
  }

  public List<SolicitudTarjetaColaborador> getPendientes() {
    return entityManager().createQuery(
            "from SolicitudTarjetaColaborador where estado = 'PENDIENTE'",
            SolicitudTarjetaColaborador.class)
        .getResultList();
  }

  public List<SolicitudTarjetaColaborador> getRechazadas() {
    return entityManager().createQuery(
            "from SolicitudTarjetaColaborador where estado = 'RECHAZADA'",
            SolicitudTarjetaColaborador.class)
        .getResultList();
  }

  public List<SolicitudTarjetaColaborador> getAceptadas() {
    return entityManager().createQuery(
            "from SolicitudTarjetaColaborador where estado = 'ACEPTADA'",
            SolicitudTarjetaColaborador.class)
        .getResultList();
  }
}
