package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.model.Colaborador;
import io.javalin.http.Context;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColaboradorController {
  private static final Logger log = LoggerFactory.getLogger(ColaboradorController.class);

  public void listaColaboraciones(@NotNull Context ctx) {
    Colaborador colaborador = ctx.sessionAttribute("user");
    assert colaborador != null; // Checkeado por auth
    var model = Map.of(
        "username", colaborador.getUsuario().getNombre()
    );

    ctx.render("colaborar.hbs", model);
  }
}
