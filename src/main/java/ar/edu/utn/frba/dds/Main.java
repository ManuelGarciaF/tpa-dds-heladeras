package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.dominio.*;
import java.time.LocalDate;

public class Main {
  public static void main(String[] args) {
    MapaHeladeras mapa = new MapaHeladeras();

    Heladera unaHeladera = new Heladera(
        "UTN Campus",
        6,
        new Ubicacion(5.35,2.76),
        "AA26U",
        23,
        17,
        null,
        null,
        LocalDate.now()
    );

    mapa.agregarHeladera(unaHeladera);
    mapa.revisarSensoresDeTemperatura();


  }
}
