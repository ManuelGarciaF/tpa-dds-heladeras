package ar.edu.utn.frba.dds.dominio.notificacionesHeladera;

import ar.edu.utn.frba.dds.dominio.ColaboradorHumano;
import ar.edu.utn.frba.dds.dominio.Heladera;
import ar.edu.utn.frba.dds.dominio.MapaHeladeras;
import ar.edu.utn.frba.dds.dominio.MotivoDeDistribucion;
import ar.edu.utn.frba.dds.dominio.colaboraciones.DistribucionDeViandas;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SugerenciaTrasladoDeViandas {
  private static final Comparator<Heladera> CRITERIO_COMPARACION = CriteriosComparacionHeladera.masVacia();
  private static final int DISTANCIA_MINIMA_KM = 10;

  private Boolean aceptada = false;
  private final Heladera heladeraRota;
  private final List<Heladera> heladerasSugeridas;
  private final MapaHeladeras mapaHeladeras;

  public SugerenciaTrasladoDeViandas(Heladera heladeraRota, MapaHeladeras mapaHeladeras) {
    this.heladeraRota = heladeraRota;
    this.mapaHeladeras = mapaHeladeras;

    this.heladerasSugeridas = calcularSugerencia();
  }

  private List<Heladera> calcularSugerencia() {
    var candidatas = this.obtenerHeladerasCandidatas();
    candidatas.sort(CRITERIO_COMPARACION);
    return this.obtenerHeladerasNecesarias(candidatas);
  }

  public List<Heladera> obtenerHeladerasCandidatas() {
    return mapaHeladeras.listarHeladeras().stream().filter(heladeraCandidata -> heladeraRota.distanciaA(heladeraCandidata) <= DISTANCIA_MINIMA_KM).toList();
  }

  private List<Heladera> obtenerHeladerasNecesarias(List<Heladera> candidatas) {
    var cantidadRestante = heladeraRota.getCantidadDeViandas();
    var heladerasElegidas = new ArrayList<Heladera>();

    while (cantidadRestante > 0) {
      if (candidatas.isEmpty()) {
        throw new RuntimeException("No hay heladeras suficientes para guardar las viandas");
      }
      var candidata = candidatas.get(0);
      heladerasElegidas.add(candidata);
      candidatas.remove(0);
      cantidadRestante -= candidata.espacioRestante();
    }

    return heladerasElegidas;
  }

  public Heladera getHeladeraRota() {
    return heladeraRota;
  }

  public List<Heladera> getHeladerasSugeridas() {
    return heladerasSugeridas;
  }

  public Boolean fueAceptada() {
    return aceptada;
  }

  public void aceptar(ColaboradorHumano colaboradorHumano) {
    aceptada = true;

    // Agregar una colaboracion por cada traslado
    var cantidadViandas = heladeraRota.getCantidadDeViandas();
    while (cantidadViandas > 0) {
      var heladeraSugerida = heladerasSugeridas.get(0);
      var cantidad = Math.min(cantidadViandas, heladeraSugerida.espacioRestante());

      var distribucion = new DistribucionDeViandas(
        MotivoDeDistribucion.DESPERFECTO_HELADERA,
        LocalDate.now(),
        cantidad,
        heladeraRota,
        heladeraSugerida
      );
      colaboradorHumano.colaborar(distribucion);

      cantidadViandas -= cantidad;
    }
  }
}