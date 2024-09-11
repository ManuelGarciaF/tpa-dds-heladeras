package ar.edu.utn.frba.dds.dominio;

import static java.util.Objects.requireNonNull;

import ar.edu.utn.frba.dds.PersistentEntity;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Embeddable;

@Embeddable
public class TarjetaPersonaVulnerable {

  private String codigoTarjeta;

  public TarjetaPersonaVulnerable(String codigoTarjeta) {
    this.codigoTarjeta = requireNonNull(codigoTarjeta);
  }

  public TarjetaPersonaVulnerable() {
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

  private List<UsoTarjetaPersonaVulnerable> usosTarjeta() {
    return MapaHeladeras.getInstance().encontrarUsosDeTarjeta(codigoTarjeta);
  }
}
