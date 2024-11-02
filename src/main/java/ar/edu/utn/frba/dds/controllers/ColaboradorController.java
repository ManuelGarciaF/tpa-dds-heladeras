package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.model.Colaborador;
import ar.edu.utn.frba.dds.model.Heladera;
import ar.edu.utn.frba.dds.model.Ubicacion;
import ar.edu.utn.frba.dds.model.Vianda;
import ar.edu.utn.frba.dds.model.colaboraciones.DonacionDeVianda;
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
import javax.xml.bind.ValidationException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColaboradorController implements WithSimplePersistenceUnit {
  private static final Logger log = LoggerFactory.getLogger(ColaboradorController.class);

  public void formasDeColaborar(@NotNull Context ctx) {
    Long user_id = ctx.sessionAttribute("user_id");
    // TODO cachear esto para no buscarlo en la db cada vez
    String nombre = RepoUsuarios.getInstance().findById(user_id).getNombre();
    String message = ctx.consumeSessionAttribute("message");

    var model = new HashMap<String, Object>();
    model.put("username", nombre);
    model.put("message", message);
    ctx.render("colaborar.hbs", model);
  }

  public void hacerseCargoDeHeladeraForm(@NotNull Context ctx) {
    ctx.render("nuevaheladera.hbs");
  }

  public void hacerseCargoDeHeladeraPost(@NotNull Context ctx) {
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
    int capacidadViandas = ctx.formParamAsClass("capacidadviandas", Integer.class)
        .check(it -> it > 0, "Capacidad de viandas debe ser mayor a 0")
        .get();

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

  public void donacionDeViandasForm(@NotNull Context ctx) {
    List<Heladera> heladeras = MapaHeladeras.getInstance().listarHeladeras();
    var model = new HashMap<String, Object>();

    model.put("heladeras", heladeras.stream().map(h ->
        Map.of("nombre", h.getNombre(), "id", h.getId())
    ).toList());
    model.put("currDate", LocalDate.now());
    ctx.render("donarviandas.hbs", model);
  }

  public void donacionDeViandasPost(@NotNull Context ctx) throws ValidationException {
    String tipoDeComida = ctx.formParamAsClass("tipocomida", String.class)
        .check(it -> !it.isEmpty(), "Tipo de comida no puede estar vacío")
        .get();
    LocalDate fechavencimiento = ctx.formParamAsClass("fechavencimiento", LocalDate.class)
        .check(it -> it.isAfter(LocalDate.now()), "Fecha de vencimiento inválida")
        .get();
    Integer calorias = ctx.formParamAsClass("calorias", Integer.class)
        .check(it -> it > 0, "Calorias debe ser mayor a 0")
        .getOrDefault(null);
    Integer peso = ctx.formParamAsClass("peso", Integer.class)
        .check(it -> it > 0, "Peso debe ser mayor a 0")
        .getOrDefault(null);

    Long heladeraId = ctx.formParamAsClass("heladera", Long.class)
        .get();
    Heladera heladera = MapaHeladeras.getInstance().buscarPorId(heladeraId);
    if (heladera == null) { // Validar que la heladera exista
      throw new ValidationException("Heladera inválida");
    }

    Long usuarioID = ctx.sessionAttribute("user_id");
    Colaborador colaborador = RepoColaboradores.getInstance().buscarPorIdUsuario(usuarioID);

    withTransaction(() -> {
      Vianda vianda = new Vianda(tipoDeComida, fechavencimiento, calorias, peso);
      heladera.agregarVianda(vianda);
      colaborador.colaborar(new DonacionDeVianda(vianda, heladera));
    });

    ctx.sessionAttribute("message", "Colaboración registrada con éxito");
    ctx.redirect("/");
  }

  public void registrarPersonaForm(@NotNull Context ctx) {
    ctx.render("registrarpersona.hbs");
  }

  public void registrarPersonaPost(@NotNull Context ctx) {
  }

  public void donacionDeDineroForm(@NotNull Context ctx) {
  }

  public void donacionDeDineroPost(@NotNull Context ctx) {
  }

  public void distribucionDeViandasForm(@NotNull Context ctx) {
  }

  public void distribucionDeViandasPost(@NotNull Context ctx) {
  }
}
