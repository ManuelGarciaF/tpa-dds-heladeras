package ar.edu.utn.frba.dds.model.sensoresheladera;

import ar.edu.utn.frba.dds.PersistentEntity;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class ProveedorCantidadDeViandas extends PersistentEntity {
  public abstract Integer getCantidadDeViandas();

  public abstract void setNuevaMedicionHandler(Runnable nuevaMedicionHandler);
}
