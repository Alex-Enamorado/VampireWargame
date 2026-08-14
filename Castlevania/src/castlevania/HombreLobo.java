package castlevania;

public class HombreLobo extends Pieza{
    //ESCUDO 2
    //VIDA 5
    //Ataque 5

    public HombreLobo(int d_pieza, int fila,int columna){
        super(TipoPieza.hombre_lobo,d_pieza,fila,columna,2,5,5);


    }


    public boolean puedeMover(int filaDestino, int columnaDestino) {
        int Dfila = Math.abs(filaDestino-this.fila);
        int Dcolumna = Math.abs(columnaDestino-this.columna);

        if (Dfila == 0 && Dcolumna == 0) {
            return false;
        }

        //Solo en linea recta: horizontal, vertical o diagonal
        boolean esLineaRecta = Dfila == 0 || Dcolumna == 0 || Dfila == Dcolumna;

        return esLineaRecta && Dfila <= 2 && Dcolumna <= 2;

    }
}
