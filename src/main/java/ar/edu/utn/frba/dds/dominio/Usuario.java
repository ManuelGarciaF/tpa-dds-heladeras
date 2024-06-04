package ar.edu.utn.frba.dds.dominio;

import ar.edu.utn.frba.dds.dominio.validaciones.ValidacionContrasenia;
import ar.edu.utn.frba.dds.dominio.validaciones.ValidacionContraseniasComunes;
import ar.edu.utn.frba.dds.dominio.validaciones.ValidacionLongitud;
import ar.edu.utn.frba.dds.exceptions.ContraseniaInseguraException;
import java.util.List;

public class Usuario {
  private static final List<ValidacionContrasenia> validaciones = List.of(
      new ValidacionLongitud(8),
      new ValidacionContraseniasComunes("10k-most-common.txt"));
  private final String nombre;
  private final String contrasenia;

  public Usuario(String contrasenia, String nombre) {
    this.nombre = nombre;
    if (!Usuario.esSegura(contrasenia)) {
      throw new ContraseniaInseguraException("La contraseña no es segura");
    }
    this.contrasenia = contrasenia;
  }

  private static boolean esSegura(String contrasenia) {
    return validaciones.stream().allMatch(validacion -> validacion.validar(contrasenia));
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
}
