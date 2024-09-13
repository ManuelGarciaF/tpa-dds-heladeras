package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.dominio.Heladera;
import ar.edu.utn.frba.dds.dominio.MapaHeladeras;
import ar.edu.utn.frba.dds.dominio.Ubicacion;
import java.time.LocalDate;

public class Main {
  public static void main(String[] args) {
    // Cron:
    // */15 * * * * java -jar tpa.jar

    // Seria reemplazado por acceso a la DB
    Heladera unaHeladera = new Heladera(
        "UTN Campus",
        6,
        new Ubicacion(5.35, 2.76),
        "AA26U",
        LocalDate.now(),
        15.0,
        0.0,
        null,
        null,
        null,
        null,
        null
    );
    // MapaHeladeras.getInstance().agregarHeladera(unaHeladera);

    revisarSensores();
  }

  private static void revisarSensores() {
    // Checkear fallos de conexion
    MapaHeladeras.getInstance().revisarSensoresDeTemperatura();
  }
}