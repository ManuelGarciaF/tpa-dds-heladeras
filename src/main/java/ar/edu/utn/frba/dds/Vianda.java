package ar.edu.utn.frba.dds;


import java.util.Date;

public class Vianda {
    private String tipoComida;
    private Date fechaVencimiento;
    private Date fechaDeDonacion;
    //colaborador (acá podemos crear un metodo para que un colaborador cree una vianda)
    private Heladera heladeraDondeEstaAlmacenada;
    private int calorias;
    private int peso;
    private Boolean fueEntregado;

    //falta hacerlo que sea opcional poner el peso y las caloreas
    public Vianda(Date fechaVencimiento, Date fechaDeDonacion,Heladera heladeraDondeEstaAlmacenada, Boolean fueEntregado, int calorias, int peso) {
        this.fechaVencimiento = fechaVencimiento;
        this.fechaDeDonacion = fechaDeDonacion;
        this.heladeraDondeEstaAlmacenada = heladeraDondeEstaAlmacenada;
        this.fueEntregado = fueEntregado;
        this.peso = peso;
        this.calorias = calorias;

        //Ya se almacena directamente en la heladera
        this.heladeraDondeEstaAlmacenada.ingresarVianda(this);
    }
}
