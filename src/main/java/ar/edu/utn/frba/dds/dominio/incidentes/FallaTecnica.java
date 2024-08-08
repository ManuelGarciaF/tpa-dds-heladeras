package ar.edu.utn.frba.dds.dominio.incidentes;

import ar.edu.utn.frba.dds.dominio.Colaborador;
import ar.edu.utn.frba.dds.dominio.Heladera;
import java.time.LocalDateTime;

public class FallaTecnica implements Incidente {
  private Colaborador colaborador;
  private LocalDateTime fecha;
  private Heladera heladera; // TODO: revisar y preguntar porque no se cargaria una heladera aca
  private String descripcion;
  private String urlFoto;
  //se va a crear cuando el colaborador reporte un incidente
  public FallaTecnica(Colaborador colaborador,
                      LocalDateTime fecha,
                      String descripcion,
                      String urlFoto) {
    this.colaborador = colaborador;
    this.fecha = fecha;
    this.descripcion = descripcion;
    this.urlFoto = urlFoto;
  }

  @Override
  public LocalDateTime getFecha() {
    return fecha;
  }

  @Override
  public String getDescripcion() {
    return descripcion;
  }

  public Colaborador getColaborador() {
    return colaborador;
  }

  public String getUrlFoto() {
    return urlFoto;
  }
}
