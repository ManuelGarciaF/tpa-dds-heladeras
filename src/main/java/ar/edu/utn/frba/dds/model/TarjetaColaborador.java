package ar.edu.utn.frba.dds.model;

import ar.edu.utn.frba.dds.PersistentEntity;
import javax.persistence.Entity;

@Entity
public class TarjetaColaborador extends PersistentEntity {
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
