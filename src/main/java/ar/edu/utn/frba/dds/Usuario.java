package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.exceptions.ContraseniaInseguraException;
import ar.edu.utn.frba.dds.validaciones.ValidacionContrasenia;
import ar.edu.utn.frba.dds.validaciones.ValidacionLongitud;
import ar.edu.utn.frba.dds.validaciones.ValidacionTop10k;
import java.util.List;

public class Usuario {
  private String nombre;
  private String contrasenia;
  private static final List<ValidacionContrasenia> validaciones = List.of(
      new ValidacionLongitud(8),
      new ValidacionTop10k()
  );

  public Usuario(String contrasenia, String nombre) {
    this.nombre = nombre;
    if (!Usuario.esSegura(contrasenia)) {
      throw new ContraseniaInseguraException("La contraseña no es segura");
    }
    this.contrasenia = contrasenia;
  }

  public String getNombre() {
    return nombre;
  }

  public String getContrasenia() {
    return contrasenia;
  }

  public Boolean verificarContrasenia(String contrasenia) {
    return this.contrasenia.equals(contrasenia);
  }

  private static boolean esSegura(String contrasenia) {
    return validaciones.stream().allMatch(validacion -> validacion.validar(contrasenia));
  }
}
