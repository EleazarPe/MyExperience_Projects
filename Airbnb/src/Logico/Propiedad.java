package Logico;

public class Propiedad {

	
public String ID;
public String Tipo;
public String Dirreccion;//por ahora lo puse asi, si lo quieren de otra forma se cambia
public String Precio;
public String Facilidades;
public String Estado;

public Propiedad(String iD, String tipo, String dirreccion, String precio, String facilidades, String estado) {
	super();
	this.ID = iD;
	this.Tipo = tipo;
	this.Dirreccion = dirreccion;
	this.Precio = precio;
	this.Facilidades = facilidades;
	this.Estado = estado;
}

public String getID() {
	return ID;
}
public void setID(String iD) {
	ID = iD;
}
public String getTipo() {
	return Tipo;
}
public void setTipo(String tipo) {
	Tipo = tipo;
}
public String getDirreccion() {
	return Dirreccion;
}
public void setDirreccion(String dirreccion) {
	Dirreccion = dirreccion;
}
public String getPrecio() {
	return Precio;
}
public void setPrecio(String precio) {
	Precio = precio;
}
public String getFacilidades() {
	return Facilidades;
}
public void setFacilidades(String facilidades) {
	Facilidades = facilidades;
}
public String getEstado() {
	return Estado;
}
public void setEstado(String estado) {
	Estado = estado;
}
}
