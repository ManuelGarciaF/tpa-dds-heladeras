package ar.edu.utn.frba.dds.externo;

public class InvalidTelephoneNumberException extends RuntimeException {
  public InvalidTelephoneNumberException(String message) {
    super(message);
  }
}
