package ar.edu.utn.frba.dds.dominio;

public class MedioDeContacto {
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

  public String getWhatsapp() {
    return whatsapp;
  }

  public String getCorreoElectronico() {
    return correoElectronico;
  }

  public String getTelefono() {
    return telefono;
  }
}
