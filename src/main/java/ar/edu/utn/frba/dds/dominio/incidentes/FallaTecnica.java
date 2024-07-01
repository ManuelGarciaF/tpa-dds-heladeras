package ar.edu.utn.frba.dds.dominio.incidentes;

import ar.edu.utn.frba.dds.dominio.Colaborador;
import ar.edu.utn.frba.dds.dominio.Heladera;
import java.time.OffsetDateTime;

public class FallaTecnica implements Incidente {
  private Colaborador colaborador;
  private OffsetDateTime fecha;
  private Heladera heladera;
  private String descripcion;
  private String urlFoto;


  @Override
  public OffsetDateTime getFecha() {
    return fecha;
  }

  @Override
  public Heladera getHeladera() {
    return heladera;
  }

  public Colaborador getColaborador() {
    return colaborador;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public String getUrlFoto() {
    return urlFoto;
  }



  //CONSTRUCTOR
  public FallaTecnica(Colaborador colaborador, Heladera heladera, OffsetDateTime fecha, String descripcion, String urlFoto) {
    this.colaborador = colaborador;
    this.heladera = heladera;
    this.fecha = fecha;
    this.descripcion = descripcion;
    this.urlFoto = urlFoto;
  }
}
