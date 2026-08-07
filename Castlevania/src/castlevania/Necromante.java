package castlevania;

public class Necromante extends Pieza{
    //escudo 1
    //vida 3
    //ataque 4

    public Necromante(int d_pieza,int fila,int columna){
        super(TipoPieza.necromante,d_pieza,fila,columna,1,3,4);

    }



    public boolean puedeMover(int filaDestino, int columnaDestino) {
        int Dfila= Math.abs(filaDestino-this.fila);
        int Dcolumna= Math.abs(columnaDestino-this.columna);
        return Dfila<=1 && Dcolumna<=1 && (Dfila!=0 || Dcolumna!=0);
    }
}
