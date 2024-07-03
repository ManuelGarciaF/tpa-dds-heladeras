package ar.edu.utn.frba.dds.dominio;

public class Tecnico {
  private String nombre;
  private Ubicacion ubicacion;
  private List<Visita> visitas;

  public void registrarVisita(Visita visita) {
    this.visitas.add(visita);
  }

}
