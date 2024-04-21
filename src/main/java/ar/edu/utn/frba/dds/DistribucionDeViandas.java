package ar.edu.utn.frba.dds;

import java.util.Date;

public class DistribucionDeViandas implements ColaboracionHumana{

    private MotivoDeDistribucion motivoDeDistribucion;
    private Heladera heladeraOrigen;
    private Heladera heladeraDestino;
    // este int no me convence, para mi es una lista de viandas y que cuando hagamos el translado de viandas vaya sacando de a una
    private int cantidadDeViandas;
    private Date fechaDeLaDistribucion;


    @Override
    public void realizarColaboracion(){

    }
}
