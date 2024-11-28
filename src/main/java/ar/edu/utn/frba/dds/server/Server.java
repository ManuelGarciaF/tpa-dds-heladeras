package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.model.MotivoDeDistribucion;
import ar.edu.utn.frba.dds.server.templates.JavalinHandlebars;
import ar.edu.utn.frba.dds.server.templates.JavalinRenderer;
import com.github.jknack.handlebars.helper.StringHelpers;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.staticfiles.Location;
import io.javalin.validation.ValidationException;
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server implements WithSimplePersistenceUnit {
  private static final Logger log = LoggerFactory.getLogger(Server.class);

  public void start() {
    var app = Javalin.create(config -> {
      config.showJavalinBanner = false;
      initializeStaticFiles(config);
      initializeTemplating(config);
      initializeValidation(config);

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

    app.start(getHerokuAssignedPort());

    // Hacer que hybernate se inicialize ahora para no esperar 10s en la primera request.
    withTransaction(() -> {});
  }

  private static void configurarExcepciones(Javalin app) {
    app.exception(ValidationException.class, (e, ctx) -> {
      log.error("Error de validacion {}", e.getErrors());
      ctx.result("400: Formulario invalido").status(400);
    });
  }

  private static void initializeTemplating(JavalinConfig config) {
    // Registrar helpers utiles
    var handlebars = new JavalinHandlebars();
    handlebars.registerHelper("dateFormat", StringHelpers.dateFormat);

    config.fileRenderer(
        new JavalinRenderer().register("hbs", handlebars)
    );
  }

  private static void initializeStaticFiles(JavalinConfig config) {
    // Crear las carpetas vacias
    var uploaded = new File("uploaded/").mkdirs();
    log.info("creando uploaded: {}", uploaded);
    var uploadedcsv = new File("uploadedcsv/").mkdirs();
    log.info("creando uploadedcsv: {}", uploadedcsv);

    config.staticFiles.add(staticFileConfig -> {
      staticFileConfig.hostedPath = "/assets";
      staticFileConfig.directory = "/assets";
    });
    config.staticFiles.add(staticFileConfig -> {
      staticFileConfig.hostedPath = "/uploaded";
      staticFileConfig.location = Location.EXTERNAL;
      staticFileConfig.directory = "uploaded/";
    });
  }

  private static void initializeValidation(JavalinConfig config) {
    config.validation.register(LocalDate.class, LocalDate::parse);
    config.validation.register(MotivoDeDistribucion.class, v -> switch (v) {
      case "desperfectoheladera" -> MotivoDeDistribucion.DESPERFECTO_HELADERA;
      case "faltadeviandas" -> MotivoDeDistribucion.FALTA_DE_VIANDAS;
      case "noespecificado" -> MotivoDeDistribucion.NO_ESPECIFICADO;
      default -> null;
    });
    config.validation.register(Boolean.class, v -> v != null && v.equals("on"));
  }

  private static int getHerokuAssignedPort() {
    ProcessBuilder processBuilder = new ProcessBuilder();
    if (processBuilder.environment().get("PORT") != null) {
      return Integer.parseInt(processBuilder.environment().get("PORT"));
    }
    return 7070;
  }

}
