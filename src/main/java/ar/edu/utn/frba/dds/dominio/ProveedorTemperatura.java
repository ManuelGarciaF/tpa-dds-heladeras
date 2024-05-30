package ar.edu.utn.frba.dds.dominio;

import java.util.List;

public interface ProveedorTemperatura {
  Double obtenerUltimaTemperatura();

  List<Double> ultimas3Temperaturas();
}
