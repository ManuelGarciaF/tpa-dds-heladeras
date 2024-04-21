package ar.edu.utn.frba.dds;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Heladera {
    //ubicacion
    private String nombre;
    private String capacidadViandas;
    private Date fechaCreacion;
    private List<Vianda> viandas;
    //ojo, aca la lista está vacía
    public Heladera(String nombre, String capacidadViandas, Date fechaCreacion) {
        this.nombre = nombre;
        this.capacidadViandas = capacidadViandas;
        this.fechaCreacion = fechaCreacion;
    }

    public void ingresarVianda(Vianda viandaNueva) {
        this.viandas.add(viandaNueva);
    }
}
