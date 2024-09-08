package ar.edu.utn.frba.dds.dominio;

import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public final class MedioDeContacto {
  private String telefono;
  private String correoElectronico;
  private String whatsapp;

  public MedioDeContacto(String telefono, String correoElectronico, String whatsapp) {
    if (telefono == null && correoElectronico == null && whatsapp == null) {
      throw new IllegalArgumentException("Al menos un medio de contacto debe ser no nulo");
    }
    this.telefono = telefono;
    this.correoElectronico = correoElectronico;
    this.whatsapp = whatsapp;
  }

  public MedioDeContacto() {
  }

  public String telefono() {
    return telefono;
  }

  public String correoElectronico() {
    return correoElectronico;
  }

  public String whatsapp() {
    return whatsapp;
  }
}
