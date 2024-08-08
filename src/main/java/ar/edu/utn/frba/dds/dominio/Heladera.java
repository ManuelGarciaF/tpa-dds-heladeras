package ar.edu.utn.frba.dds.dominio;

import static java.util.Objects.requireNonNull;

import ar.edu.utn.frba.dds.dominio.incidentes.AlertaFallaConexion;
import ar.edu.utn.frba.dds.dominio.incidentes.Incidente;
import ar.edu.utn.frba.dds.dominio.incidentes.IncidenteHandler;
import ar.edu.utn.frba.dds.dominio.incidentes.TipoDeFalla;
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
  private final Ubicacion ubicacion;

  private final String numeroDeSerie;

  private final List<UsoTarjetaPersonaVulnerable> usosPersonasVulnerables = new ArrayList<>();
  private final List<Vianda> viandas = new ArrayList<>();
  private final List<AperturaHeladera> aperturasPendientes = new ArrayList<>();
  private final List<AperturaHeladera> aperturasCompletadas = new ArrayList<>();

  private final ProveedorPeso proveedorPeso;
  private final ProveedorTemperatura proveedorTemperatura;
  private final AutorizadorAperturas autorizadorAperturas;
  //comente y puse null para que no se jodan los test que hicieron los chicos
  private final ContadorCantidadViandas contadorCantidadViandas = null;

  private final List<Incidente> incidentesActivos = new ArrayList<>();
  private final IncidenteHandler incidenteHandler = new IncidenteHandler();

  private final RepoTecnicos repoTecnicos;

  public Heladera(String nombre,
                  Integer capacidadViandas,
                  Ubicacion ubicacion,
                  String numeroDeSerie,
                  LocalDate fechaCreacion,
                  ProveedorPeso proveedorPeso,
                  ProveedorTemperatura proveedorTemperatura,
                  AutorizadorAperturas autorizadorAperturas,
            //      ContadorCantidadViandas contadorCantidadViandas,
                  RepoTecnicos repoTecnicos) {
    this.nombre = requireNonNull(nombre);
    this.capacidadViandas = requireNonNull(capacidadViandas);
    this.ubicacion = requireNonNull(ubicacion);
    this.numeroDeSerie = requireNonNull(numeroDeSerie);
    this.proveedorPeso = proveedorPeso;
    this.proveedorTemperatura = proveedorTemperatura;
    this.fechaCreacion = fechaCreacion;
    this.autorizadorAperturas = autorizadorAperturas;
   // this.contadorCantidadViandas = contadorCantidadViandas;
    this.repoTecnicos = repoTecnicos;
  }

  public void ingresarViandas(List<Vianda> viandas) {
    if (this.viandas.size() + viandas.size() > this.capacidadViandas) {
      throw new HeladeraException("Las viandas no entran, capacidad: " + this.capacidadViandas);
    }
    this.viandas.addAll(viandas);
  }

  public List<Vianda> sacarViandas(int cantidad) {
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
    return proveedorTemperatura.requiereAtencion();
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

  //Entrega 3 //TODO: revisar esto
  public void nuevoIncidente(Incidente incidente) {
    incidentesActivos.add(incidente);
    incidenteHandler.notificar(incidente);
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

  public String getNumeroDeSerie() {
    return numeroDeSerie;
  }

  public Integer cantidadDeViandas(){
    return contadorCantidadViandas.getCantidad();
  }

  public List<Incidente> getIncidentesActivos() {
    return incidentesActivos;
  }
}
