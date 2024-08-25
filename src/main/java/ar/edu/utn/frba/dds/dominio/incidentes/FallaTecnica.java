package ar.edu.utn.frba.dds.dominio.incidentes;

import ar.edu.utn.frba.dds.dominio.Colaborador;
import java.time.LocalDateTime;

public class FallaTecnica extends Incidente {
  private Colaborador colaborador;
  private String descripcionDelError;
  private String urlFoto;

  public FallaTecnica(Colaborador colaborador,
                      LocalDateTime fecha,
                      String descripcionDelError,
                      String urlFoto) {
    super(fecha);
    this.colaborador = colaborador;
    this.descripcionDelError = descripcionDelError;
    this.urlFoto = urlFoto;
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
