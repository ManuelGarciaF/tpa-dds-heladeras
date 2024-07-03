package ar.edu.utn.frba.dds.dominio;

import ar.edu.utn.frba.dds.dominio.incidentes.MedicionDeTemperatura;
import java.util.List;

public interface ProveedorTemperatura {
  List<Double> ultimas3Temperaturas();

  MedicionDeTemperatura getUltimaMedicionDeTemperatura();
}
