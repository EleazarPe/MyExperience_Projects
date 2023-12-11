package Logico;

import java.util.Date;

public class Factura {

public String ID;
public Date Fecha;
public Float Monto;

public Factura(String iD, Date fecha, Float monto) {
	super();
	this.ID = iD;
	this.Fecha = fecha;
	this.Monto = monto;
}

public String getID() {
	return ID;
}
public void setID(String iD) {
	ID = iD;
}
public Date getFecha() {
	return Fecha;
}
public void setFecha(Date fecha) {
	Fecha = fecha;
}
public Float getMonto() {
	return Monto;
}
public void setMonto(Float monto) {
	Monto = monto;
}


}
