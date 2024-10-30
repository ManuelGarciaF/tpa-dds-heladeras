package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.controllers.ColaboradorController;
import ar.edu.utn.frba.dds.controllers.SessionController;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import io.javalin.Javalin;

public class Router implements SimplePersistenceTest {
  public void configure(Javalin app) {
    var sessionController = new SessionController();
    var colaboradorController = new ColaboradorController();

    app.before(ctx -> entityManager().clear());

    app.get("/", ctx -> ctx.redirect("/colaborar"), Role.COLABORADOR);

    app.get("/login", sessionController::show, Role.ANYONE);
    app.post("/login", sessionController::create, Role.ANYONE);

    app.get("/colaborar", colaboradorController::listaColaboraciones, Role.COLABORADOR);
  }
}
