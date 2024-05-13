package ar.edu.utn.frba.dds;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ValidacionTop10k implements ValidacionContrasenia {
  @Override
  public Boolean validar(String contrasenia) {
    try {
      return Files.lines(Paths.get("./resources/10k-most-common.txt"))
          .noneMatch(line -> line.equals(contrasenia));
    } catch (IOException e) {
      throw new RuntimeException("Hubo un error al leer el archivo de contrasenias");
    }
  }
}
