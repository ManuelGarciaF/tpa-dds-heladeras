package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.dominio.Heladera;
import ar.edu.utn.frba.dds.dominio.MapaHeladeras;
import ar.edu.utn.frba.dds.dominio.Ubicacion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDate;

public class Main implements WithSimplePersistenceUnit {
  public static void main(String[] args) {
    // Main main = new Main();
    // main.agregarHeladera();

    // Cron:
    // */15 * * * * java -jar tpa.jar

    // Checkear fallos de conexion
    //MapaHeladeras.getInstance().revisarSensoresDeTemperatura();
  }

  public void agregarHeladera() {
    withTransaction(() -> {
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
      MapaHeladeras.getInstance().agregarHeladera(unaHeladera);
    });
  }
}