package ar.edu.utn.frba.dds.dominio;

import static java.util.Objects.requireNonNull;

import ar.edu.utn.frba.dds.dominio.notificacionesheladera.ProveedorMensajeria;
import ar.edu.utn.frba.dds.dominio.notificacionesheladera.SugerenciaTrasladoDeViandas;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ColaboradorHumano extends Colaborador {
  private final String nombre;
  private final String apellido;
  private final LocalDate fechaDeNacimiento;
  private final Set<FormaDeColaboracionHumana> formasDeColaboracion;
  private final TipoDocumento tipoDocumento;
  private final Integer numeroDocumento;
  private TarjetaColaborador tarjetaColaborador;

  private final List<SugerenciaTrasladoDeViandas> sugerenciasPendientes = new ArrayList<>();

  private final ProveedorMensajeria proveedorMensajeria;

  public ColaboradorHumano(String nombre,
                           String apellido,
                           LocalDate fechaDeNacimiento,
                           String direccion,
                           MedioDeContacto medioDeContacto,
                           Set<FormaDeColaboracionHumana> formasDeColaboracion,
                           TipoDocumento tipoDocumento,
                           Integer numeroDocumento,
                           ProveedorMensajeria proveedorMensajeria) {
    super(direccion, medioDeContacto);
    this.nombre = requireNonNull(nombre);
    this.apellido = requireNonNull(apellido);
    this.fechaDeNacimiento = fechaDeNacimiento;
    this.formasDeColaboracion = formasDeColaboracion;
    this.tipoDocumento = requireNonNull(tipoDocumento);
    this.numeroDocumento = requireNonNull(numeroDocumento);
    this.proveedorMensajeria = proveedorMensajeria;
  }

  @Override
  public boolean esDeDocumento(TipoDocumento tipoDocumento, Integer numeroDocumento) {
    return this.tipoDocumento.equals(tipoDocumento) && this.numeroDocumento.equals(numeroDocumento);
  }

  public void notificar(String mensaje) {
    proveedorMensajeria.enviarMensaje(mensaje);
  }

  public void agregarSugerenciaTrasladoDeViandas(
      SugerenciaTrasladoDeViandas sugerenciaTrasladoDeViandas
  ) {
    sugerenciasPendientes.add(sugerenciaTrasladoDeViandas);
  }

  public String getNombre() {
    return nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public LocalDate getFechaDeNacimiento() {
    return fechaDeNacimiento;
  }

  public Set<FormaDeColaboracionHumana> getFormasDeColaboracion() {
    return formasDeColaboracion;
  }

  public TarjetaColaborador getTarjetaColaborador() {
    return tarjetaColaborador;
  }

  public List<SugerenciaTrasladoDeViandas> getSugerenciasPendientes() {
    return sugerenciasPendientes;
  }

  public void setTarjetaColaborador(TarjetaColaborador tarjetaColaborador) {
    this.tarjetaColaborador = requireNonNull(tarjetaColaborador);
  }
}
