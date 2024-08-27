package ar.edu.utn.frba.dds.dominio;

import static java.util.Objects.requireNonNull;

import ar.edu.utn.frba.dds.dominio.incidentes.AlertaFallaConexion;
import ar.edu.utn.frba.dds.dominio.incidentes.Incidente;
import ar.edu.utn.frba.dds.dominio.incidentes.TipoDeFalla;
import ar.edu.utn.frba.dds.dominio.notificacionesheladera.NotificacionHeladeraHandler;
import ar.edu.utn.frba.dds.dominio.sensoresheladera.ProveedorCantidadDeViandas;
import ar.edu.utn.frba.dds.dominio.sensoresheladera.ProveedorPeso;
import ar.edu.utn.frba.dds.dominio.sensoresheladera.ProveedorTemperatura;
import ar.edu.utn.frba.dds.dominio.tecnicos.RepoTecnicos;
import ar.edu.utn.frba.dds.exceptions.HeladeraException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Heladera {
  private static final Double PESO_ESTANDAR_VIANDA_GRAMOS = 400.0;

  private final String nombre;
  private final Integer capacidadViandas;
  private final LocalDate fechaCreacion;
  private final Double temperaturaMaximaAceptable;
  private final Double temperaturaMinimaAceptable;
  private final Ubicacion ubicacion;
  private final String numeroDeSerie;

  private final List<UsoTarjetaPersonaVulnerable> usosPersonasVulnerables = new ArrayList<>();
  private final List<Vianda> viandas = new ArrayList<>();
  private final List<AperturaHeladera> aperturasPendientes = new ArrayList<>();
  private final List<AperturaHeladera> aperturasCompletadas = new ArrayList<>();

  private final ProveedorPeso proveedorPeso;
  private final ProveedorTemperatura proveedorTemperatura;
  private final AutorizadorAperturas autorizadorAperturas;

  private final List<Incidente> incidentesActivos = new ArrayList<>();

  private final RepoTecnicos repoTecnicos;

  private final ProveedorCantidadDeViandas proveedorCantidadDeViandas;
  private final NotificacionHeladeraHandler notificacionHeladeraHandler;

  public Heladera(String nombre,
                  Integer capacidadViandas,
                  Ubicacion ubicacion,
                  String numeroDeSerie,
                  LocalDate fechaCreacion,
                  Double temperaturaMaximaAceptable,
                  Double temperaturaMinimaAceptable,
                  ProveedorPeso proveedorPeso,
                  ProveedorTemperatura proveedorTemperatura,
                  AutorizadorAperturas autorizadorAperturas,
                  RepoTecnicos repoTecnicos,
                  ProveedorCantidadDeViandas proveedorCantidadDeViandas,
                  NotificacionHeladeraHandler notificacionHeladeraHandler) {
    this.nombre = requireNonNull(nombre);
    this.capacidadViandas = requireNonNull(capacidadViandas);
    this.ubicacion = requireNonNull(ubicacion);
    this.numeroDeSerie = requireNonNull(numeroDeSerie);
    this.proveedorPeso = proveedorPeso;
    this.proveedorTemperatura = proveedorTemperatura;
    this.fechaCreacion = fechaCreacion;
    this.temperaturaMaximaAceptable = temperaturaMaximaAceptable;
    this.temperaturaMinimaAceptable = temperaturaMinimaAceptable;
    this.autorizadorAperturas = autorizadorAperturas;
    this.repoTecnicos = repoTecnicos;
    this.proveedorCantidadDeViandas = proveedorCantidadDeViandas;
    this.notificacionHeladeraHandler = notificacionHeladeraHandler;

    configurarSensores(proveedorTemperatura,
        proveedorCantidadDeViandas,
        notificacionHeladeraHandler);
  }

  // Configurar handlers automaticos para los sensores
  private void configurarSensores(ProveedorTemperatura proveedorTemperatura,
                                  ProveedorCantidadDeViandas proveedorCantidadDeViandas,
                                  NotificacionHeladeraHandler notificacionHeladeraHandler) {
    if (proveedorTemperatura != null) {
      proveedorTemperatura.setCheckeoDeTemperaturaHandler(() -> {
        if (requiereAtencion()) {
          nuevoIncidente(
              new AlertaFallaConexion(LocalDateTime.now(), TipoDeFalla.SENSOR_DE_TEMPERATURA));
        }
      });
    }

    if (proveedorCantidadDeViandas != null) {
      proveedorCantidadDeViandas.setNuevaMedicionHandler(
          () -> notificacionHeladeraHandler.notificarCambioCantidadDeViandas(this)
      );
    }
  }

  public void ingresarViandas(List<Vianda> viandas) {
    if (this.viandas.size() + viandas.size() > this.capacidadViandas) {
      throw new HeladeraException("Las viandas no entran, capacidad: " + this.capacidadViandas);
    }
    this.viandas.addAll(viandas);
  }

  public List<Vianda> sacarViandas(Integer cantidad) {
    if (cantidad > this.viandas.size()) {
      throw new HeladeraException("No hay suficientes viandas, cantidad: " + this.viandas.size());
    }
    List<Vianda> removidas = new ArrayList<>(this.viandas.subList(0, cantidad));
    this.viandas.subList(0, cantidad).clear();
    return removidas;
  }

  public List<Vianda> listarViandas() {
    return viandas;
  }

  public void registrarUso(UsoTarjetaPersonaVulnerable uso) {
    this.usosPersonasVulnerables.add(uso);
  }

  public boolean tieneNombre(String nombreHeladera) {
    return this.nombre.equals(nombreHeladera);
  }

  public List<UsoTarjetaPersonaVulnerable> getUsosPersonasVulnerables() {
    return this.usosPersonasVulnerables;
  }

  public Ubicacion getUbicacion() {
    return this.ubicacion;
  }

  public Integer mesesActivos() {
    return (int) ChronoUnit.MONTHS.between(fechaCreacion, LocalDate.now());
  }

  public NivelLlenado nivelLlenado() {
    Double capacidadTotalGramos = capacidadViandas * PESO_ESTANDAR_VIANDA_GRAMOS;
    return NivelLlenado.of(proveedorPeso.obtenerPesoGramos(this), capacidadTotalGramos);
  }

  public boolean requiereAtencion() {
    var ultimasTresTemperaturas = proveedorTemperatura.ultimasTresTemperaturas();
    // Si tenemos al menos 3 temperaturas y todas son mayores a la maxima o menores a la minima
    boolean masAltas = ultimasTresTemperaturas.stream().allMatch(
        temperatura -> temperatura > temperaturaMaximaAceptable);
    boolean masBajas = ultimasTresTemperaturas.stream().allMatch(
        temperatura -> temperatura < temperaturaMinimaAceptable);
    return ultimasTresTemperaturas.size() >= 3 && (masAltas || masBajas);

  }

  public Integer cantidadUsos() {
    return usosPersonasVulnerables.size();
  }

  public void agregarUso(UsoTarjetaPersonaVulnerable uso) {
    this.usosPersonasVulnerables.add(uso);
  }

  public List<UsoTarjetaPersonaVulnerable> usosDeTarjetaPersonaVulnerable(String codigotarjeta) {
    return usosPersonasVulnerables.stream()
        .filter(u -> u.tarjetaPersonaVulnerable().esDeCodigo(codigotarjeta))
        .toList();
  }

  public void agregarSolicitudApertura(AperturaHeladera apertura) {
    // Notificar al controlador de acceso
    autorizadorAperturas.habilitarTarjeta(apertura.getTarjetaColaborador());
    aperturasPendientes.add(apertura);
  }

  public void registrarApertura(ColaboradorHumano colaborador, LocalDateTime fechaApertura) {
    AperturaHeladera apertura = buscarAperturaValida(colaborador, fechaApertura);

    aperturasPendientes.remove(apertura);
    apertura.setFechaApertura(fechaApertura);
    aperturasCompletadas.add(apertura);
  }

  private AperturaHeladera buscarAperturaValida(ColaboradorHumano colaborador,
                                                LocalDateTime fechaApertura) {
    return aperturasPendientes.stream()
        .filter(a -> a.getColaboradorHumano().equals(colaborador) && a.esValida(fechaApertura))
        .findFirst()
        .orElseThrow(() -> new HeladeraException("No hay aperturas validas para este colaborador"));
  }

  public List<AperturaHeladera> getAperturasCompletadas() {
    return aperturasCompletadas;
  }

  public List<AperturaHeladera> getAperturasPendientes() {
    return aperturasPendientes;
  }

  public List<AperturaHeladera> getAperturasValidas() {
    return aperturasPendientes.stream()
        .filter(AperturaHeladera::esValidaAhora)
        .toList();
  }

  public void nuevoIncidente(Incidente incidente) {
    incidentesActivos.add(incidente);
    repoTecnicos.delegarReparacion(this);
    notificacionHeladeraHandler.notificarIncidente(this);
  }

  public void checkearDesconexionSensorTemperatura() {
    if (proveedorTemperatura.hayFalloConexion()) {
      nuevoIncidente(
          new AlertaFallaConexion(LocalDateTime.now(), TipoDeFalla.SENSOR_DE_TEMPERATURA)
      );
    }
  }

  public boolean estaActiva() {
    return incidentesActivos.isEmpty();
  }

  public void limpiarIncidentes() {
    incidentesActivos.clear();
  }

  public String getNumeroDeSerie() {
    return numeroDeSerie;
  }

  public List<Incidente> getIncidentesActivos() {
    return incidentesActivos;
  }

  public Integer getCantidadDeViandas() {
    return proveedorCantidadDeViandas.getCantidadDeViandas();
  }

  //E3 REQ 5
  public NotificacionHeladeraHandler getNotificacionHeladeraHandler() {
    return notificacionHeladeraHandler;
  }

  public Integer espacioRestante() {
    return capacidadViandas - this.getCantidadDeViandas();
  }

  public Double distanciaA(Heladera otra) {
    return this.ubicacion.distanciaA(otra.getUbicacion());
  }
}
