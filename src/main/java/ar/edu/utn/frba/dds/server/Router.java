package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.controllers.SessionController;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import io.javalin.Javalin;

public class Router implements SimplePersistenceTest {
  public void configure(Javalin app) {
    SessionController sessionController = new SessionController();

    app.before(ctx -> entityManager().clear());

    app.get("/", ctx -> ctx.redirect("/colaborar"), Role.COLABORADOR);

    app.get("/login", sessionController::show, Role.ANYONE);
    app.post("/login", sessionController::create, Role.ANYONE);

    app.get("/colaborar", ctx -> {}, Role.COLABORADOR);
//    app.get("/", ctx -> {}, Role.COLABORADOR);
  }
}
