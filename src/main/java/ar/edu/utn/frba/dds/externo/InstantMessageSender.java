package ar.edu.utn.frba.dds.externo;

public interface InstantMessageSender {
  void sendMessage(
      InstantMessageApp provider,
      String telephone,
      String message
  ) throws InvalidTelephoneNumberException;
}
