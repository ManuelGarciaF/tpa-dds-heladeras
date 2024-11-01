package ar.edu.utn.frba.dds.model.repositorios;

import ar.edu.utn.frba.dds.model.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public class RepoUsuarios implements WithSimplePersistenceUnit {

  private static final RepoUsuarios instance = new RepoUsuarios();

  public static RepoUsuarios getInstance() {
    return instance;
  }

  public List<Usuario> getUsuarios() {
    return entityManager().createQuery("from Usuario ", Usuario.class).getResultList();
  }

  public void agregarUsuario(Usuario usuario) {
    entityManager().persist(usuario);
  }

  public Usuario findById(Long id) {
    return entityManager().find(Usuario.class, id);
  }

  public Usuario findByUsername(String username) {
    return entityManager().createQuery("from Usuario where nombre = :username", Usuario.class)
        .setParameter("username", username)
        .getResultStream()
        .findFirst()
        .orElse(null);
  }
}
