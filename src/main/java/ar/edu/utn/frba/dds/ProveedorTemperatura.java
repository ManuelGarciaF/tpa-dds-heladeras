package ar.edu.utn.frba.dds;

import java.util.List;

public interface ProveedorTemperatura {
  Double obtenerUltimaTemperatura();

  List<Double> ultimas3Temperaturas();
}
