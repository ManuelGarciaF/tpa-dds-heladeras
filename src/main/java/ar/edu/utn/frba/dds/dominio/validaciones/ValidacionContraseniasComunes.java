package ar.edu.utn.frba.dds.dominio.validaciones;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ValidacionContraseniasComunes implements ValidacionContrasenia {
  private BufferedReader readerArchivo;

  public ValidacionContraseniasComunes(String nombreRecurso) {
    InputStream inputStream = getClass()
        .getClassLoader()
        .getResourceAsStream(nombreRecurso);
    if (inputStream == null) {
      throw new RuntimeException("No se pudo abrir el archivo de contrasenias");
    }
    readerArchivo = new BufferedReader(new InputStreamReader(inputStream));
  }

  @Override
  public Boolean validar(String contrasenia) {
    return readerArchivo.lines()
        .noneMatch(line -> line.equals(contrasenia));
  }


}
