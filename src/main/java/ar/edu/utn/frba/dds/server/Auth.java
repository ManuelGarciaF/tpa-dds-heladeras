package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.model.Usuario;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.RedirectResponse;
import org.jetbrains.annotations.NotNull;

public class Auth {
  public static void handleAccess(@NotNull Context ctx) {
    // If the route is in assets, skip auth
    if (ctx.path().startsWith("/assets")) {
      return;
    }

    var allowedRoles = ctx.routeRoles();
    if (allowedRoles.contains(Role.ANYONE)) {
      return;
    }
    Usuario user = ctx.sessionAttribute("user");
    if (allowedRoles.contains(Role.COLABORADOR) && user != null) {
      return;
    }
    boolean isAdmin = Boolean.TRUE.equals(ctx.sessionAttribute("admin"));
    if (allowedRoles.contains(Role.ADMIN) && isAdmin) {
      return;
    }
    ctx.redirect("/login");
    throw new RedirectResponse(HttpStatus.FOUND, "You must log in");
  }
}
