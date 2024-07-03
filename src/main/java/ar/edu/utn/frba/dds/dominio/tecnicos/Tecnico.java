package ar.edu.utn.frba.dds.dominio.tecnicos;

import ar.edu.utn.frba.dds.dominio.Ubicacion;
import java.util.ArrayList;
import java.util.List;

public class Tecnico {
  private final String nombre;
  private final Ubicacion ubicacion;
  private final List<Visita> visitas = new ArrayList<>();

  public Tecnico(String nombre, Ubicacion ubicacion) {
    this.nombre = nombre;
    this.ubicacion = ubicacion;
  }

  public void registrarVisita(Visita visita) {
    this.visitas.add(visita);
  }

}
