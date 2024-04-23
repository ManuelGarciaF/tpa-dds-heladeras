package ar.edu.utn.frba.dds;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    if (estaEnTop10k(contrasenia)) {
      return false;
    }

    // Checkear que tenga al menos 8 caracteres
    return contrasenia.length() >= 8;
  }

  private static boolean estaEnTop10k(String contrasenia) {
    try {
      return Files.lines(Paths.get("./resources/10k-most-common.txt"))
          .anyMatch(line -> line.equals(contrasenia));
    } catch (IOException e) {
      throw new RuntimeException("Hubo un error al leer el archivo de contrasenias");
    }
  }
}
