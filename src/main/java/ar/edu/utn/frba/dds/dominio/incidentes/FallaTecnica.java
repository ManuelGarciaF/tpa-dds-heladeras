package ar.edu.utn.frba.dds.dominio.incidentes;

import ar.edu.utn.frba.dds.dominio.Colaborador;
import ar.edu.utn.frba.dds.dominio.Heladera;
import ar.edu.utn.frba.dds.dominio.Ubicacion;
import java.time.LocalDateTime;



public class FallaTecnica implements Incidente {
  private Colaborador colaborador;
  private LocalDateTime fecha;
  private Heladera heladeraDefectuosa;
  private String descripcionDelError;
  private String urlFoto;
  private Ubicacion ubicacionDelIncidente;

  //se va a crear cuando un colaborador reporte un incidente
  public FallaTecnica(Colaborador colaborador,
                      LocalDateTime fecha,
                      Heladera heladeraDefectuosa,
                      String descripcionDelError,
                      String urlFoto) {
    this.colaborador = colaborador;
    this.fecha = fecha;
    this.descripcionDelError = descripcionDelError;
    this.urlFoto = urlFoto;
    this.heladeraDefectuosa = heladeraDefectuosa;
    this.ubicacionDelIncidente = heladeraDefectuosa.getUbicacion();
  }

  public Ubicacion getUbicacionDelIncidente() {
    return ubicacionDelIncidente;
  }

  @Override
  public LocalDateTime getFecha() {
    return fecha;
  }

  public Heladera getHeladeraDefectuosa() {
    return heladeraDefectuosa;
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
