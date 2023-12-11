package Logico;

public class Administrador {

public String ID;
public String Nombre;
public String Correo;
public String Telefono;


public Administrador(String iD, String nombre, String correo, String telefono) {
	super();
	this.ID = iD;
	this.Nombre = nombre;
	this.Correo = correo;
	this.Telefono = telefono;
}

public String getID() {
	return ID;
}
public void setID(String iD) {
	ID = iD;
}
public String getNombre() {
	return Nombre;
}
public void setNombre(String nombre) {
	Nombre = nombre;
}
public String getCorreo() {
	return Correo;
}
public void setCorreo(String correo) {
	Correo = correo;
}
public String getTelefono() {
	return Telefono;
}
public void setTelefono(String telefono) {
	Telefono = telefono;
}
	
}
