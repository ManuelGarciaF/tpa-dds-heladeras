package ar.edu.utn.frba.dds.dominio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.externo.Reading;
import ar.edu.utn.frba.dds.externo.WSensor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProveedorPesoSensorTest {
  WSensor sensor;
  ProveedorPesoSensor proveedorPesoSensor;

  @BeforeEach
  void setUp() {
    sensor = mock(WSensor.class);
    proveedorPesoSensor = new ProveedorPesoSensor(sensor);
  }

  @Test
  void obtenerPesoFuncionaCuandoDevuelveKgs() {
    when(sensor.getWeight("123")).thenReturn(new Reading(42, "KG"));

    assertEquals(42_000, proveedorPesoSensor.obtenerPesoGramos("123"));
  }

  @Test
  void obtenerPesoFuncionaCuandoDevuelveLbs() {
    when(sensor.getWeight("123")).thenReturn(new Reading(42, "lbs"));

    assertEquals(19050.88, proveedorPesoSensor.obtenerPesoGramos("123"), 1);
  }
}