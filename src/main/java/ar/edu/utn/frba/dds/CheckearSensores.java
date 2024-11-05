package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.model.repositorios.MapaHeladeras;

public class CheckearSensores {
  // Cron: */15 * * * * java -jar tpa.jar
  public static void main(String[] args) {
    // Checkear fallos de conexion
    MapaHeladeras.getInstance().revisarSensoresDeTemperatura();
  }
}