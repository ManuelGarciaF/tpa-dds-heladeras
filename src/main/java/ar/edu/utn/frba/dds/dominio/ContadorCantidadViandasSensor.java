package ar.edu.utn.frba.dds.dominio;

import ar.edu.utn.frba.dds.externo.LSensor;

public class ContadorCantidadViandasSensor implements ContadorCantidadViandas {
  private final LSensor api;
  private int ultimoValor;
  public ContadorCantidadViandasSensor(LSensor api) {
    this.api = api;

    api.onLunchBoxChanged(this::actualizarUltimoValor);
  }


  @Override
  public void onCambioDeVianda(AccionCambioVianda accionCambioVianda) {
    api.onLunchBoxChanged(accionCambioVianda::onCambioDeVianda);
  }

  @Override
  public Integer getCantidad() {
    return ultimoValor;
  }

  public void actualizarUltimoValor(Integer valorNuevo){
    ultimoValor = valorNuevo ;
  }

}
