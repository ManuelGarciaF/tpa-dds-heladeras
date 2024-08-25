package ar.edu.utn.frba.dds.dominio.notificacionesHeladera;


import ar.edu.utn.frba.dds.exceptions.InvalidTelephoneNumberException;
import ar.edu.utn.frba.dds.externo.InstantMessageApp;
import ar.edu.utn.frba.dds.externo.InstantMessageSender;

//Esto podria ser un singleton? o un service locator
public class DespachadorDeMensajes implements DespachadorDeMensajeria{
  private InstantMessageSender instantMessageSender;

  public DespachadorDeMensajes(InstantMessageSender instantMessageSender) {
    this.instantMessageSender = instantMessageSender;
  }

  public void enviarMensaje(InstantMessageApp provider, String telephone, String message) throws InvalidTelephoneNumberException {
    //ver como esa persona va a recibir el mensaje, si en forma de noticacion mail, etc...
    if(!esUnNumeroDeTelefonoValido(telephone)) throw new InvalidTelephoneNumberException("El numero ingresado no es valido");
    instantMessageSender.sendMessage(provider, telephone, message);
  }

  //TODO revisar
  public boolean esUnNumeroDeTelefonoValido(String telephone){
    return telephone.matches("^[0-9]{10}$");
  }
}
