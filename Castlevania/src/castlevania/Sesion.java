package castlevania;


public class Sesion {

    public static GestorJugadores gestorJugadores = new GestorJugadores();
    public static Jugador jugadorActual = null;

    public static HistorialPartidas historialPartidas = new HistorialPartidas();

    public static void registrarPartida(RegistroPartida registro) {
        historialPartidas.registrar(registro);
    }

    public static void cerrarSesion() {
        jugadorActual = null;
    }
}
