package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.model.repositorios.MapaHeladeras;

public class CheckearSensores {
  // Cron: */15 * * * * java -jar tpa.jar
  public static void main(String[] args) {
    // Checkear fallos de conexion
    System.out.println("Revisando fallas de conexion...");
    MapaHeladeras.getInstance().revisarSensoresDeTemperatura();
    System.out.println("Se revisaron las fallas de conexion.");
  }
}