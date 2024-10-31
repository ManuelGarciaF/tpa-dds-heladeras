package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.controllers.ColaboradorController;
import ar.edu.utn.frba.dds.controllers.SessionController;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.Javalin;

public class Router implements WithSimplePersistenceUnit {
  public void configure(Javalin app) {
    var sessionController = new SessionController();
    var colaboradorController = new ColaboradorController();

    // Limpiar el entity manager antes de cada request
    app.before(ctx -> entityManager().clear());

    // Inicio
    app.get("/", ctx -> ctx.redirect("/colaborar"), Role.COLABORADOR);

    // Sesiones
    app.get("/login", sessionController::show, Role.ANYONE);
    app.post("/login", sessionController::create, Role.ANYONE);
    app.post("/logout", sessionController::logout, Role.COLABORADOR);

    // Colaboraciones
    app.get("/colaborar", colaboradorController::listaColaboraciones, Role.COLABORADOR);

    app.get("/colaborar/nuevaheladera",
        colaboradorController::nuevaHeladeraGet,
        Role.COLABORADOR);
    app.post("/colaborar/nuevaheladera",
        colaboradorController::nuevaHeladeraPost,
        Role.COLABORADOR);

    // TODO cambiar rol
    app.get("/colaborar/donarviandas",
        colaboradorController::donarViandasGet,
        Role.ANYONE);
    app.post("/colaborar/donarviandas",
        colaboradorController::donarViandasPost,
        Role.COLABORADOR);

    app.get("/colaborar/registrarpersona",
        colaboradorController::registrarPersonaGet,
        Role.COLABORADOR);
    app.post("/colaborar/registrarpersona",
        colaboradorController::registrarPersonaPost,
        Role.COLABORADOR);

    app.get("/colaborar/donardinero",
        colaboradorController::donarDineroGet,
        Role.COLABORADOR);
    app.post("/colaborar/donardinero",
        colaboradorController::donarDineroPost,
        Role.COLABORADOR);

    app.get("/colaborar/distribuirviandas",
        colaboradorController::distribuirViandasGet,
        Role.COLABORADOR);
    app.post("/colaborar/distribuirviandas",
        colaboradorController::distribuirViandasPost,
        Role.COLABORADOR);
  }
}
