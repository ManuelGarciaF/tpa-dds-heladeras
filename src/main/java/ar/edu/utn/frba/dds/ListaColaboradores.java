package ar.edu.utn.frba.dds;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
    try {
      Stream<String> stream = Files.lines(Paths.get(path));
      stream.forEach(this::parsearColaboracionCsv);
      stream.close();
    } catch (IOException e) {
      throw new RuntimeException("Hubo un error al leer el archivo de contrasenias");
    }
  }

  private void parsearColaboracionCsv(String line) {
    String[] campos = parsearCampos(line);

    TipoDocumento tipoDocumento = parsearTipoDocumento(campos[0]);
    Integer documento = Integer.parseInt(campos[1]);

    Colaborador colaborador = buscarColaborador(tipoDocumento, documento);
    if (colaborador == null) {
      String nombre = campos[2];
      String apellido = campos[3];
      String mail = campos[4];
      colaborador = new PersonaHumana(nombre,
          apellido,
          null,
          null,
          null,
          null,
          tipoDocumento,
          documento);
    }

    LocalDate fechaColaboracion = LocalDate.parse(
        campos[5],
        DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    Integer cantidad = Integer.parseInt(campos[7]);

    crearColaboracionSegunStringCsv(colaborador, campos[6], cantidad, fechaColaboracion);
  }

  private void crearColaboracionSegunStringCsv(Colaborador colaborador,
                                               String tipo,
                                               Integer cantidad,
                                               LocalDate fecha) {
    switch (tipo) {
      case "DINERO":
        colaborador.colaborar(new DonacionDeDinero(cantidad, false, null));
        break;
      case "DONACION_VIANDAS":
        colaborador.colaborar(new DonacionDeViandaHistorica(cantidad));
        break;
      case "REDISTRIBUCION_VIANDAS":
        colaborador.colaborar(new DistribucionDeViandas(
            MotivoDeDistribucion.NO_ESPECIFICADO,
            fecha,
            cantidad,
            null,
            null));
        break;
      case "ENTREGA_TARJETAS":
        colaborador.colaborar(new RegistroDePersonaVulnerableHistorico(cantidad));
        break;
      default:
        throw new CsvInvalidoException("El archivo de colaboraciones tiene un formato incorrecto");
    }
  }

  private Colaborador buscarColaborador(TipoDocumento tipoDocumento, Integer documento) {
    return colaboradores.stream()
        .filter(c -> c.esDeDocumento(tipoDocumento, documento))
        .findFirst()
        .orElse(null);
  }

  private String[] parsearCampos(String line) {
    String[] campos = line.split(",");
    if (campos.length != 8) {
      throw new CsvInvalidoException("El archivo de colaboraciones tiene un formato incorrecto");
    }
    return campos;
  }

  private TipoDocumento parsearTipoDocumento(String tipo) {
    switch (tipo) {
      case "LC":
        return TipoDocumento.LC;
      case "LE":
        return TipoDocumento.LE;
      case "DNI":
        return TipoDocumento.DNI;
      default:
        throw new CsvInvalidoException("El archivo de colaboraciones tiene un formato incorrecto");
    }

  }
}