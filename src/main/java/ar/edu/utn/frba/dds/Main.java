package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.dominio.Heladera;
import ar.edu.utn.frba.dds.dominio.MapaHeladeras;
import ar.edu.utn.frba.dds.dominio.Ubicacion;
import ar.edu.utn.frba.dds.dominio.notificacionesheladera.NotificacionHeladeraHandler;
import java.time.LocalDate;

public class Main {
  public static void main(String[] args) {
    // Cron:
    // */15 * * * * java -jar tpa.jar

    var unaHeladera = new Heladera("heladera2",
        39,
        new Ubicacion(0.2, 0.0),
        "kd393j",
        LocalDate.now(),
        15.0,
        0.0,
        null,
        null,
        null,
        null,
        new NotificacionHeladeraHandler());
//    MapaHeladeras.getInstance().agregarHeladera(unaHeladera);
    System.out.println(MapaHeladeras.getInstance().listarHeladeras().size());
  }

  private static void revisarSensores() {
  }
}
