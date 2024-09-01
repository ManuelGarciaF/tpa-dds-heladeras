package ar.edu.utn.frba.dds.dominio;

import javax.persistence.Embeddable;

@Embeddable
public record TarjetaColaborador(String codigoTarjeta) {
}
