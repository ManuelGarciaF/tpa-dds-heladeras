package ar.edu.utn.frba.dds.dominio;

import java.util.ArrayList;
import java.util.List;

public class RepoSolicitudes {
  private final List<SolicitudTarjetaColaborador> pendientes = new ArrayList<>();
  private final List<SolicitudTarjetaColaborador> rechazadas = new ArrayList<>();
  private final List<SolicitudTarjetaColaborador> aceptadas = new ArrayList<>();

  public void agregarSolicitud(SolicitudTarjetaColaborador solicitud) {
    pendientes.add(solicitud);
  }

  public void aceptar(SolicitudTarjetaColaborador solicitud, TarjetaColaborador tarjetaARegistrar) {
    pendientes.remove(solicitud);
    solicitud.asignarTarjeta(tarjetaARegistrar);
    aceptadas.add(solicitud);
  }

  public void rechazar(SolicitudTarjetaColaborador solicitud) {
    pendientes.remove(solicitud);
    rechazadas.add(solicitud);
  }

  public List<SolicitudTarjetaColaborador> getPendientes() {
    return pendientes;
  }

  public List<SolicitudTarjetaColaborador> getRechazadas() {
    return rechazadas;
  }

  public List<SolicitudTarjetaColaborador> getAceptadas() {
    return aceptadas;
  }
}
