package ar.edu.utn.frba.dds.model;

import ar.edu.utn.frba.dds.PersistentEntity;
import ar.edu.utn.frba.dds.exceptions.ContraseniaInseguraException;
import ar.edu.utn.frba.dds.model.validaciones.ValidacionContrasenia;
import ar.edu.utn.frba.dds.model.validaciones.ValidacionContraseniasComunes;
import ar.edu.utn.frba.dds.model.validaciones.ValidacionLongitud;
import java.util.List;
import javax.persistence.Entity;

@Entity
public class Usuario extends PersistentEntity {
  private static final List<ValidacionContrasenia> validaciones = List.of(
      new ValidacionLongitud(8),
      new ValidacionContraseniasComunes("10k-most-common.txt"));

  private String contrasenia; // Texto plano :)
  // TODO cambiar por hash

  private String nombre;

  public Usuario(String contrasenia, String nombre) {
    this.nombre = nombre;
    if (!Usuario.esSegura(contrasenia)) {
      throw new ContraseniaInseguraException("La contraseña no es segura");
    }
    this.contrasenia = contrasenia;
  }

  public Usuario() {
  }

  private static boolean esSegura(String contrasenia) {
    return validaciones.stream().allMatch(validacion -> validacion.validar(contrasenia));
  }

  // TODO: cambiar por hash
  public Boolean verificarContrasenia(String contrasenia) {
    return this.contrasenia.equals(contrasenia);
  }

  public String getNombre() {
    return nombre;
  }
}
