package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.cargamasiva.FilaCsv;
import ar.edu.utn.frba.dds.colaboraciones.Colaboracion;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class ListaColaboradores {
  private final List<Colaborador> colaboradores;
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
    List<FilaCsv> filas = filasColaboracionFromCsv(path);

    filas.forEach(fila -> {
      Colaborador colaborador = obtenerCrearColaborador(fila);
      Colaboracion colaboracion = fila.crearColaboracion();
      colaborador.colaborar(colaboracion);
    });

  }

  private List<FilaCsv> filasColaboracionFromCsv(String path) {
    List<FilaCsv> filas = new ArrayList<>();
    try {
      Stream<String> stream = Files.lines(Paths.get(path));
      stream.forEach(linea -> {
        FilaCsv fila = FilaCsv.fromString(linea);
        filas.add(fila);
      });
      stream.close();
    } catch (IOException e) {
      throw new RuntimeException("Hubo un error al leer el archivo de colaboraciones: " + path, e);
    }
    return filas;
  }

  private Colaborador obtenerCrearColaborador(FilaCsv fila) {
    Colaborador colaborador = buscarColaborador(fila.tipoDocumento(), fila.numeroDocumento());
    // Si el colaborador no existe, crearlo y agregarlo a la lista
    if (colaborador == null) {
      colaborador = new PersonaHumana(fila.nombre(),
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