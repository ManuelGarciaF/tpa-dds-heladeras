package ar.edu.utn.frba.dds.dominio;

import javax.persistence.Embeddable;

@Embeddable
public record MedioDeContacto(String telefono, String correoElectronico, String whatsapp) {
  public MedioDeContacto {
    if (telefono == null && correoElectronico == null && whatsapp == null) {
      throw new IllegalArgumentException("Al menos un medio de contacto debe ser no nulo");
    }
  }
}
