package org.example.encapsulaciones;

public class MensajeToView {
    private boolean emisor;
    private String mensaje;
    public MensajeToView(boolean emisor, String mensaje){
        this.setEmisor(emisor);
        this.setMensaje(mensaje);
    }
    public boolean isEmisor() {
        return emisor;
    }

    public void setEmisor(boolean emisor) {
        this.emisor = emisor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
