package ar.edu.utn.frba.dds.dominio;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.externo.Reading;
import ar.edu.utn.frba.dds.externo.TSensor;
import ar.edu.utn.frba.dds.externo.WSensor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HeladeraTest {
  private Heladera heladera;
  private TSensor tSensor;
  private WSensor wSensor;
  private ProveedorTemperaturaSensor proveedorTemperaturaSensor;

  @BeforeEach
  void setUp() {
    tSensor = mock(TSensor.class);
    wSensor = mock(WSensor.class);
    proveedorTemperaturaSensor = new ProveedorTemperaturaSensor(tSensor, "kd993j");

    heladera = new Heladera("heladera",
        40,
        new Ubicacion(0.1, 0.0),
        "kd993j",
        23,
        new ProveedorPesoSensor(wSensor),
        proveedorTemperaturaSensor);
  }

  @Test
  void requiereAtencionCuandoLasUltimasTresTemperaturasEstanSobreTemperaturaMaxima() {
    proveedorTemperaturaSensor.agregarLectura(24.0);
    proveedorTemperaturaSensor.agregarLectura(25.0);
    proveedorTemperaturaSensor.agregarLectura(123.0);

    assertTrue(heladera.requiereAtencion());
  }

  @Test
  void noRequiereAtencionCuandoLasUltimasTresTemperaturasNoEstanSobreTemperaturaMaxima() {
    proveedorTemperaturaSensor.agregarLectura(20.0);
    proveedorTemperaturaSensor.agregarLectura(10.0);
    proveedorTemperaturaSensor.agregarLectura(123.0);

    assertFalse(heladera.requiereAtencion());
  }

  @Test
  void nivelLlenadoEsBajoCuandoHay1kgDeViandas() {
    when(wSensor.getWeight("kd993j")).thenReturn(new Reading(1.0, "KG"));
    assertEquals(NivelLlenado.BAJO, heladera.nivelLlenado());
  }

  @Test
  void nivelLlenadoEsMedioCuandoHay6kgDeViandas() {
    when(wSensor.getWeight("kd993j")).thenReturn(new Reading(6.0, "KG"));
    assertEquals(NivelLlenado.MEDIO, heladera.nivelLlenado());
  }

  @Test
  void nivelLlenadoEsAltoCuandoHay200kgDeViandas() {
    when(wSensor.getWeight("kd993j")).thenReturn(new Reading(200.0, "KG"));
    assertEquals(NivelLlenado.ALTO, heladera.nivelLlenado());
  }
}