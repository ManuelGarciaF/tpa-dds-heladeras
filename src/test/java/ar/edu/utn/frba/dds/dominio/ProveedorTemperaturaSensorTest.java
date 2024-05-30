package ar.edu.utn.frba.dds.dominio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import ar.edu.utn.frba.dds.exceptions.TemperaturaNoDisponibleException;
import ar.edu.utn.frba.dds.externo.TSensor;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProveedorTemperaturaSensorTest {
  TSensor sensor;
  String numeroDeSerie;
  ProveedorTemperaturaSensor proveedorTemperaturaSensor;

  @BeforeEach
  void setUp() {
    sensor = mock(TSensor.class);
    numeroDeSerie = "124";
    proveedorTemperaturaSensor = new ProveedorTemperaturaSensor(sensor, numeroDeSerie);
  }

  @Test
  void puedoObtenerLaUltimaTemperaturaSiYaHuboUnaActualizacionDelSensor() {
    proveedorTemperaturaSensor.agregarLectura(20.0);
    assertEquals(20.0, proveedorTemperaturaSensor.obtenerUltimaTemperatura());
  }

  @Test
  void noPuedoObtenerLaUltimaTemperaturaSiNoHuboActualizaciones() {
    assertThrows(TemperaturaNoDisponibleException.class,
        () -> proveedorTemperaturaSensor.obtenerUltimaTemperatura());
  }

  @Test
  void noPuedoObtenerLasUltimasTresTemperaturasSiHayMenosLecturas() {
    proveedorTemperaturaSensor.agregarLectura(20.0);
    proveedorTemperaturaSensor.agregarLectura(21.0);
    assertThrows(TemperaturaNoDisponibleException.class,
        () -> proveedorTemperaturaSensor.ultimas3Temperaturas());
  }

  @Test
  void puedoObtenerLasUltimasTresTemperaturas() {
    proveedorTemperaturaSensor.agregarLectura(20.0);
    proveedorTemperaturaSensor.agregarLectura(21.0);
    proveedorTemperaturaSensor.agregarLectura(22.0);
    assertEquals(List.of(20.0, 21.0, 22.0), proveedorTemperaturaSensor.ultimas3Temperaturas());
  }
}