package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.model.notificacionesheladera.ProveedorMensajeriaMail;

public class MailTest {
  public static void main(String[] args) {
    var proveedorMail = new ProveedorMensajeriaMail("mgarciafrigo@frba.utn.edu.ar");
    proveedorMail.enviarMensaje("prueba");
  }
}
