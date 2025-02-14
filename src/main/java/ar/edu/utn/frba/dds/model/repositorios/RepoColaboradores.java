package ar.edu.utn.frba.dds.model.repositorios;

import ar.edu.utn.frba.dds.cargamasiva.FilaCsv;
import ar.edu.utn.frba.dds.model.Colaborador;
import ar.edu.utn.frba.dds.model.ColaboradorHumano;
import ar.edu.utn.frba.dds.model.MedioDeContacto;
import ar.edu.utn.frba.dds.model.TipoDocumento;
import ar.edu.utn.frba.dds.model.colaboraciones.Colaboracion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Set;
import javax.persistence.NoResultException;

public class RepoColaboradores implements WithSimplePersistenceUnit {

  private static final RepoColaboradores instance = new RepoColaboradores();

  public static RepoColaboradores getInstance() {
    return instance;
  }

  public List<Colaborador> getColaboradores() {
    return entityManager().createQuery("from Colaborador", Colaborador.class).getResultList();
  }

  public void agregarColaborador(Colaborador colaborador) {
    entityManager().persist(colaborador);
  }

  public void cargarDeCsv(String path) {
    List<FilaCsv> filas = FilaCsv.fromCsv(path);

    filas.forEach(fila -> {
      Colaborador colaborador = findOrCreateColaboradorHumano(fila);
      Colaboracion colaboracion = fila.crearColaboracion();
      colaborador.colaborar(colaboracion);
    });

  }

  private Colaborador findOrCreateColaboradorHumano(FilaCsv fila) {
    try {
      return buscarColaboradorHumano(fila.tipoDocumento(), fila.numeroDocumento());
    } catch (NoResultException e) {
      // Si el colaborador no existe, crearlo y agregarlo
      Colaborador colaborador = new ColaboradorHumano(fila.nombre(),
          fila.apellido(),
          null,
          null,
          new MedioDeContacto(null, fila.mail(), null),
          null,
          Set.of(),
          fila.tipoDocumento(),
          fila.numeroDocumento(),
          null); // Quizas tendria sentido crear un proveedor de
      // mensajeria para el colaborador nuevo.
      agregarColaborador(colaborador);
      return colaborador;
    }
  }

  private Colaborador buscarColaboradorHumano(TipoDocumento tipoDocumento, Integer documento) {
    return entityManager().createQuery(
            "from ColaboradorHumano where "
                + "tipoDocumento = :tipoDocumento and numeroDocumento = :documento",
            ColaboradorHumano.class)
        .setParameter("tipoDocumento", tipoDocumento)
        .setParameter("documento", documento)
        .getSingleResult();
  }

  public Colaborador buscarPorIdUsuario(Long id) {
    try {
      return entityManager().createQuery(
              "SELECT c FROM Colaborador c JOIN c.usuario u ON u.id = :id",
              Colaborador.class)
          .setParameter("id", id)
          .getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }
}