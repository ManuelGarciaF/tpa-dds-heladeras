package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.exceptions.CsvInvalidoException;
import ar.edu.utn.frba.dds.model.repositorios.RepoColaboradores;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import io.javalin.util.FileUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class AdminController implements WithSimplePersistenceUnit {
  public void loginForm(@NotNull Context ctx) {
    var model = new HashMap<String, Object>();
    model.put("errors", ctx.consumeSessionAttribute("errors"));
    ctx.render("loginadmin.hbs", model);
  }

  public void loginPost(@NotNull Context ctx) {
    final String ADMIN_USERNAME = "admin"; // TODO hacer algo mejor
    final String ADMIN_PASSWORD = "admin";

    String username = ctx.formParamAsClass("username", String.class)
        .check(it -> !it.isEmpty(), "Usuario no puede estar vacío")
        .get();
    String password = ctx.formParamAsClass("password", String.class)
        .check(it -> !it.isEmpty(), "Contraseña no puede estar vacía")
        .get();

    if (!username.equals(ADMIN_USERNAME) || !password.equals(ADMIN_PASSWORD)) {
      ctx.sessionAttribute("errors", List.of("Usuario o contraseña incorrectos"));
      ctx.redirect("/admin/login");
      return;
    }

    ctx.sessionAttribute("is_admin", true);
    ctx.redirect("/admin");
  }

  public void logout(@NotNull Context ctx) {
    ctx.sessionAttribute("is_admin", null);
    ctx.redirect("/admin/login");
  }

  public void cargarCsvForm(@NotNull Context ctx) {
    var model = new HashMap<String, Object>();
    model.put("errors", ctx.consumeSessionAttribute("errors"));
    ctx.render("cargarcsv.hbs", model);
  }

  public void cargarCsvPost(@NotNull Context ctx) {
    UploadedFile csv = ctx.uploadedFile("csv");
    if (csv == null) {
      ctx.sessionAttribute("errors", List.of("Debe seleccionar un archivo CSV"));
      ctx.redirect("/admin/csvs/new");
      return;
    }

    // Guardar el archivo
    String path = "uploadedcsv/" + csv.filename();
    FileUtil.streamToFile(csv.content(), path);

    // Cargar los datos del archivo
    try {
      withTransaction(() -> RepoColaboradores.getInstance().cargarDeCsv(path));
    } catch (CsvInvalidoException e) {
      ctx.sessionAttribute("errors", List.of("El archivo CSV es inválido"));
      ctx.redirect("/admin/csvs/new");
      return;
    }

    ctx.render("cargarcsv.hbs", Map.of("success", "Archivo cargado exitosamente"));
  }
}
