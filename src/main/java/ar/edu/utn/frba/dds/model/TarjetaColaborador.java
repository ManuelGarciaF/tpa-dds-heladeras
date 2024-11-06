package ar.edu.utn.frba.dds.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TarjetaColaborador {
  @Id
  private String codigoTarjeta;

  public TarjetaColaborador(String codigoTarjeta) {
    this.codigoTarjeta = codigoTarjeta;
  }

  public TarjetaColaborador() {
  }

  public String codigoTarjeta() {
    return codigoTarjeta;
  }
}