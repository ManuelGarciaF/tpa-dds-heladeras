package ar.edu.utn.frba.dds.model.notificacionesheladera;

import ar.edu.utn.frba.dds.PersistentEntity;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class ProveedorMensajeria extends PersistentEntity {
  public abstract void enviarMensaje(String mensaje);
}
