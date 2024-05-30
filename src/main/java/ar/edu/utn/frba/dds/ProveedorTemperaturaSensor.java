package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.externo.TSensor;
import java.util.List;

public class ProveedorTemperaturaSensor implements ProveedorTemperatura {
  List<Double> ultimasTresTemperaturas;

  public ProveedorTemperaturaSensor(TSensor api, String numeroDeSerie) {
    api.connect(numeroDeSerie);
    // Le pasamos el método agregarLectura como callback
    api.onTemperatureChange(this::agregarLectura);
  }

  @Override
  public Double obtenerUltimaTemperatura() {
    return ultimasTresTemperaturas.get(ultimasTresTemperaturas.size() - 1);
  }

  @Override
  public List<Double> ultimas3Temperaturas() {
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
