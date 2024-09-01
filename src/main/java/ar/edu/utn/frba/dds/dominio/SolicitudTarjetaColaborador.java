package ar.edu.utn.frba.dds.dominio;

import ar.edu.utn.frba.dds.PersistentEntity;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Entity
public class SolicitudTarjetaColaborador extends PersistentEntity {

  @ManyToOne
  private ColaboradorHumano colaboradorHumano;

  private LocalDate fechaSolicitud;

  @Enumerated(EnumType.STRING)
  private EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;

  public SolicitudTarjetaColaborador(
      ColaboradorHumano colaboradorHumano,
      LocalDate fechaSolicitud
  ) {
    this.colaboradorHumano = colaboradorHumano;
    this.fechaSolicitud = fechaSolicitud;
  }

  public SolicitudTarjetaColaborador() {
  }

  public EstadoSolicitud getEstado() {
    return estado;
  }

  public void aceptar(TarjetaColaborador tarjetaColaborador) {
    estado = EstadoSolicitud.ACEPTADA;
    colaboradorHumano.setTarjetaColaborador(tarjetaColaborador);
  }

  public void rechazar() {
    estado = EstadoSolicitud.RECHAZADA;
  }
}
