package org.example.encapsulaciones;

public class Mensajes {
    private  String nombre;

    private  String room;

    private  String mensaje;

    public Mensajes(String nombre, String room, String mensaje) {
        this.setNombre(nombre);
        this.setRoom(room);
        this.setMensaje(mensaje);
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRoom() {
        return this.room;
    }

    public  void setRoom(String room) {
        this.room = room;
    }

    public  String getMensaje() {
        return this.mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
