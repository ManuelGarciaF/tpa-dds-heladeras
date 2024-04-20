package ar.edu.utn.frba.dds;
import java.util.Date;

public class PersonaHumana {
    private String nombre;
    private String apellido;
    private Date fechaDeNacimiento;
    private String direccion;
    private FormasDeColaboracion formaDeColaboracion;
    private List<Colaboracion> historialDeColaboraciones;

    public PersonaHumana(String nombre, String apellido, Date fechaDeNacimiento, String direccion, FormasDeColaboracion formaDeColaboracion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.direccion = direccion;
        this.formaDeColaboracion = formaDeColaboracion;
    }

}
