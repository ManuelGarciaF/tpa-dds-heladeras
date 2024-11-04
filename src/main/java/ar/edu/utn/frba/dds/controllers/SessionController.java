package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.model.Usuario;
import ar.edu.utn.frba.dds.model.repositorios.RepoUsuarios;
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

    Usuario usuario = RepoUsuarios.getInstance().findByUsername(username);
    // Si no se encontro el usuario o la contrasenia es incorrecta
    if (usuario == null || !usuario.verificarContrasenia(password)) {
      ctx.render("login.hbs", Map.of("errors", List.of("Usuario o contraseña incorrectos")));
      return;
    }

    log.info("Usuario {} logueado", username);
    ctx.sessionAttribute("user_id", usuario.getId());
    ctx.redirect("/");
  }

  public void logout(@NotNull Context ctx) {
    ctx.sessionAttribute("user_id", null);
    ctx.redirect("/");
  }

  public void showAdmin(@NotNull Context ctx) {
    ctx.render("loginadmin.hbs");
  }

  public void createAdmin(@NotNull Context ctx) {
    
  }

}
