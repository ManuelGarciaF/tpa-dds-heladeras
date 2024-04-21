package ar.edu.utn.frba.dds;
import java.time.LocalDate;

public class PersonaVulnerable {
	private String nombre;
	private String domicilio;
	private LocalDate fechaNacimiento;
	private Integer menoresACargo;

	public PersonaVulnerable(String nombre, String domicilio, LocalDate fechaNacimiento, Integer menoresACargo) {
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

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public Integer getMenoresACargo() {
		return menoresACargo;
	}
}
