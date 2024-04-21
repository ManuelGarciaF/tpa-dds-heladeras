package ar.edu.utn.frba.dds;

public abstract class Usuario {
	private String nombre;
	private String contrasenia;

	public Usuario(String contrasenia, String nombre) {
		this.nombre = nombre;

		this.contrasenia = contrasenia;

	}

	public String getNombre() {
		return nombre;
	}

	public String getContrasenia() {
		return contrasenia;
	}
}
