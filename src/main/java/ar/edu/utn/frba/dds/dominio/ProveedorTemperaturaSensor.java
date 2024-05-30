package ar.edu.utn.frba.dds.dominio;

import ar.edu.utn.frba.dds.exceptions.TemperaturaNoDisponibleException;
import ar.edu.utn.frba.dds.externo.TSensor;
import java.util.ArrayList;
import java.util.List;

public class ProveedorTemperaturaSensor implements ProveedorTemperatura {
  List<Double> ultimasTresTemperaturas;

  public ProveedorTemperaturaSensor(TSensor api, String numeroDeSerie) {
    ultimasTresTemperaturas = new ArrayList<>();

    api.connect(numeroDeSerie);
    // Le pasamos el método agregarLectura como callback
    api.onTemperatureChange(this::agregarLectura);
  }

  @Override
  public Double obtenerUltimaTemperatura() {
    if (ultimasTresTemperaturas.isEmpty()) {
      throw new TemperaturaNoDisponibleException("Temperatura no disponible");
    }
    return ultimasTresTemperaturas.get(ultimasTresTemperaturas.size() - 1);
  }

  @Override
  public List<Double> ultimas3Temperaturas() {
    if (ultimasTresTemperaturas.size() < 3) {
      throw new TemperaturaNoDisponibleException("No hay suficientes temperaturas");
    }
    return ultimasTresTemperaturas;
  }

  public void agregarLectura(Double temperatura) {
    if (ultimasTresTemperaturas.size() >= 3) {
      // Remover la temperatura más antigua
      ultimasTresTemperaturas.remove(0);
    }
    // Agregar la temperatura nueva al final
    ultimasTresTemperaturas.add(temperatura);
  }
}
