package Logico;

public class Cliente {

	private String ID;
	private String Nombre1;
	private String Nombre2;
	private String Apellido1;
	private String Apellido2;
	private String Telefono;
	private String Correo;


	public Cliente(String iD, String nombre1, String nombre2, String apellido1, String apellido2, String telefono,
			String correo) {
		super();
		this.ID = iD;
		this.Nombre1 = nombre1;
		this.Nombre2 = nombre2;
		this.Apellido1 = apellido1;
		this.Apellido2 = apellido2;
		this.Telefono = telefono;
		this.Correo = correo;
	}


	public String getID() {
		return ID;
	}


	public void setID(String iD) {
		ID = iD;
	}


	public String getNombre1() {
		return Nombre1;
	}


	public void setNombre1(String nombre1) {
		Nombre1 = nombre1;
	}


	public String getNombre2() {
		return Nombre2;
	}


	public void setNombre2(String nombre2) {
		Nombre2 = nombre2;
	}


	public String getApellido1() {
		return Apellido1;
	}


	public void setApellido1(String apellido1) {
		Apellido1 = apellido1;
	}


	public String getApellido2() {
		return Apellido2;
	}


	public void setApellido2(String apellido2) {
		Apellido2 = apellido2;
	}


	public String getTelefono() {
		return Telefono;
	}


	public void setTelefono(String telefono) {
		Telefono = telefono;
	}


	public String getCorreo() {
		return Correo;
	}


	public void setCorreo(String correo) {
		Correo = correo;
	}


}
