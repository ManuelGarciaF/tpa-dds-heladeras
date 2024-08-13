package ar.edu.utn.frba.dds.dominio.incidentes;

import ar.edu.utn.frba.dds.dominio.Colaborador;
import ar.edu.utn.frba.dds.dominio.Heladera;
import ar.edu.utn.frba.dds.dominio.Ubicacion;
import java.time.LocalDateTime;


//TODO:podemos hacer que un usuario carge una heladera, y el incidente se añada a la lista de esta
public class FallaTecnica implements Incidente {
  private Colaborador colaborador;
  private LocalDateTime fecha;
  private Heladera heladeraDefectuosa; // TODO: revisar y preguntar porque no se cargaria una heladera aca

  private String descripcion;
  private String urlFoto;
  private Ubicacion ubicacionDelIncidente;
  //se va a crear cuando el colaborador reporte un incidente
  public FallaTecnica(Colaborador colaborador,
                      LocalDateTime fecha,
                      Heladera heladeraDefectuosa,
                      String descripcion,
                      String urlFoto) {
    this.colaborador = colaborador;
    this.fecha = fecha;
    this.descripcion = descripcion;
    this.urlFoto = urlFoto;
    this.ubicacionDelIncidente = heladeraDefectuosa.getUbicacion();
  }

  public Ubicacion getUbicacionDelIncidente() {
    return ubicacionDelIncidente;
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
