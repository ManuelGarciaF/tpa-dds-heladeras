package ar.edu.utn.frba.dds.dominio;

import static java.util.Objects.requireNonNull;

import ar.edu.utn.frba.dds.dominio.incidentes.FallaTecnica;
import java.time.LocalDate;
import java.util.Set;

public class ColaboradorHumano extends Colaborador {
  private String nombre;
  private String apellido;
  private LocalDate fechaDeNacimiento;
  private Set<FormaDeColaboracionHumana> formasDeColaboracion;
  private TipoDocumento tipoDocumento;
  private Integer numeroDocumento;
  private TarjetaColaborador tarjetaColaborador;

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

  //E3
  public void reportarIncidente(FallaTecnica fallaTecnica){
    //repositorio.avisar(fallaTecnica);
    fallaTecnica.getHeladeraDefectuosa().nuevoIncidente(fallaTecnica);
  }
}
