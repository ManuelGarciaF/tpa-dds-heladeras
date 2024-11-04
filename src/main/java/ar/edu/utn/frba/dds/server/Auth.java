package ar.edu.utn.frba.dds.server;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.RedirectResponse;
import org.jetbrains.annotations.NotNull;

public class Auth {
  public static void handleAccess(@NotNull Context ctx) {
    // Ignorar auth para assets
    if (ctx.path().startsWith("/assets")) {
      return;
    }

    var allowedRoles = ctx.routeRoles();
    if (allowedRoles.contains(Role.ANYONE)) {
      return;
    }
    Long idUsuario = ctx.sessionAttribute("user_id");
    if (allowedRoles.contains(Role.COLABORADOR) && idUsuario != null) {
      return;
    }
    boolean isAdmin = Boolean.TRUE.equals(ctx.sessionAttribute("is_admin"));
    if (allowedRoles.contains(Role.ADMIN) && isAdmin) {
      return;
    }

    // No tiene el rol necesario, redirigir a login.
    ctx.redirect("/login");
    throw new RedirectResponse(HttpStatus.FOUND, "Hay iniciar sesión");
  }
}
