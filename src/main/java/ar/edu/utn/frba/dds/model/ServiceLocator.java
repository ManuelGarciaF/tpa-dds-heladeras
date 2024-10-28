package ar.edu.utn.frba.dds.model;

import ar.edu.utn.frba.dds.externo.ControladorDeAcceso;
import ar.edu.utn.frba.dds.externo.ControladorDeAccesoImpl;
import ar.edu.utn.frba.dds.externo.InstantMessageSender;
import ar.edu.utn.frba.dds.externo.InstantMessageSenderImpl;
import ar.edu.utn.frba.dds.externo.LSensor;
import ar.edu.utn.frba.dds.externo.LSensorImpl;
import ar.edu.utn.frba.dds.externo.TSensor;
import ar.edu.utn.frba.dds.externo.TSensorImpl;
import ar.edu.utn.frba.dds.externo.WSensor;
import ar.edu.utn.frba.dds.externo.WSensorImpl;

public class ServiceLocator {
  public static TSensor getTSensor() {
    return new TSensorImpl(); // TODO: Esto es una clase vacia.
  }

  public static WSensor getWSensor() {
    return new WSensorImpl(); // TODO: Esto es una clase vacia.
  }

  public static LSensor getLSensor() {
    return new LSensorImpl(); // TODO: Esto es una clase vacia.
  }

  public static ControladorDeAcceso getControladorDeAcceso(String numeroDeSerie) {
    // Se usaria el numeroDeSerie para saber que controlador devolver.
    return new ControladorDeAccesoImpl(); // TODO: Esto es una clase vacia.
  }

  public static InstantMessageSender getInstantMessageSender() {
    return new InstantMessageSenderImpl(); // TODO: Esto es una clase vacia.
  }
}
