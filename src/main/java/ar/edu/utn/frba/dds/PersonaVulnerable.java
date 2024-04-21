package ar.edu.utn.frba.dds;
import java.util.Date;

public class PersonaVulnerable {
	private String nombre;
	private String domicilio;
	private Date fechaNacimiento;
	private Integer menoresACargo;

	public PersonaVulnerable(String nombre, String domicilio, Date fechaNacimiento, Integer menoresACargo) {
		this.nombre = nombre;
		this.domicilio = domicilio;
		this.fechaNacimiento = fechaNacimiento;
		this.menoresACargo = menoresACargo;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public Integer getMenoresACargo() {
		return menoresACargo;
	}
}
