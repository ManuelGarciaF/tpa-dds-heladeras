package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.model.Colaborador;
import ar.edu.utn.frba.dds.model.ColaboradorHumano;
import ar.edu.utn.frba.dds.model.Heladera;
import ar.edu.utn.frba.dds.model.incidentes.FallaTecnica;
import ar.edu.utn.frba.dds.model.repositorios.MapaHeladeras;
import ar.edu.utn.frba.dds.model.repositorios.RepoColaboradores;
import ar.edu.utn.frba.dds.model.repositorios.RepoUsuarios;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class HeladeraController implements WithSimplePersistenceUnit {

  public void show(@NotNull Context ctx) {
    String busqueda = ctx.queryParam("q");

    var model = new HashMap<String, Object>();

    Long idUsuario = ctx.sessionAttribute("user_id");
    // TODO cachear esto para no buscarlo en la db cada vez
    String nombre = RepoUsuarios.getInstance().findById(idUsuario).getNombre();
    model.put("username", nombre);

    List<Heladera> heladeras = busqueda != null
        ? MapaHeladeras.getInstance().buscarPorNombre(busqueda)
        : MapaHeladeras.getInstance().listarHeladeras();

    model.put("heladeras", heladeras.stream().map(h -> {
      var ubicacion = h.getUbicacion();

      Integer cantidadDeViandas;
      try { // TODO revisar
        cantidadDeViandas = h.getCantidadDeViandas();
      } catch (NullPointerException ignored) {
        cantidadDeViandas = 0;
      }

      return Map.of(
          "id", h.getId(),
          "nombre", h.getNombre(),
          "viandas", cantidadDeViandas,
          "incidentes", h.getIncidentesActivos().size(),
          "lat", ubicacion.latitud(),
          "lng", ubicacion.longitud()
      );
    }).toList());

    ctx.render("heladeras.hbs", model);
  }

  public void details(@NotNull Context ctx) {
    Long id = ctx.pathParamAsClass("id", Long.class).get();
    var heladera = MapaHeladeras.getInstance().buscarPorId(id);
    if (heladera == null) {
      ctx.status(404);
      return;
    }

    var model = new HashMap<String, Object>();

    Integer cantidadDeViandas;
    try { // TODO revisar
      cantidadDeViandas = heladera.getCantidadDeViandas();
    } catch (NullPointerException ignored) {
      cantidadDeViandas = 0;
    }
    var ubicacion = heladera.getUbicacion();

    model.put("id", heladera.getId());
    model.put("nombre", heladera.getNombre());
    model.put("numeroSerie", heladera.getNumeroDeSerie());
    model.put("viandas", cantidadDeViandas);
    model.put("temperatura", heladera.getUltimaTemperatura());
    model.put("incidentes", heladera.getIncidentesActivos().size());
    model.put("lat", ubicacion.latitud());
    model.put("lng", ubicacion.longitud());

    Long idUsuario = ctx.sessionAttribute("user_id");
    Colaborador colaborador = RepoColaboradores.getInstance().buscarPorIdUsuario(idUsuario);

    // FIXME No se que mejor forma hay de hacer esto, los colaboradores juridicos no
    //  pueden subscribirse a heladeras.
    try {
      var subscriptorCantidadDeViandas = heladera
          .getNotificacionHeladeraHandler()
          .buscarSubscriptorCantidadDeViandas((ColaboradorHumano) colaborador);
      var cantidadAlertaViandas = subscriptorCantidadDeViandas == null
          ? 0
          : subscriptorCantidadDeViandas.getCantidadMinima();
      model.put("cantidadAlertaViandas", cantidadAlertaViandas);
      model.put("puedeSubscribirse", true);
    } catch (ClassCastException e) {
      model.put("puedeSubscribirse", false);
    }

    model.put("incidentes", heladera.getIncidentesActivos().stream().map(i -> {
      var map = new HashMap<String, Object>();
      map.put("titulo", i.getTitulo());
      map.put("descripcion", i.getDescripcionDelError());
      map.put("fecha", i.getFecha().toString());
      try {
        var fallaTecnica = (FallaTecnica) i; // De nuevo, no se como hacer esto mejor.
        map.put("foto", fallaTecnica.getUrlFoto());
        map.put("reportadoPor", fallaTecnica.getUrlFoto());
      } catch (ClassCastException ignored) {
        // No es una falla tecnica
      }

      return map;
    }).toList());

    ctx.render("heladeradetalle.hbs", model);
  }

  public void report(@NotNull Context ctx) {
  }
}
