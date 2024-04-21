package ar.edu.utn.frba.dds;
import java.util.Date;
import java.util.List;

import static java.lang.System.exit;

public class PersonaHumana {
    private String nombre;
    private String apellido;
    private Date fechaDeNacimiento;
    private String direccion;
    //Queda poner el medio de contacto
   // private List<FormasDeColaboracion> formaDeColaboracion;
    private List<ColaboracionHumana> formaDeColaboracion;

    public PersonaHumana(String nombre, String apellido, Date fechaDeNacimiento, String direccion, ColaboracionHumana formaDeColaboracion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.direccion = direccion;
        this.formaDeColaboracion.add(formaDeColaboracion);
    }
    //Al ejecutar el metodo de colaborar, ejecuta cada tipo de colaboracion PENDIENTE
    public void colaborarTodoJunto(){
        this.formaDeColaboracion.forEach(tipoDeColaboracion -> tipoDeColaboracion.realizarColaboracion());
    }
    public void colaborar(ColaboracionHumana formaDeColaborar){
        //Acá verifico si esa forma de colaborar le pertenece al usuario para no poner cualquier verdura
        if(!this.formaDeColaboracion.contains(formaDeColaborar)){
            System.out.println("no existe esa forma de colaborar en esta persona");
            exit(0);
        }
        formaDeColaborar.realizarColaboracion();
    }
}
