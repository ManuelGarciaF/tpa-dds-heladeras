package ar.edu.utn.frba.dds.dominio.sensoresheladera;

import ar.edu.utn.frba.dds.PersistentEntity;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class ProveedorTemperatura extends PersistentEntity {
  public abstract List<Double> ultimasTresTemperaturas();

  public abstract void setCheckeoDeTemperaturaHandler(Runnable checkeoDeTemperaturaHandler);

  public abstract boolean hayFalloConexion();
}
