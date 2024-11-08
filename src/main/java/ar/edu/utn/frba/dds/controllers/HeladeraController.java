package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.model.Colaborador;
import ar.edu.utn.frba.dds.model.ColaboradorHumano;
import ar.edu.utn.frba.dds.model.Heladera;
import ar.edu.utn.frba.dds.model.incidentes.FallaTecnica;
import ar.edu.utn.frba.dds.model.notificacionesheladera.SubscriptorCantidadDeViandas;
import ar.edu.utn.frba.dds.model.notificacionesheladera.SubscriptorIncidente;
import ar.edu.utn.frba.dds.model.repositorios.MapaHeladeras;
import ar.edu.utn.frba.dds.model.repositorios.RepoColaboradores;
import ar.edu.utn.frba.dds.model.repositorios.RepoUsuarios;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.UploadedFile;
import io.javalin.util.FileUtil;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class HeladeraController implements WithSimplePersistenceUnit {

  public void showAll(@NotNull Context ctx) {
    String busqueda = ctx.queryParam("q");

    var model = new HashMap<String, Object>();

    Long idUsuario = ctx.sessionAttribute("user_id");
    String nombre = ctx.cachedSessionAttributeOrCompute("username", (c) ->
        RepoUsuarios.getInstance().findById(idUsuario).getNombre());
    model.put("username", nombre);

    List<Heladera> heladeras = busqueda != null
        ? MapaHeladeras.getInstance().buscarPorNombre(busqueda)
        : MapaHeladeras.getInstance().listarHeladeras();

    model.put("heladeras", heladeras.stream().map(h -> {
      var ubicacion = h.getUbicacion();

      Integer cantidadDeViandas = h.getCantidadDeViandas();

      var m = new HashMap<String, Object>();
      m.put("id", h.getId());
      m.put("nombre", h.getNombre());
      m.put("viandas", cantidadDeViandas);
      m.put("incidentes", h.getIncidentesActivos().size());
      m.put("lat", ubicacion.latitud());
      m.put("lng", ubicacion.longitud());

      return m;
    }).toList());

    ctx.render("heladeras.hbs", model);
  }

  public void details(@NotNull Context ctx) {
    var heladera = obtenerHeladeraDePathParam(ctx);

    var model = new HashMap<String, Object>();

    var ubicacion = heladera.getUbicacion();

    model.put("id", heladera.getId());
    model.put("nombre", heladera.getNombre());
    model.put("numeroSerie", heladera.getNumeroDeSerie());
    model.put("viandas", heladera.getCantidadDeViandas());
    model.put("temperatura", heladera.getUltimaTemperatura());
    model.put("incidentes", heladera.getIncidentesActivos().size());
    model.put("lat", ubicacion.latitud());
    model.put("lng", ubicacion.longitud());

    Long idUsuario = ctx.sessionAttribute("user_id");
    Colaborador colaborador = RepoColaboradores.getInstance().buscarPorIdUsuario(idUsuario);

    // FIXME No se que mejor forma hay de hacer esto, los colaboradores juridicos no
    //  pueden subscribirse a heladeras.
    try {
      var colaboradorHumano = (ColaboradorHumano) colaborador;
      var notifHandler = heladera
          .getNotificacionHeladeraHandler();
      model.put("subscriptoIncidentes", notifHandler.estaSubscriptoIncidentes(colaboradorHumano));

      var subscriptoCantidadDeViandas = notifHandler
          .estaSubscriptoCantidadDeViandas(colaboradorHumano);
      model.put("subscriptoCantidadDeViandas", subscriptoCantidadDeViandas);

      if (subscriptoCantidadDeViandas) {
        model.put("cantidadAlertaViandas",
            notifHandler.buscarSubscriptorCantidadDeViandas(colaboradorHumano).getCantidadMinima()
        );
      }


      model.put("puedeSubscribirse", true);
    } catch (ClassCastException e) {
      model.put("puedeSubscribirse", false);
    }

    model.put("incidentes", heladera.getIncidentesActivos().stream().map(i -> {
      var map = new HashMap<String, Object>();
      map.put("titulo", i.getTitulo());
      map.put("descripcion", i.getDescripcionDelError());
      map.put("fecha", i.getFecha());
      try {
        var fallaTecnica = (FallaTecnica) i; // FIXME De nuevo, no se como hacer esto mejor.
        map.put("foto", fallaTecnica.getUrlFoto());
        map.put("reportadoPor", fallaTecnica.getColaborador().getUsuario().getNombre());
      } catch (ClassCastException ignored) {
        // No es una falla tecnica
      }

      return map;
    }).toList());

    ctx.render("heladeradetalle.hbs", model);
  }

  public void subscripcionesPost(@NotNull Context ctx) {
    Long idUsuario = ctx.sessionAttribute("user_id");
    ColaboradorHumano colaborador;
    try {
      colaborador = (ColaboradorHumano) RepoColaboradores.getInstance()
          .buscarPorIdUsuario(idUsuario);
    } catch (ClassCastException e) {
      ctx.status(403);
      return;
    }

    var notifHandler = obtenerHeladeraDePathParam(ctx).getNotificacionHeladeraHandler();

    boolean subscribirseIncidentes = ctx.formParamAsClass("incidente", Boolean.class)
        .getOrDefault(false);
    boolean subscribirseViandas = ctx.formParamAsClass("pocasviandas", Boolean.class)
        .getOrDefault(false);

    var cantidadMinimaViandas = ctx.formParamAsClass("cantidadAlertaViandas", Integer.class)
        .check(v -> !(subscribirseViandas && v == null), "Cantidad de viandas requerida")
        .check(v -> v > 0, "Cantidad de viandas debe ser mayor a 0")
        .getOrDefault(null);


    // Cambiar las subscripciones del colaborador
    withTransaction(() -> {
      if (notifHandler.estaSubscriptoIncidentes(colaborador) != subscribirseIncidentes) {
        if (subscribirseIncidentes) {
          notifHandler.agregarSubscriptorIncidente(new SubscriptorIncidente(colaborador));
        } else {
          notifHandler.removerSubscriptorIncidente(colaborador);
        }
      }

      if (notifHandler.estaSubscriptoCantidadDeViandas(colaborador) && !subscribirseViandas) {
        notifHandler.removerSubscriptorCantidadDeViandas(colaborador);
      } else if (!notifHandler.estaSubscriptoCantidadDeViandas(colaborador)
          && subscribirseViandas) {
        notifHandler.agregarSubscriptorCantidadDeViandas(
            new SubscriptorCantidadDeViandas(colaborador, cantidadMinimaViandas)
        );
      } else if (subscribirseViandas) {
        notifHandler.buscarSubscriptorCantidadDeViandas(colaborador)
            .setCantidadMinima(cantidadMinimaViandas);
      }
    });

    ctx.render("fragments/msgexito.hbs", Map.of("message", "Subscripciones actualizadas"));
  }

  public void reportForm(@NotNull Context ctx) {
    var model = new HashMap<String, Object>();
    model.put("id", ctx.pathParam("id"));
    ctx.render("nuevoincidente.hbs", model);
  }

  public void reportPost(@NotNull Context ctx) {
    String descripcion = ctx.formParamAsClass("descripcion", String.class)
        .check(d -> d.length() >= 10, "Descripcion requerida")
        .get();

    UploadedFile img = ctx.uploadedFile("img");
    String nombreImg;
    if (img != null) {
      // Guardar archivo
      nombreImg = img.filename(); // TODO quizas habria que generar un UUID para evitar conflictos
      FileUtil.streamToFile(img.content(), "uploaded/" + img.filename());
    } else {
      nombreImg = null;
    }

    var heladera = obtenerHeladeraDePathParam(ctx);

    var colaborador = RepoColaboradores.getInstance()
        .buscarPorIdUsuario(ctx.sessionAttribute("user_id"));

    withTransaction(() -> heladera.nuevoIncidente(new FallaTecnica(
        colaborador,
        LocalDateTime.now(),
        descripcion,
        nombreImg
    )));

    ctx.redirect("/heladeras/" + heladera.getId());
  }

  private static @NotNull Heladera obtenerHeladeraDePathParam(@NotNull Context ctx) {
    Long id = ctx.pathParamAsClass("id", Long.class).get();
    var heladera = MapaHeladeras.getInstance().buscarPorId(id);
    if (heladera == null) {
      throw new ForbiddenResponse();
    }
    return heladera;
  }

}
