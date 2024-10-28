package ar.edu.utn.frba.dds.model.sensoresheladera;

import ar.edu.utn.frba.dds.externo.TSensor;
import ar.edu.utn.frba.dds.model.ServiceLocator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("SENSOR")
public class ProveedorTemperaturaSensor extends ProveedorTemperatura {
  public static final int MINUTOS_DESCONECTADO_MAXIMOS = 15;

  private String numeroDeSerie;

  @ElementCollection
  private final List<Double> ultimasTresTemperaturas = new ArrayList<>();
  private LocalDate ultimaMedicion;

  @Transient
  private Runnable checkeoDeTemperaturaHandler;

  public ProveedorTemperaturaSensor(
      TSensor api,
      String numeroDeSerie) {
    this.numeroDeSerie = numeroDeSerie;
    api.connect(numeroDeSerie);
    // Le pasamos el metodo agregarLectura como callback
    api.onTemperatureChange(this::agregarLectura);
  }

  public ProveedorTemperaturaSensor() {
  }

  @PostLoad
  public void postLoad() {
    var api = ServiceLocator.getTSensor();
    api.connect(numeroDeSerie);
    api.onTemperatureChange(this::agregarLectura);
  }

  @Override
  public void setCheckeoDeTemperaturaHandler(Runnable checkeoDeTemperaturaHandler) {
    this.checkeoDeTemperaturaHandler = checkeoDeTemperaturaHandler;
  }

  @Override
  public boolean hayFalloConexion() {
    long diferenciaMinutos = ChronoUnit.MINUTES.between(LocalDateTime.now(), ultimaMedicion);
    return ultimaMedicion != null
        && diferenciaMinutos > MINUTOS_DESCONECTADO_MAXIMOS;
  }

  public void agregarLectura(Double temperatura) {
    if (ultimasTresTemperaturas.size() >= 3) {
      // Remover la temperatura más antigua
      ultimasTresTemperaturas.remove(0);
    }
    // Agregar la temperatura nueva al final
    ultimasTresTemperaturas.add(temperatura);
    ultimaMedicion = LocalDate.now();

    // Delegar el checkeo de temperatura a la heladera
    if (checkeoDeTemperaturaHandler != null) {
      checkeoDeTemperaturaHandler.run();
    }
  }

  @Override
  public List<Double> ultimasTresTemperaturas() {
    return ultimasTresTemperaturas;
  }
}
