package ar.edu.utn.frba.dds.dominio;

import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public final class TarjetaColaborador {
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
