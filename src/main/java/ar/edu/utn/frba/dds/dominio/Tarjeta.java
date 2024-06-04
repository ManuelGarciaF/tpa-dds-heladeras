package ar.edu.utn.frba.dds.dominio;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;

public class Tarjeta {
  private final String codigoTarjeta;
  private final MapaHeladeras mapaHeladeras;

  public Tarjeta(String codigoTarjeta, MapaHeladeras mapaHeladeras) {
    this.codigoTarjeta = requireNonNull(codigoTarjeta);
    this.mapaHeladeras = requireNonNull(mapaHeladeras);
  }

  public boolean esDeCodigo(String codigoTarjeta) {
    return this.codigoTarjeta.equals(codigoTarjeta);
  }

  public Integer usosHoy() {
    return (int) usosTarjeta().stream().filter(u -> u.fecha().equals(LocalDate.now())).count();
  }

  public Integer cantidadUsos() {
    return usosTarjeta().size();
  }

  private List<UsoTarjeta> usosTarjeta() {
    return mapaHeladeras.encontrarUsosDeTarjeta(codigoTarjeta);
  }
}
