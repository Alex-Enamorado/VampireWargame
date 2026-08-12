package castlevania;


public class GestorJugadores implements IGestorJugadores {

    private Jugador[] jugadores = new Jugador[100]; // donde se guardan los jugadoes
    private int cantidad = 0; //cantidad inicial 0

    @Override
    public boolean registrar(Jugador nuevoJugador) {

        if (buscarPorUsuario(nuevoJugador.getUsuario()) != null) {
            System.out.println("Ese usuario ya existe");
            return false;
        }

        if (cantidad >= jugadores.length) {
            System.out.println("Ya no hay espacio para más jugadores");
            return false;
        }

        jugadores[cantidad] = nuevoJugador;
        cantidad++;
        return true;
    }

    @Override
    public Jugador validarLogin(String usuario, String contrasenia) {
        Jugador j = buscarPorUsuario(usuario);

        if (j != null && j.isActivo() && j.getContrasenia().equals(contrasenia)) {
            return j;
        }

        return null;
    }

    //Punto de entrada de la búsqueda
    @Override
    public Jugador buscarPorUsuario(String usuario) {
        return buscarPorUsuarioRecursivo(usuario, 0);
    }

    //funcion para buscar usuario via recursividad
    private Jugador buscarPorUsuarioRecursivo(String usuario, int indice) {

        if (indice >= cantidad) {
            return null;
        }

        if (jugadores[indice].getUsuario().equalsIgnoreCase(usuario)) {
            return jugadores[indice];
        }

        return buscarPorUsuarioRecursivo(usuario, indice + 1);
    }

    @Override
    public Jugador[] getJugadores() {
        return jugadores;
    }

    @Override
    public int getCantidad() {
        return cantidad;
    }

    //Ordena a los jugadores por la cantidad de puntos
    public Jugador[] ordenarPorPuntos() {
        Jugador[] copia = new Jugador[cantidad];

        for (int i = 0; i < cantidad; i++) {
            copia[i] = jugadores[i];
        }

        ordenarPorPuntosRecursivo(copia, 0);
        return copia;
    }

    //Metodo recursivo para ordenar lpor directamente los oubntos disponibles
    private void ordenarPorPuntosRecursivo(Jugador[] arreglo, int inicio) {

        if (inicio >= arreglo.length - 1) {
            return;
        }

        int indiceMayor = inicio;

        for (int i = inicio + 1; i < arreglo.length; i++) {
            if (arreglo[i].getPuntos() > arreglo[indiceMayor].getPuntos()) {
                indiceMayor = i;
            }
        }

        Jugador temp = arreglo[inicio];
        arreglo[inicio] = arreglo[indiceMayor];
        arreglo[indiceMayor] = temp;

        ordenarPorPuntosRecursivo(arreglo, inicio + 1);
    }
}
