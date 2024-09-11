package ar.edu.utn.frba.dds.dominio;

import static java.util.Objects.requireNonNull;

import ar.edu.utn.frba.dds.PersistentEntity;
import ar.edu.utn.frba.dds.dominio.colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.exceptions.ColaboracionException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Colaborador extends PersistentEntity {
  private final String direccion;

  @Embedded
  private final MedioDeContacto medioDeContacto;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "colaborador_id")
  private final List<Colaboracion> historialDeColaboraciones = new ArrayList<>();

  public Colaborador(String direccion, MedioDeContacto medioDeContacto) {
    this.direccion = direccion;
    this.medioDeContacto = requireNonNull(medioDeContacto);
  }

  public Colaborador() {
    this.direccion = null;
    this.medioDeContacto = null;
  }

  public List<Colaboracion> getHistorialDeColaboraciones() {
    return historialDeColaboraciones;
  }

  public void colaborar(Colaboracion unaColaboracion) {
    if (!unaColaboracion.puedeSerRealizadaPor(this)) {
      throw new ColaboracionException(
          "Esta colaboración no puede ser realizada por este colaborador");
    }
    historialDeColaboraciones.add(unaColaboracion);
  }

  public String getDireccion() {
    return direccion;
  }

  public MedioDeContacto getMedioDeContacto() {
    return medioDeContacto;
  }

  public Double puntaje() {
    return historialDeColaboraciones.stream()
        .mapToDouble(Colaboracion::puntaje).sum();
  }

  public abstract boolean esDeDocumento(TipoDocumento tipoDocumento, Integer numeroDocumento);
}
