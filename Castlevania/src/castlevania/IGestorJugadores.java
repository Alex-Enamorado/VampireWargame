package castlevania;

public interface IGestorJugadores {

    boolean registrar(Jugador nuevoJugador);

    Jugador validarLogin(String usuario, String contrasenia);

    Jugador buscarPorUsuario(String usuario);

    Jugador[] getJugadores();

    int getCantidad();
}
