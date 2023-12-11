package Logico;

public class Propietario {

public String IDPropietario;
public String Nombre;
public String Correo;
public String Telefono;
public String Tipo;

public Propietario(String iDPropietario, String nombre, String correo, String telefono, String tipo) {
	super();
	this.IDPropietario = iDPropietario;
	this.Nombre = nombre;
	this.Correo = correo;
	this.Telefono = telefono;
	this.Tipo = tipo;
}
public String getIDPropietario() {
	return IDPropietario;
}
public void setIDPropietario(String iDPropietario) {
	IDPropietario = iDPropietario;
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
public String getTipo() {
	return Tipo;
}
public void setTipo(String tipo) {
	Tipo = tipo;
}
}
