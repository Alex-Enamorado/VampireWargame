package castlevania;


public class HistorialPartidas implements IHistorialPartidas {

    private RegistroPartida[] registros = new RegistroPartida[200]; //arreglo donde se guarda el registro de las partidas jugadas.
    private int cantidad = 0;

    //Registro
    @Override
    public void registrar(RegistroPartida registro) {
        if (cantidad < registros.length) {
            registros[cantidad] = registro;
            cantidad++;
        } else {
            System.out.println("Ya no hay espacio para más registros de historial");
        }
    }


    @Override
    public RegistroPartida[] getRegistros() {
        return registros;
    }

    @Override
    public int getCantidad() {
        return cantidad;
    }
}
