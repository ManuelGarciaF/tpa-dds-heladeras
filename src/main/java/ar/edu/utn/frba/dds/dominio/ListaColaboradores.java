package ar.edu.utn.frba.dds.dominio;

import ar.edu.utn.frba.dds.cargamasiva.FilaCsv;
import ar.edu.utn.frba.dds.dominio.colaboraciones.Colaboracion;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ListaColaboradores {
  private final List<Colaborador> colaboradores;

  public ListaColaboradores() {
    this.colaboradores = new ArrayList<>();
  }

  public List<Colaborador> getColaboradores() {
    return colaboradores;
  }

  public void agregarColaborador(Colaborador colaborador) {
    this.colaboradores.add(colaborador);
  }

  public void cargarDeCsv(String path) {
    List<FilaCsv> filas = FilaCsv.fromCsv(path);

    filas.forEach(fila -> {
      Colaborador colaborador = obtenerCrearColaborador(fila);
      Colaboracion colaboracion = fila.crearColaboracion();
      colaborador.colaborar(colaboracion);
    });

  }

  private Colaborador obtenerCrearColaborador(FilaCsv fila) {
    Colaborador colaborador = buscarColaborador(fila.tipoDocumento(), fila.numeroDocumento());
    // Si el colaborador no existe, crearlo y agregarlo a la lista
    if (colaborador == null) {
      colaborador = new ColaboradorHumano(fila.nombre(),
          fila.apellido(),
          null,
          null,
          new MedioDeContacto(null, fila.mail(), null),
          Set.of(),
          fila.tipoDocumento(),
          fila.numeroDocumento());
      agregarColaborador(colaborador);
    }
    return colaborador;
  }

  private Colaborador buscarColaborador(TipoDocumento tipoDocumento, Integer documento) {
    return colaboradores.stream()
        .filter(c -> c.esDeDocumento(tipoDocumento, documento))
        .findFirst()
        .orElse(null);
  }
}