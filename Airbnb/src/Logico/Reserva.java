package Logico;

import java.util.Date;

public class Reserva {

public String ID;
public Date Reserva;
public Date Checkin;
public Date Checkout;
public float Costo;

public Reserva(String iD, Date reserva, Date checkin, Date checkout, float costo, String estado) {
	super();
	ID = iD;
	this.Reserva = reserva;
	this.Checkin = checkin;
	this.Checkout = checkout;
	this.Costo = costo;
	this.Estado = estado;
}

public String getID() {
	return ID;
}
public void setID(String iD) {
	ID = iD;
}
public Date getReserva() {
	return Reserva;
}
public void setReserva(Date reserva) {
	Reserva = reserva;
}
public Date getCheckin() {
	return Checkin;
}
public void setCheckin(Date checkin) {
	Checkin = checkin;
}
public Date getCheckout() {
	return Checkout;
}
public void setCheckout(Date checkout) {
	Checkout = checkout;
}
public float getCosto() {
	return Costo;
}
public void setCosto(float costo) {
	Costo = costo;
}
public String getEstado() {
	return Estado;
}
public void setEstado(String estado) {
	Estado = estado;
}
public String Estado;
}
