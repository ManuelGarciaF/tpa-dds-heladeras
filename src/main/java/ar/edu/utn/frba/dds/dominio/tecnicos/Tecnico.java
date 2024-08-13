package ar.edu.utn.frba.dds.dominio.tecnicos;

import ar.edu.utn.frba.dds.dominio.Ubicacion;
import ar.edu.utn.frba.dds.dominio.incidentes.Incidente;
import ar.edu.utn.frba.dds.dominio.incidentes.IncidenteHandler;
import java.util.ArrayList;
import java.util.List;

public class Tecnico implements Comparable<Tecnico>{
  private final String nombre;
  private final Ubicacion ubicacion;
  //TODO: mirar comentario de notion
  private List<Incidente> visitasPendientes;
  private final List<Visita> visitas = new ArrayList<>();
  private double distanciaDelTecnicoDeUnaUbicacion;

  public Tecnico(String nombre, Ubicacion ubicacion) {
    this.nombre = nombre;
    this.ubicacion = ubicacion;
  }

  //Acá podriamos instanciar un objeto visita? consultar podemos cargar el incidente?
  public void registrarVisita(Visita visita) {
    this.visitas.add(visita);
  }

  public void asignarIncidenteParaResolver(Incidente incidente) {
    this.visitasPendientes.add(incidente);
  }

  public double getDistanciaDelTecnicoDeUnaUbicacion() {
    return distanciaDelTecnicoDeUnaUbicacion;
  }

  public double calcularDistancia(Ubicacion ubicacion2){

    double diferenciaEntreLatitud = Math.pow((ubicacion2.getLatitud() - ubicacion.getLatitud()),2);
    double diferenciaEntreLongitud = Math.pow((ubicacion2.getLongitud() - ubicacion.getLongitud()),2);
    double distancia = Math.sqrt(diferenciaEntreLongitud + diferenciaEntreLatitud);
    this.distanciaDelTecnicoDeUnaUbicacion = distancia;
    return distancia;
  }

  @Override
  public int compareTo(Tecnico otroTecnico) {
    return Double.compare(otroTecnico.getDistanciaDelTecnicoDeUnaUbicacion(), this.distanciaDelTecnicoDeUnaUbicacion);
  }
}
