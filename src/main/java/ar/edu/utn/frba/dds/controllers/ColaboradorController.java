package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.model.Colaborador;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColaboradorController {
  private static final Logger log = LoggerFactory.getLogger(ColaboradorController.class);

  public void listaColaboraciones(@NotNull Context ctx) {
    var model = getModel(ctx);
    ctx.render("colaborar.hbs", model);
  }

  public void nuevaHeladeraGet(@NotNull Context ctx) {
    var model = getModel(ctx);
    ctx.render("nuevaheladera.hbs", model);
  }

  public void nuevaHeladeraPost(@NotNull Context ctx) {
    double lat = ctx.formParamAsClass("latitud", Double.class).get();
    double lng = ctx.formParamAsClass("longitud", Double.class).get();
    String nombre = ctx.formParamAsClass("nombre", String.class)
        .check(it -> !it.isEmpty(), "Nombre no puede estar vacío")
        .get();
    String numeroserie = ctx.formParamAsClass("numeroserie", String.class)
        .check(it -> !it.isEmpty(), "Número de serie no puede estar vacío")
        .get();
    int capacidadviandas = ctx.formParamAsClass("capacidadviandas", Integer.class).get();

    log.info("Se recibio: {}-{}, nombre: {}, numeroserie: {}, capacidad: {}", lat, lng, nombre, numeroserie, capacidadviandas);
    // TODO
  }

  public void donarViandasGet(@NotNull Context ctx) {
  }

  public void donarViandasPost(@NotNull Context ctx) {
  }

  public void registrarPersonaGet(@NotNull Context ctx) {
  }

  public void registrarPersonaPost(@NotNull Context ctx) {
  }

  public void donarDineroGet(@NotNull Context ctx) {
  }

  public void donarDineroPost(@NotNull Context ctx) {
  }

  public void distribuirViandasGet(@NotNull Context ctx) {
  }

  public void distribuirViandasPost(@NotNull Context ctx) {
  }

  private @NotNull Map<String, Object> getModel(@NotNull Context ctx) {
    Colaborador colaborador = ctx.sessionAttribute("user");
    assert colaborador != null; // Checkeado por auth
    var model = new HashMap<String, Object>();
    model.put("username", colaborador.getUsuario().getNombre());

    return model;
  }
}
