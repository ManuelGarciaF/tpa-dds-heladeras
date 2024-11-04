package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.model.Heladera;
import ar.edu.utn.frba.dds.model.repositorios.MapaHeladeras;
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

    Long user_id = ctx.sessionAttribute("user_id");
    // TODO cachear esto para no buscarlo en la db cada vez
    String nombre = RepoUsuarios.getInstance().findById(user_id).getNombre();
    model.put("username", nombre);

    List<Heladera> heladeras = busqueda != null ?
        MapaHeladeras.getInstance().buscarPorNombre(busqueda) :
        MapaHeladeras.getInstance().listarHeladeras();

    model.put("heladeras", heladeras.stream().map(h -> {
      var ubicacion = h.getUbicacion();

      var cantidadDeViandas = 0;
      try { // TODO revisar
        cantidadDeViandas = h.getCantidadDeViandas();
      } catch (NullPointerException ignored) {}

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
  }

  public void report(@NotNull Context ctx) {
  }
}
