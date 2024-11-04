package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.controllers.ColaboradorController;
import ar.edu.utn.frba.dds.controllers.HeladeraController;
import ar.edu.utn.frba.dds.controllers.SessionController;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.Javalin;

public class Router implements WithSimplePersistenceUnit {
  public void configure(Javalin app) {
    var sessionController = new SessionController();
    var colaboradorController = new ColaboradorController();
    var heladeraController = new HeladeraController();

    // Limpiar el entity manager antes de cada request
    app.before(ctx -> entityManager().clear());

    // Inicio
    app.get("/", ctx -> ctx.redirect("/colaboraciones"), Role.COLABORADOR);

    // Sesiones
    app.get("/login", sessionController::show, Role.ANYONE);
    app.post("/login", sessionController::create, Role.ANYONE);
    app.post("/logout", sessionController::logout, Role.COLABORADOR);

    // Colaboraciones
    app.get("/colaboraciones", colaboradorController::formasDeColaborar, Role.COLABORADOR);

    // Ponemos /new a la pagina que contiene el formulario
    app.get("/colaboraciones/hacersecargodeheladera/new",
        colaboradorController::hacerseCargoDeHeladeraForm,
        Role.COLABORADOR);
    app.post("/colaboraciones/hacersecargodeheladera",
        colaboradorController::hacerseCargoDeHeladeraPost,
        Role.COLABORADOR);

    app.get("/colaboraciones/donaciondeviandas/new",
        colaboradorController::donacionDeViandasForm,
        Role.COLABORADOR);
    app.post("/colaboraciones/donaciondeviandas",
        colaboradorController::donacionDeViandasPost,
        Role.COLABORADOR);

    app.get("/colaboraciones/registrarpersona/new",
        colaboradorController::registrarPersonaForm,
        Role.COLABORADOR);
    app.post("/colaboraciones/registrarpersona",
        colaboradorController::registrarPersonaPost,
        Role.COLABORADOR);

    app.get("/colaboraciones/donaciondedinero/new",
        colaboradorController::donacionDeDineroForm,
        Role.COLABORADOR);
    app.post("/colaboraciones/donaciondedinero",
        colaboradorController::donacionDeDineroPost,
        Role.COLABORADOR);

    app.get("/colaboraciones/distribuciondeviandas/new",
        colaboradorController::distribucionDeViandasForm,
        Role.COLABORADOR);
    app.post("/colaboraciones/distribuciondeviandas",
        colaboradorController::distribucionDeViandasPost,
        Role.COLABORADOR);

    // Heladeras
    app.get("/heladeras", heladeraController::show, Role.COLABORADOR);
    app.get("/heladeras/{id}", heladeraController::details, Role.COLABORADOR);
    app.get("/heladeras/{id}/reportarfalla", heladeraController::report, Role.COLABORADOR);


    // Intefaz Admin
    app.get("/admin/login", sessionController::showAdmin, Role.ANYONE);
    app.post("/admin/login", sessionController::createAdmin, Role.ANYONE);
    app.post("/admin/logout", sessionController::logoutAdmin, Role.ADMIN);

    app.get("/admin", ctx -> ctx.redirect("/admin/cargarcsv"), Role.ADMIN);
    app.get("/admin/cargarcsv", ctx -> {}, Role.ADMIN); // TODO
  }
}
