package ar.edu.utn.frba.dds.exceptions;

public class CsvInvalidoException extends RuntimeException {
  public CsvInvalidoException(String message, Throwable cause) {
    super(message, cause);
  }

  public CsvInvalidoException(String message) {
    super(message);
  }
}
