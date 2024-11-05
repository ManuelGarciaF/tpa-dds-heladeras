package ar.edu.utn.frba.dds.model.incidentes;

import ar.edu.utn.frba.dds.PersistentEntity;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Incidente extends PersistentEntity {
  private LocalDateTime fecha;

  public Incidente(LocalDateTime fecha) {
    this.fecha = fecha;
  }

  public Incidente() {
  }

  public LocalDateTime getFecha() {
    return fecha;
  }

  public abstract String getDescripcionDelError();

  public abstract String getTitulo();
}
