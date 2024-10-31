package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.model.Colaborador;
import ar.edu.utn.frba.dds.model.Heladera;
import ar.edu.utn.frba.dds.model.Ubicacion;
import ar.edu.utn.frba.dds.model.colaboraciones.HacerseCargoHeladera;
import ar.edu.utn.frba.dds.model.repositorios.MapaHeladeras;
import ar.edu.utn.frba.dds.model.repositorios.RepoColaboradores;
import ar.edu.utn.frba.dds.model.repositorios.RepoTecnicos;
import ar.edu.utn.frba.dds.model.repositorios.RepoUsuarios;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColaboradorController implements WithSimplePersistenceUnit {
  private static final Logger log = LoggerFactory.getLogger(ColaboradorController.class);

  public void listaColaboraciones(@NotNull Context ctx) {
    Long user_id = ctx.sessionAttribute("user_id");
    // TODO cachear esto para no buscarlo en la db cada vez
    String nombre = RepoUsuarios.getInstance().findById(user_id).getNombre();
    String message = ctx.consumeSessionAttribute("message");

    var model = new HashMap<String, Object>();
    model.put("username", nombre);
    model.put("message", message);
    ctx.render("colaborar.hbs", model);
  }

  public void nuevaHeladeraGet(@NotNull Context ctx) {
    ctx.render("nuevaheladera.hbs");
  }

  public void nuevaHeladeraPost(@NotNull Context ctx) {
    Double lat = ctx.formParamAsClass("latitud", Double.class).getOrDefault(null);
    Double lng = ctx.formParamAsClass("longitud", Double.class).getOrDefault(null);
    // Campos ocultos no son validados del lado del cliente, hay que hacerlo acá
    if (lat == null || lng == null) {
      var model = Map.of("errors", List.of("Ubicación inválida"));
      ctx.render("nuevaheladera.hbs", model);
      return;
    }

    String nombre = ctx.formParamAsClass("nombre", String.class)
        .check(it -> !it.isEmpty(), "Nombre no puede estar vacío")
        .get();
    String numeroSerie = ctx.formParamAsClass("numeroserie", String.class)
        .check(it -> !it.isEmpty(), "Número de serie no puede estar vacío")
        .get();
    int capacidadViandas = ctx.formParamAsClass("capacidadviandas", Integer.class).get();

    // FIXME borrar
    log.info("Se recibio: {}-{}, nombre: {}, numeroserie: {}, capacidad: {}", lat, lng, nombre,
        numeroSerie, capacidadViandas);

    Long usuarioID = ctx.sessionAttribute("user_id");
    Colaborador colaborador = RepoColaboradores.getInstance().buscarPorIdUsuario(usuarioID);

    withTransaction(() -> {
      Heladera heladera = new Heladera(
          nombre,
          capacidadViandas,
          new Ubicacion(lat, lng),
          numeroSerie,
          LocalDate.now(),
          2.0,
          8.0,
          null,
          null,
          null,
          null,
          null,
          RepoTecnicos.getInstance()
      );
      MapaHeladeras.getInstance().agregarHeladera(heladera);
      colaborador.colaborar(new HacerseCargoHeladera(heladera));
    });

    ctx.sessionAttribute("message", "Heladera registrada con éxito");
    ctx.redirect("/");
  }

  public void donarViandasGet(@NotNull Context ctx) {
    List<Heladera> heladeras = MapaHeladeras.getInstance().listarHeladeras();

    var model = new HashMap<String, Object>();
    model.put("heladeras", heladeras.stream().map(h ->
        Map.of("nombre", h.getNombre(), "id", h.getId())
    ).toList());
    ctx.render("donarviandas.hbs", model);
  }

  public void donarViandasPost(@NotNull Context ctx) {
  }

  public void registrarPersonaGet(@NotNull Context ctx) {
  }

  public void registrarPersonaPost(@NotNull Context ctx) {
  }

  public void donarDineroGet(@NotNull Context ctx) {
  }

  public void donarDineroPost(@NotNull Context ctx) {
  }

  public void distribuirViandasGet(@NotNull Context ctx) {
  }

  public void distribuirViandasPost(@NotNull Context ctx) {
  }
}
