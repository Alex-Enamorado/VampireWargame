package castlevania;

public class Zombie extends Pieza{
    //Escudo 0
    //Vida 1
    //Ataque 1

    public Zombie(int d_pieza, int fila,int columna){
        super(null,d_pieza,fila,columna,0,1,1);

    }

    @Override
    public boolean puedeMover(int filaDestino, int columnaDestino) {
        return false;
    }
}
