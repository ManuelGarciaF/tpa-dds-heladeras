package ar.edu.utn.frba.dds.controllers;

import io.javalin.http.Context;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class SessionController {
  public void show(@NotNull Context ctx) {
    Map<String, ?> model = Map.of();
    ctx.render("login.hbs", model);
  }

  public void create(@NotNull Context ctx) {
  }
}
