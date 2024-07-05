package ar.edu.utn.frba.dds.dominio;

public interface ContadorCantidadViandas {
  void onCambioDeVianda(AccionCambioVianda accionCambioVianda);
  Integer getCantidad();
}
