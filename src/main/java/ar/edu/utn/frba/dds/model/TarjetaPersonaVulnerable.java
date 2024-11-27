package ar.edu.utn.frba.dds.model;

import ar.edu.utn.frba.dds.PersistentEntity;
import ar.edu.utn.frba.dds.model.repositorios.MapaHeladeras;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

@Entity
public class TarjetaPersonaVulnerable extends PersistentEntity {
  private String codigoTarjeta;

  @Transient
  private MapaHeladeras mapaHeladeras;

  public TarjetaPersonaVulnerable(String codigoTarjeta, MapaHeladeras mapaHeladeras) {
    this.codigoTarjeta = codigoTarjeta;
    this.mapaHeladeras = mapaHeladeras;
  }

  public TarjetaPersonaVulnerable() {
  }

  @PostLoad
  public void postLoad() {
    this.mapaHeladeras = MapaHeladeras.getInstance();
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
    return mapaHeladeras.encontrarUsosDeTarjeta(codigoTarjeta);
  }
}
