package ar.edu.utn.frba.dds.dominio.tecnicos;

import ar.edu.utn.frba.dds.dominio.Heladera;
import ar.edu.utn.frba.dds.dominio.Ubicacion;
import ar.edu.utn.frba.dds.exceptions.VisitaTecnicoException;
import java.util.ArrayList;
import java.util.List;

public class Tecnico {
  private final String nombre;
  private final Ubicacion ubicacion;
  private final List<Heladera> visitasPendientes = new ArrayList<>();

  private final List<Visita> visitasCompletadas = new ArrayList<>();

  public Tecnico(String nombre, Ubicacion ubicacion) {
    this.nombre = nombre;
    this.ubicacion = ubicacion;
  }

  public void registrarVisita(Visita visita, Boolean incidenteSolucionado) {
    // Checkear que haya sido asignado a esta heladera
    if (!visitasPendientes.contains(visita.getHeladeraAsistida())) {
      throw new VisitaTecnicoException("No puede registrar una visita a la cual no fue asignado");
    }

    if (incidenteSolucionado) {
      visita.getHeladeraAsistida().limpiarIncidentes();
    }

    this.visitasCompletadas.add(visita);
    this.visitasPendientes.remove(visita.getHeladeraAsistida());
  }

  public Double distanciaA(Ubicacion ubicacion) {
    return this.ubicacion.distanciaA(ubicacion);
  }

  public void asignarHeladera(Heladera heladera) {
    this.visitasPendientes.add(heladera);
  }

  public List<Visita> getVisitasCompletadas() {
    return visitasCompletadas;
  }

  public String getNombre() {
    return nombre;
  }
}
