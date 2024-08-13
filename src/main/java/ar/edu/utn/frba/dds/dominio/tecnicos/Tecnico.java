package ar.edu.utn.frba.dds.dominio.tecnicos;

import ar.edu.utn.frba.dds.dominio.Ubicacion;
import ar.edu.utn.frba.dds.dominio.incidentes.Incidente;
import ar.edu.utn.frba.dds.dominio.incidentes.IncidenteHandler;
import ar.edu.utn.frba.dds.exceptions.VisitaTecnicoException;
import java.util.ArrayList;
import java.util.List;

public class Tecnico implements Comparable<Tecnico>{
  private final String nombre;
  private final Ubicacion ubicacion;
  //TODO: mirar comentario de notion
  private List<Incidente> visitasPendientes = new ArrayList<Incidente>();
  private final List<Visita> visitas = new ArrayList<Visita>();
  private double distanciaDelTecnicoDeUnaUbicacion;

  public Tecnico(String nombre, Ubicacion ubicacion) {
    this.nombre = nombre;
    this.ubicacion = ubicacion;
  }

  //Acá podriamos instanciar un objeto visita? consultar podemos cargar el incidente?
  public void registrarVisita(Visita visita) {
    // me aseguro de que si la visita fue asignada, este no pueda efectuarla, esto me asegura de que primero se asigne y despues se haga la visita
    if(!visitasPendientes.contains(visita.getIncidenteProblematico())) {
      throw new VisitaTecnicoException("No puede registrar una visita a la cual no fue asignado");
    }
    this.visitas.add(visita);
    this.visitasPendientes.remove(visita.getIncidenteProblematico());
  }

  public void asignarIncidenteParaResolver(Incidente incidente) {
    this.visitasPendientes.add(incidente);
  }

  public double getDistanciaDelTecnicoDeUnaUbicacion() {
    return distanciaDelTecnicoDeUnaUbicacion;
  }

  public List<Incidente> getVisitasPendientes() {
    return visitasPendientes;
  }

  public double calcularDistancia(Ubicacion ubicacion2){

    double diferenciaEntreLatitud = Math.pow((ubicacion2.getLatitud() - ubicacion.getLatitud()),2);
    double diferenciaEntreLongitud = Math.pow((ubicacion2.getLongitud() - ubicacion.getLongitud()),2);
    double distancia = Math.sqrt(diferenciaEntreLongitud + diferenciaEntreLatitud);
    this.distanciaDelTecnicoDeUnaUbicacion = distancia;
    return distancia;
  }

  public List<Visita> getVisitas() {
    return visitas;
  }

  @Override
  public int compareTo(Tecnico otroTecnico) {
    return Double.compare(otroTecnico.getDistanciaDelTecnicoDeUnaUbicacion(), this.distanciaDelTecnicoDeUnaUbicacion);
  }
}
