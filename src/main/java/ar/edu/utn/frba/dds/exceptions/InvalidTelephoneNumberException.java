package ar.edu.utn.frba.dds.exceptions;

public class InvalidTelephoneNumberException extends RuntimeException {
  public InvalidTelephoneNumberException(String message) {
    super(message);
  }
}
