package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.model.Colaborador;
import ar.edu.utn.frba.dds.model.repositorios.RepoColaboradores;
import io.javalin.http.Context;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionController {
  private static final Logger log = LoggerFactory.getLogger(SessionController.class);

  public void show(@NotNull Context ctx) {
    ctx.render("login.hbs");
  }

  public void create(@NotNull Context ctx) {
    String username = ctx.formParamAsClass("username", String.class)
        .check(it -> !it.isEmpty(), "Usuario no puede estar vacío")
        .get();
    String password = ctx.formParamAsClass("password", String.class)
        .check(it -> it.length() >= 4, "Contraseña debe tener al menos 4 caracteres")
        .get();

    Colaborador colaborador = RepoColaboradores.getInstance().buscarPorUsuario(username);
    // Si no se encontro el usuario o la contrasenia es incorrecta
    if (colaborador == null || !colaborador.getUsuario().verificarContrasenia(password)) {
      ctx.render("login.hbs", Map.of("errors", List.of("Usuario o contraseña incorrectos")));
      return;
    }

    log.info("Usuario {} logueado", username);
    ctx.sessionAttribute("user", colaborador);
    ctx.redirect("/");
  }

  public void logout(@NotNull Context ctx) {
    ctx.sessionAttribute("user", null);
    ctx.redirect("/");
  }
}
