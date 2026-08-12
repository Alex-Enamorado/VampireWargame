package castlevania;

public interface IHistorialPartidas {

    void registrar(RegistroPartida registro);

    RegistroPartida[] getRegistros();

    int getCantidad();
}
