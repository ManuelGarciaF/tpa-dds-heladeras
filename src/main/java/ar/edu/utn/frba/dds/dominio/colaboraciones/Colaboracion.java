package ar.edu.utn.frba.dds.dominio.colaboraciones;

import ar.edu.utn.frba.dds.PersistentEntity;
import ar.edu.utn.frba.dds.dominio.Colaborador;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Colaboracion extends PersistentEntity {
  public abstract Double puntaje();

  public abstract boolean puedeSerRealizadaPor(Colaborador colaborador);
}
