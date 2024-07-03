package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.dominio.Heladera;
import ar.edu.utn.frba.dds.dominio.MapaHeladeras;
import ar.edu.utn.frba.dds.dominio.Ubicacion;
import java.time.LocalDate;

public class Main {
  private static MapaHeladeras mapa;

  public static void main(String[] args) {

    if (args == null || args.length == 0) {
      System.out.println("Uso: java -jar <nombre-del-jar> <accion>");
      System.out.println("Accion: 'revisarSensores', 'generarReporte'");
      return;
    }

    // Cron:
    // */15 * * * * java -jar tpa.jar revisarSensores
    // * * * * MON  java -jar tpa.jar generarReporte

    // Seria reemplazado por acceso a la DB
    mapa = new MapaHeladeras();
    Heladera unaHeladera = new Heladera(
        "UTN Campus",
        6,
        new Ubicacion(5.35, 2.76),
        "AA26U",
        LocalDate.now(),
        null,
        null,
        null,
        null
    );
    mapa.agregarHeladera(unaHeladera);

    String accion = args[0];
    switch (accion) {
      case "revisarSensores":
        revisarSensores();
        break;
      case "generarReporte":
        generarReporte();
        break;
      default:
        System.out.println("Accion no reconocida");
    }
  }

  private static void revisarSensores() {
    // Checkear fallos de conexion
    mapa.revisarSensoresDeTemperatura();
  }

  private static void generarReporte() {
    // TODO
  }
}
