package ar.edu.utn.frba.dds.dominio.tecnicos;

import ar.edu.utn.frba.dds.PersistentEntity;
import ar.edu.utn.frba.dds.dominio.Heladera;
import ar.edu.utn.frba.dds.dominio.Ubicacion;
import ar.edu.utn.frba.dds.exceptions.VisitaTecnicoException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
public class Tecnico extends PersistentEntity {
  private String nombre;

  @Embedded
  private Ubicacion ubicacion;

  @ManyToMany
  private final List<Heladera> visitasPendientes = new ArrayList<>();

  @ElementCollection
  private final List<Visita> visitasCompletadas = new ArrayList<>();

  public Tecnico(String nombre, Ubicacion ubicacion) {
    this.nombre = nombre;
    this.ubicacion = ubicacion;
  }

  public Tecnico() {
  }

  public void registrarVisita(Visita visita, Boolean incidenteSolucionado) {
    // Checkear que haya sido asignado a esta heladera
    if (!visitasPendientes.contains(visita.heladeraAsistida())) {
      throw new VisitaTecnicoException("No puede registrar una visita a la cual no fue asignado");
    }

    if (incidenteSolucionado) {
      visita.heladeraAsistida().limpiarIncidentes();
    }

    this.visitasCompletadas.add(visita);
    this.visitasPendientes.remove(visita.heladeraAsistida());
  }

  public Double distanciaA(Ubicacion ubicacion) {
    return this.ubicacion.distanciaA(ubicacion);
  }

  public void asignarHeladera(Heladera heladera) {
    this.visitasPendientes.add(heladera);
  }

  public List<Heladera> getVisitasPendientes() {
    return visitasPendientes;
  }

  public List<Visita> getVisitasCompletadas() {
    return visitasCompletadas;
  }

  public String getNombre() {
    return nombre;
  }
}
