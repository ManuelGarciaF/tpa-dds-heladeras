package ar.edu.utn.frba.dds.dominio.notificacionesheladera;

import ar.edu.utn.frba.dds.PersistentEntity;
import ar.edu.utn.frba.dds.dominio.ColaboradorHumano;
import ar.edu.utn.frba.dds.dominio.Heladera;
import ar.edu.utn.frba.dds.dominio.MapaHeladeras;
import ar.edu.utn.frba.dds.dominio.MotivoDeDistribucion;
import ar.edu.utn.frba.dds.dominio.colaboraciones.DistribucionDeViandas;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class SugerenciaTrasladoDeViandas extends PersistentEntity {
  private static final CriterioSeleccionHeladeras CRITERIO_COMPARACION = new HeladerasMasVacias();
  private static final int DISTANCIA_MINIMA_KM = 10;

  private Boolean aceptada = false;

  @ManyToOne
  private Heladera heladeraRota;

  @ManyToMany
  private List<Heladera> heladerasSugeridas;

  public SugerenciaTrasladoDeViandas(Heladera heladeraRota) {
    this.heladeraRota = heladeraRota;

    this.heladerasSugeridas = calcularSugerencia();
  }

  public SugerenciaTrasladoDeViandas() {
  }

  private List<Heladera> calcularSugerencia() {
    // Hay que copiar la lista para hacerla mutable
    var candidatas = new ArrayList<>(this.obtenerHeladerasCandidatas());
    CRITERIO_COMPARACION.sort(heladeraRota, candidatas);
    return this.obtenerHeladerasNecesarias(candidatas);
  }

  private List<Heladera> obtenerHeladerasCandidatas() {
    return MapaHeladeras.getInstance()
        .listarHeladeras()
        .stream()
        .filter(
            heladeraCandidata -> heladeraRota.distanciaA(heladeraCandidata) <= DISTANCIA_MINIMA_KM)
        .toList();
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
    if (aceptada) {
      throw new RuntimeException("La sugerencia ya fue aceptada");
    }
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