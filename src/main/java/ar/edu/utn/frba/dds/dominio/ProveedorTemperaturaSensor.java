package ar.edu.utn.frba.dds.dominio;

import ar.edu.utn.frba.dds.dominio.incidentes.AlertaTemperatura;
import ar.edu.utn.frba.dds.externo.TSensor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ProveedorTemperaturaSensor implements ProveedorTemperatura {
  public static final int MINUTOS_DESCONECTADO_MAXIMOS = 15;

  private final List<Double> ultimasTresTemperaturas;
  private LocalDate ultimaMedicion;

  private final Double temperaturaMaximaAceptable;
  private final Double temperaturaMinimaAceptable;

  // Necesitamos la referencia doble para agregar el incidente
  private Heladera heladera;

  public ProveedorTemperaturaSensor(
      TSensor api,
      String numeroDeSerie,
      Double temperaturaMaximaAceptable,
      Double temperaturaMinimaAceptable) {
    this.temperaturaMaximaAceptable = temperaturaMaximaAceptable;
    this.temperaturaMinimaAceptable = temperaturaMinimaAceptable;
    ultimasTresTemperaturas = new ArrayList<>();

    api.connect(numeroDeSerie);
    // Le pasamos el método agregarLectura como callback
    api.onTemperatureChange(this::agregarLectura);
  }

  public void setHeladera(Heladera heladera) {
    this.heladera = heladera;
  }

  @Override
  public boolean requiereAtencion() {
    // Si tenemos al menos 3 temperaturas y todas son mayores a la maxima o menores a la minima
    boolean masAltas = ultimasTresTemperaturas.stream().allMatch(
        temperatura -> temperatura > temperaturaMaximaAceptable);
    boolean masBajas = ultimasTresTemperaturas.stream().allMatch(
        temperatura -> temperatura < temperaturaMinimaAceptable);
    return ultimasTresTemperaturas.size() >= 3 && (masAltas || masBajas);
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

    // Si la temperatura es menor a la mínima o mayor a la máxima, se genera una alerta
    if (requiereAtencion()) {
      heladera.nuevoIncidente(new AlertaTemperatura(LocalDateTime.now(), heladera.getUbicacion()));
    }
  }
}
