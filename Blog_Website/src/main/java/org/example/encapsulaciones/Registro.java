package org.example.encapsulaciones;

import java.sql.Timestamp;

public class Registro {
    private String id;

    private String nombre;

    private Timestamp fecha;

    public Registro(String id, String nombre, Timestamp fecha){
        this.setId(id);
        this.setNombre(nombre);
        this.setFecha(fecha);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
}
