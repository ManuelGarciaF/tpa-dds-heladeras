package ar.edu.utn.frba.dds.dominio;

public class TarjetaColaborador {
  private final String codigoTarjeta;

  public TarjetaColaborador(String codigoTarjeta) {
    this.codigoTarjeta = codigoTarjeta;
  }

  public boolean esDeCodigo(String codigoTarjeta) {
    return this.codigoTarjeta.equals(codigoTarjeta);
  }
}
