package ar.edu.utn.frba.dds.dominio.incidentes;

import ar.edu.utn.frba.dds.dominio.Colaborador;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.hibernate.engine.internal.Cascade;

@Entity
public class FallaTecnica extends Incidente {
  private String descripcionDelError;
  private String urlFoto;

  @ManyToOne
  private Colaborador colaborador;

  public FallaTecnica(Colaborador colaborador,
                      LocalDateTime fecha,
                      String descripcionDelError,
                      String urlFoto) {
    super(fecha);
    this.colaborador = colaborador;
    this.descripcionDelError = descripcionDelError;
    this.urlFoto = urlFoto;
  }

  public FallaTecnica() {
  }

  @Override
  public String getDescripcionDelError() {
    return descripcionDelError;
  }

  public Colaborador getColaborador() {
    return colaborador;
  }

  public String getUrlFoto() {
    return urlFoto;
  }
}
