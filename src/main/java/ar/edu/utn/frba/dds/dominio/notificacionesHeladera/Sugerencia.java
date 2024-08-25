package ar.edu.utn.frba.dds.dominio.notificacionesHeladera;

import ar.edu.utn.frba.dds.dominio.Heladera;
import ar.edu.utn.frba.dds.dominio.MapaHeladeras;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//podriamos hacer un abstract o una interface para los distintos tipos de sugerencias
public class Sugerencia {

  //TODO
  private static final Integer criterio = 0;
  private List<Heladera> heladerasCandidatas = new ArrayList<Heladera>();
  private Boolean fueAceptadaLaSugerencia;
  private Heladera heladeraRota;
  private List<Heladera> heladerasFinales;
  private MapaHeladeras mapaHeladeras;

  public Sugerencia(Heladera heladeraRota, MapaHeladeras mapaHeladeras) {
    this.heladeraRota = heladeraRota;
    this.mapaHeladeras = mapaHeladeras;
    heladerasCandidatas = this.obtenerHeladerasCandidatas();
    this.ordenarPorMasVacia();
    this.heladerasFinales = this.obtenerHeladerasNecesarias();
  }

  public List<Heladera> obtenerHeladerasNecesarias(){
    var cantidadRestante = heladeraRota.getCantidadDeViandas();
    var heladerasElegidas = new ArrayList<Heladera>();

    while(cantidadRestante > 0){
      if(heladerasCandidatas.isEmpty()){
        throw new RuntimeException("No hay heladeras suficientes para guardar las viandas");
      }
      var candidata = heladerasCandidatas.getFirst();
      heladerasElegidas.add(candidata);
      heladerasCandidatas.removeFirst();
      cantidadRestante -= candidata.espacioRestante();
    }

    return heladerasElegidas;
  }

  //Tendra un sort esto?
   public List<Heladera> obtenerHeladerasCandidatas() {
      return mapaHeladeras.listarHeladeras().stream().filter(heladeraCandidata -> heladeraCandidata.calcularDistancia(heladeraRota.getUbicacion()) <= 10).toList();
   }

  public void ordenarPorMenorDistancia(){
    heladerasCandidatas.sort((o1, o2) -> Double.compare(o2.calcularDistancia(heladeraRota.getUbicacion()),
        o1.calcularDistancia(heladeraRota.getUbicacion())));
  }

  public void ordenarPorMasVacia(){
    heladerasCandidatas.sort((o1, o2) -> Double.compare(o2.getCantidadDeViandas(),o1.getCantidadDeViandas()));
  }


  public List<Heladera> getHeladerasCandidatas() {
    return heladerasCandidatas;
  }

  public Integer getCriterio() {
    return criterio;
  }

  public Boolean getFueAceptadaLaSugerencia() {
    return fueAceptadaLaSugerencia;
  }

  public Heladera getHeladeraRota() {
    return heladeraRota;
  }

}




