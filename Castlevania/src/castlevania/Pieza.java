package castlevania;

public abstract class Pieza {
    protected TipoPieza tipo;
    protected int d_pieza; //Dueño de la pieza

    protected int fila;
    protected int columna;
    protected int escudo;
    protected int vida;
    protected int ataque;


    public Pieza(TipoPieza tipo, int d_pieza, int fila, int columna,
                 int escudo, int vida, int ataque) {

        this.tipo = tipo;
        this.d_pieza = d_pieza;
        this.fila=fila;
        this.columna=columna;
        this.escudo = escudo;
        this.vida = vida;
        this.ataque = ataque;


    }


    public abstract boolean puedeMover(int filaDestino, int columnaDestino);

    public final void recibirDanio(int cantidad, boolean perforacion) {
        if (perforacion) {
            vida -= cantidad;
        } else if (escudo >= cantidad) {
            escudo -= cantidad;
        } else if (escudo < cantidad) {
            escudo -= cantidad;
            vida += escudo;
            escudo = 0;
        }
        if (vida < 0) {
            vida = 0;
        }
    }


    public boolean seleccionRuleta(TipoPieza resultadoRuleta) {
        return (tipo != null && tipo == resultadoRuleta);


    }



    public boolean estaViva(){
        return vida>0;
    }

    public TipoPieza getTipo() {
        return tipo;
    }

    public int getDuenio() {
        return d_pieza;
    }

    public int getFila(){
        return fila;
    }

    public int getColumna() {
        return columna;
    }



    public int getEscudo() {
        return escudo;

    }

    public int getVida() {
        return vida;

    }

    public int getAtaque() {
        return ataque;

    }

    public void setPosicion(int nuevaFila, int nuevaColumna){
        this.fila = nuevaFila;
        this.columna= nuevaColumna;
    }










}
