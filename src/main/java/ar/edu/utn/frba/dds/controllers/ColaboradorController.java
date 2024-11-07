package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.externo.ControladorDeAccesoImpl;
import ar.edu.utn.frba.dds.externo.LSensorImpl;
import ar.edu.utn.frba.dds.externo.TSensorImpl;
import ar.edu.utn.frba.dds.externo.WSensorImpl;
import ar.edu.utn.frba.dds.model.AutorizadorAperturasActual;
import ar.edu.utn.frba.dds.model.Colaborador;
import ar.edu.utn.frba.dds.model.Heladera;
import ar.edu.utn.frba.dds.model.MotivoDeDistribucion;
import ar.edu.utn.frba.dds.model.PersonaVulnerable;
import ar.edu.utn.frba.dds.model.TarjetaPersonaVulnerable;
import ar.edu.utn.frba.dds.model.Ubicacion;
import ar.edu.utn.frba.dds.model.Vianda;
import ar.edu.utn.frba.dds.model.colaboraciones.DistribucionDeViandas;
import ar.edu.utn.frba.dds.model.colaboraciones.DonacionDeDinero;
import ar.edu.utn.frba.dds.model.colaboraciones.DonacionDeVianda;
import ar.edu.utn.frba.dds.model.colaboraciones.HacerseCargoHeladera;
import ar.edu.utn.frba.dds.model.colaboraciones.RegistroDePersonaVulnerable;
import ar.edu.utn.frba.dds.model.notificacionesheladera.NotificacionHeladeraHandler;
import ar.edu.utn.frba.dds.model.repositorios.MapaHeladeras;
import ar.edu.utn.frba.dds.model.repositorios.RepoColaboradores;
import ar.edu.utn.frba.dds.model.repositorios.RepoTecnicos;
import ar.edu.utn.frba.dds.model.repositorios.RepoUsuarios;
import ar.edu.utn.frba.dds.model.sensoresheladera.ProveedorCantidadDeViandasSensor;
import ar.edu.utn.frba.dds.model.sensoresheladera.ProveedorPesoSensor;
import ar.edu.utn.frba.dds.model.sensoresheladera.ProveedorTemperaturaSensor;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColaboradorController implements WithSimplePersistenceUnit {
  private static final Logger log = LoggerFactory.getLogger(ColaboradorController.class);

  public void formasDeColaborar(@NotNull Context ctx) {
    Long idUsuario = ctx.sessionAttribute("user_id");
    String nombre = ctx.cachedSessionAttributeOrCompute("username", (c) ->
        RepoUsuarios.getInstance().findById(idUsuario).getNombre());
    String message = ctx.consumeSessionAttribute("message");

    var model = new HashMap<String, Object>();
    model.put("username", nombre);
    model.put("message", message);
    ctx.render("colaboraciones.hbs", model);
  }

  public void hacerseCargoDeHeladeraForm(@NotNull Context ctx) {
    ctx.render("hacersecargodeheladeraform.hbs");
  }

  public void hacerseCargoDeHeladeraPost(@NotNull Context ctx) {
    Double lat = ctx.formParamAsClass("latitud", Double.class).getOrDefault(null);
    Double lng = ctx.formParamAsClass("longitud", Double.class).getOrDefault(null);
    // Campos ocultos no son validados del lado del cliente, hay que hacerlo acá
    if (lat == null || lng == null) {
      var model = Map.of("errors", List.of("Ubicación inválida"));
      ctx.render("hacersecargodeheladeraform.hbs", model);
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
          new ProveedorPesoSensor(new WSensorImpl()), // Usamos las implementaciones de mentira
          new ProveedorTemperaturaSensor(new TSensorImpl(), numeroSerie),
          new AutorizadorAperturasActual(new ControladorDeAccesoImpl()),
          new ProveedorCantidadDeViandasSensor(new LSensorImpl()),
          new NotificacionHeladeraHandler(),
          RepoTecnicos.getInstance()
      );
      MapaHeladeras.getInstance().agregarHeladera(heladera);
      colaborador.colaborar(new HacerseCargoHeladera(heladera));
    });

    ctx.sessionAttribute("message", "Heladera registrada con éxito");
    ctx.redirect("/");
  }

  public void donacionDeViandasForm(@NotNull Context ctx) {
    var model = new HashMap<String, Object>();
    obtenerListaDeHeladeras(model);
    model.put("currDate", LocalDate.now());
    ctx.render("donaciondeviandasform.hbs", model);
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
      ctx.status(400);
      return;
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
    ctx.render("registrarpersonaform.hbs");
  }

  public void registrarPersonaPost(@NotNull Context ctx) {
    String nombre = ctx.formParamAsClass("nombre", String.class)
        .check(it -> !it.isEmpty(), "Nombre no puede estar vacío")
        .get();
    LocalDate fechaDeNacimiento = ctx.formParamAsClass("fechadenacimiento", LocalDate.class)
        .check(it -> it.isBefore(LocalDate.now()), "Fecha de nacimiento inválida")
        .get();
    Integer menoresACargo = ctx.formParamAsClass("menoresacargo", Integer.class)
        .check(it -> it >= 0, "Menores a cargo debe ser mayor o igual a 0")
        .get();
    String domicilio = ctx.formParamAsClass("domicilio", String.class)
        .check(it -> !it.isEmpty(), "Domicilio no puede estar vacío")
        .getOrDefault(null);
    String codigoDeTarjeta = ctx.formParamAsClass("codigotarjeta", String.class)
        .check(it -> !it.isEmpty(), "Código de tarjeta no puede estar vacío")
        .get();

    Long usuarioID = ctx.sessionAttribute("user_id");
    Colaborador colaborador = RepoColaboradores.getInstance().buscarPorIdUsuario(usuarioID);

    withTransaction(() -> {
      var personaVulnerable = new PersonaVulnerable(
          nombre,
          domicilio,
          fechaDeNacimiento,
          LocalDate.now(),
          menoresACargo,
          new TarjetaPersonaVulnerable(codigoDeTarjeta, MapaHeladeras.getInstance())
      );
      colaborador.colaborar(new RegistroDePersonaVulnerable(personaVulnerable));
    });

    ctx.sessionAttribute("message", "Persona registrada con éxito");
    ctx.redirect("/");
  }

  public void donacionDeDineroForm(@NotNull Context ctx) {
    ctx.render("donaciondedineroform.hbs");
  }

  public void donacionDeDineroPost(@NotNull Context ctx) {
    Integer monto = ctx.formParamAsClass("monto", Integer.class)
        .check(it -> it > 0, "Monto debe ser mayor a 0")
        .get();
    String donacionPeriodicaStr = ctx.formParam("donacionperiodica");
    boolean donacionPeriodica = donacionPeriodicaStr != null && donacionPeriodicaStr.equals("on");
    Integer frecuenciaEnDias = donacionPeriodica ? ctx.formParamAsClass("frecuencia", Integer.class)
        .check(it -> it > 0, "Frecuencia en días debe ser mayor a 0")
        .get() : null;

    Long usuarioID = ctx.sessionAttribute("user_id");
    Colaborador colaborador = RepoColaboradores.getInstance().buscarPorIdUsuario(usuarioID);

    withTransaction(() -> {
      colaborador.colaborar(new DonacionDeDinero(
          LocalDate.now(),
          monto,
          donacionPeriodica,
          frecuenciaEnDias
      ));
    });

    ctx.sessionAttribute("message", "Colaboración registrada con éxito");
    ctx.redirect("/");
  }

  public void distribucionDeViandasForm(@NotNull Context ctx) {
    var model = new HashMap<String, Object>();
    obtenerListaDeHeladeras(model);
    ctx.render("distribuciondeviandasform.hbs", model);
  }

  public void distribucionDeViandasPost(@NotNull Context ctx) throws ValidationException {
    Long heladeraOrigenId = ctx.formParamAsClass("heladeraorigen", Long.class)
        .get();
    Heladera heladeraOrigen = MapaHeladeras.getInstance().buscarPorId(heladeraOrigenId);
    if (heladeraOrigen == null) { // Validar que la heladera exista
      ctx.status(400);
      return;
    }

    Long heladeraDestinoId = ctx.formParamAsClass("heladeradestino", Long.class)
        .get();
    Heladera heladeraDestino = MapaHeladeras.getInstance().buscarPorId(heladeraDestinoId);
    if (heladeraDestino == null) { // Validar que la heladera exista
      ctx.status(400);
      return;
    }

    if (heladeraOrigenId.equals(heladeraDestinoId)) {
      var model = new HashMap<String, Object>();
      obtenerListaDeHeladeras(model);
      model.put("errors", List.of("Las heladeras deben ser distintas"));
      ctx.render("distribuciondeviandasform.hbs", model);
      return;
    }

    MotivoDeDistribucion motivoDeDistribucion = ctx.formParamAsClass("motivo",
            MotivoDeDistribucion.class)
        .get();

    Integer cantidadDeViandas = ctx.formParamAsClass("cantidadtransladada", Integer.class)
        .check(it -> it > 0, "Cantidad de viandas debe ser mayor a 0")
        .get();

    Long usuarioID = ctx.sessionAttribute("user_id");
    Colaborador colaborador = RepoColaboradores.getInstance().buscarPorIdUsuario(usuarioID);

    withTransaction(() -> {
      colaborador.colaborar(new DistribucionDeViandas(
          motivoDeDistribucion,
          LocalDate.now(),
          cantidadDeViandas,
          heladeraOrigen,
          heladeraDestino
      ));
    });

    ctx.sessionAttribute("message", "Colaboración registrada con éxito");
    ctx.redirect("/");
  }

  private static void obtenerListaDeHeladeras(HashMap<String, Object> model) {
    List<Heladera> heladeras = MapaHeladeras.getInstance().listarHeladeras();
    model.put("heladeras", heladeras.stream().map(h ->
        Map.of("nombre", h.getNombre(), "id", h.getId())
    ).toList());
  }

}
