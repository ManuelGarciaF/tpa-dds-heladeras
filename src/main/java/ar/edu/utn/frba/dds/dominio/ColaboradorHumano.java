package ar.edu.utn.frba.dds.dominio;

import static java.util.Objects.requireNonNull;

import ar.edu.utn.frba.dds.dominio.incidentes.FallaTecnica;
import ar.edu.utn.frba.dds.dominio.notificacionesHeladera.NotificacionHeladeraHandler;
import ar.edu.utn.frba.dds.dominio.notificacionesHeladera.NotificacionHeladeraObserver;
import ar.edu.utn.frba.dds.dominio.notificacionesHeladera.NotificarFaltaDeViandas;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

// Se me ocurrio que cuando se suscribe a una heladera, que el colaborador tenga las notificaciones a la que fue creado, y podemos ver si nos combiene
// delegar toda la logica a este

public class ColaboradorHumano extends Colaborador {
  private String nombre;
  private String apellido;
  private LocalDate fechaDeNacimiento;
  private Set<FormaDeColaboracionHumana> formasDeColaboracion;
  private TipoDocumento tipoDocumento;
  private Integer numeroDocumento;
  private TarjetaColaborador tarjetaColaborador;
  private List<String> alertasEnviadas = new ArrayList<String>();

  public ColaboradorHumano(String nombre,
                           String apellido,
                           LocalDate fechaDeNacimiento,
                           String direccion,
                           MedioDeContacto medioDeContacto,
                           Set<FormaDeColaboracionHumana> formasDeColaboracion,
                           TipoDocumento tipoDocumento,
                           Integer numeroDocumento) {
    super(direccion, medioDeContacto);
    this.nombre = requireNonNull(nombre);
    this.apellido = requireNonNull(apellido);
    this.fechaDeNacimiento = fechaDeNacimiento;
    this.formasDeColaboracion = formasDeColaboracion;
    this.tipoDocumento = requireNonNull(tipoDocumento);
    this.numeroDocumento = requireNonNull(numeroDocumento);
  }

  public void setTarjetaColaborador(TarjetaColaborador tarjetaColaborador) {
    this.tarjetaColaborador = requireNonNull(tarjetaColaborador);
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

  @Override
  public boolean esDeDocumento(TipoDocumento tipoDocumento, Integer numeroDocumento) {
    return this.tipoDocumento.equals(tipoDocumento) && this.numeroDocumento.equals(numeroDocumento);
  }

  public List<String> getAlertasEnviadas() {
    return alertasEnviadas;
  }

  public void agregarAlerta(String alerta){
    alertasEnviadas.add(alerta);
  }


}
