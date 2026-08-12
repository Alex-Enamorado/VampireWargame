package castlevania;

import java.util.Date;


public final class RegistroPartida {

    private String jugador1;
    private String jugador2;
    private String mensaje;
    private Date fecha;

    public RegistroPartida(String jugador1, String jugador2, String mensaje) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.mensaje = mensaje;
        this.fecha = new Date();
    }

    public String getJugador1() {
        return jugador1;
    }

    public String getJugador2() {
        return jugador2;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Date getFecha() {
        return fecha;
    }


    public boolean participo(String usuario) {
        return jugador1.equalsIgnoreCase(usuario) || jugador2.equalsIgnoreCase(usuario);
    }
}
