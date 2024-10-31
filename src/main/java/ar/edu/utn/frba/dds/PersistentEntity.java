package ar.edu.utn.frba.dds;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class PersistentEntity {
  @Id
  @GeneratedValue
  private Long id;

  public Long getId() {
    return id;
  }
}
