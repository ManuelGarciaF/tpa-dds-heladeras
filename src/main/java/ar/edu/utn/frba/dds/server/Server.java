package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.server.templates.JavalinHandlebars;
import ar.edu.utn.frba.dds.server.templates.JavalinRenderer;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {
  private static final Logger log = LoggerFactory.getLogger(Server.class);

  public void start() {
    var app = Javalin.create(config -> {
      config.showJavalinBanner = false;
      initializeStaticFiles(config);
      initializeTemplating(config);

      // Logging de requests
      config.requestLogger.http((ctx, ms) -> {
        if (ctx.path().startsWith("/assets")) {
          return;
        }
        log.info("{} {} - {} ms", ctx.method(), ctx.path(), ms);
      });
    });

    // Configurar excepciones
    configurarExcepciones(app);

    // Configurar auth
    app.beforeMatched(Auth::handleAccess);

    // Configurar rutas
    new Router().configure(app);

    app.start(9001);
  }

  private static void configurarExcepciones(Javalin app) {
    app.exception(ValidationException.class, (e, ctx) -> {
      ctx.result("400: Formulario invalido").status(400);
    });
  }

  private static void initializeTemplating(JavalinConfig config) {
    config.fileRenderer(
        new JavalinRenderer().register("hbs", new JavalinHandlebars())
    );
  }

  private static void initializeStaticFiles(JavalinConfig config) {
    config.staticFiles.add(staticFileConfig -> {
      staticFileConfig.hostedPath = "/assets";
      staticFileConfig.directory = "/assets";
    });
  }
}
