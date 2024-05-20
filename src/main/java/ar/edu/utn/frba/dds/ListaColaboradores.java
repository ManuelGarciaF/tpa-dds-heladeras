package ar.edu.utn.frba.dds;

import java.util.ArrayList;
import java.util.List;
import jdk.jshell.spi.ExecutionControl;

public class ListaColaboradores {
  private List<Colaborador> colaboradores;
  private static final ListaColaboradores INSTANCE = new ListaColaboradores();

  public static ListaColaboradores instance() {
    return INSTANCE;
  }

  private ListaColaboradores() {
    this.colaboradores = new ArrayList<>();
  }

  public List<Colaborador> getColaboradores() {
    return colaboradores;
  }

  public void agregarColaborador(Colaborador colaborador) {
    this.colaboradores.add(colaborador);
  }

  public void cargarDeCsv(String path) {
    // TODO
    throw new RuntimeException("No implementado");
  }
}