package ar.edu.utn.frba.dds.dominio.validaciones;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ValidacionTop10k implements ValidacionContrasenia {
  @Override
  public Boolean validar(String contrasenia) {
    InputStream inputStream = getClass()
        .getClassLoader()
        .getResourceAsStream("10k-most-common.txt");
    if (inputStream == null) {
      throw new RuntimeException("No se pudo abrir el archivo de contrasenias");
    }
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    return reader.lines()
        .noneMatch(line -> line.equals(contrasenia));
  }
}
