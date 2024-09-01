package ar.edu.utn.frba.dds.dominio;

import static java.util.Objects.requireNonNull;

import ar.edu.utn.frba.dds.PersistentEntity;
import ar.edu.utn.frba.dds.exceptions.UsoTarjetaException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
public class PersonaVulnerable extends PersistentEntity {
  private String nombre;
  private LocalDate fechaNacimiento;
  private LocalDate fechaRegistro;
  private String domicilio;
  private Integer menoresAcargo;

  @Embedded
  private TarjetaPersonaVulnerable tarjeta;

  public PersonaVulnerable(String nombre,
                           String domicilio,
                           LocalDate fechaNacimiento,
                           LocalDate fechaRegistro,
                           Integer menoresAcargo,
                           TarjetaPersonaVulnerable tarjetaPersonaVulnerable) {
    this.nombre = requireNonNull(nombre);
    this.domicilio = domicilio;
    this.fechaNacimiento = requireNonNull(fechaNacimiento);
    this.fechaRegistro = requireNonNull(fechaRegistro);
    this.menoresAcargo = requireNonNull(menoresAcargo);
    this.tarjeta = requireNonNull(tarjetaPersonaVulnerable);
  }

  public PersonaVulnerable() {
  }

  public Integer usosMaximosDiarios() {
    return 4 + menoresAcargo * 2;
  }

  public Boolean tieneUsosDisponibles() {
    return tarjeta.usosHoy() < usosMaximosDiarios();
  }

  public Integer mesesActivos() {
    return (int) ChronoUnit.MONTHS.between(fechaRegistro, LocalDate.now());
  }

  public Integer usosTarjeta() {
    return tarjeta.cantidadUsos();
  }

  public void agregarUsoTarjeta(Heladera heladera) {
    if (!tieneUsosDisponibles()) {
      throw new UsoTarjetaException("No tiene usos disponibles");
    }
    heladera.agregarUso(new UsoTarjetaPersonaVulnerable(tarjeta, LocalDate.now()));
  }

  public String getDomicilio() {
    return domicilio;
  }

  public String getNombre() {
    return nombre;
  }

  public LocalDate getFechaNacimiento() {
    return fechaNacimiento;
  }

  public LocalDate getFechaRegistro() {
    return fechaRegistro;
  }

  public Integer getMenoresAcargo() {
    return menoresAcargo;
  }

  public TarjetaPersonaVulnerable getTarjeta() {
    return tarjeta;
  }

}
