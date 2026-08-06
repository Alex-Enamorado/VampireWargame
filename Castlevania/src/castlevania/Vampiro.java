package castlevania;

public class Vampiro extends Pieza{
    //Escudo: 5;
    //Vida: 4;
    //Ataque de vampiro: 3;

    public Vampiro(int d_pieza, int fila, int columna){
        super(TipoPieza.vampiro, d_pieza, fila,columna,5,4,3);



    }



    public boolean puedeMover(int filaDestino, int columnaDestino) {
        int dFila= Math.abs(filaDestino-this.fila);
        int dColumna= Math.abs(columnaDestino-this.columna);

        return dFila<=1 && dColumna<=1 && ( dFila!=0 || dColumna!=0 );
    }


}
