package ar.edu.utn.frba.dds;

import com.unicodelabs.jdp.core.DumbPassword;
import com.unicodelabs.jdp.core.exceptions.IsNullException;
import java.io.IOException;
import java.time.LocalDate;

public class Usuario {
  private String nombre;
  private String contrasenia;

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

  private Boolean verificarContrasenia(String contrasenia) {
    return this.contrasenia.equals(contrasenia);
  }

  private static boolean esSegura(String contrasenia) {
    // Checkear contra las 10000 contraseñas más comunes
    final DumbPassword dumbPasswords = new DumbPassword();
    // Necesitamos usar un try-catch para convertir la excepcion checkeada en no-checkeada.
    try {
      if (dumbPasswords.checkPassword(contrasenia)) {
        return false;
      }
    } catch (IOException ex) {
      throw new RuntimeException("No se pudo verificar la contraseña");
    } catch (IsNullException ex) {
      throw new ContraseniaInseguraException("La contraseña no puede ser nula");
    }

    // Checkear que tenga al menos 8 caracteres
    return contrasenia.length() >= 8;
  }
}
