package ar.edu.utn.frba.dds.model;

import static java.util.Objects.requireNonNull;

import ar.edu.utn.frba.dds.model.notificacionesheladera.ProveedorMensajeria;
import ar.edu.utn.frba.dds.model.notificacionesheladera.SugerenciaTrasladoDeViandas;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class ColaboradorHumano extends Colaborador {
  private String nombre;
  private String apellido;
  private LocalDate fechaDeNacimiento;

  @ElementCollection
  @Enumerated(javax.persistence.EnumType.STRING)
  private Set<FormaDeColaboracionHumana> formasDeColaboracion;

  @Enumerated(javax.persistence.EnumType.STRING)
  private TipoDocumento tipoDocumento;

  private Integer numeroDocumento;

  @OneToOne(cascade = CascadeType.PERSIST)
  private TarjetaColaborador tarjetaColaborador;

  @ManyToMany
  private final List<SugerenciaTrasladoDeViandas> sugerenciasPendientes = new ArrayList<>();

  @OneToOne(cascade = CascadeType.PERSIST)
  private ProveedorMensajeria proveedorMensajeria;

  public ColaboradorHumano(String nombre,
                           String apellido,
                           LocalDate fechaDeNacimiento,
                           String direccion,
                           MedioDeContacto medioDeContacto,
                           Usuario usuario,
                           Set<FormaDeColaboracionHumana> formasDeColaboracion,
                           TipoDocumento tipoDocumento,
                           Integer numeroDocumento,
                           ProveedorMensajeria proveedorMensajeria) {
    super(direccion, medioDeContacto, usuario);
    this.nombre = requireNonNull(nombre);
    this.apellido = requireNonNull(apellido);
    this.fechaDeNacimiento = fechaDeNacimiento;
    this.formasDeColaboracion = formasDeColaboracion;
    this.tipoDocumento = requireNonNull(tipoDocumento);
    this.numeroDocumento = requireNonNull(numeroDocumento);
    this.proveedorMensajeria = proveedorMensajeria;
  }

  public ColaboradorHumano() {
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
