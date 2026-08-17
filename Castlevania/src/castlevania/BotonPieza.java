package castlevania;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.InputStream;


public class BotonPieza extends JButton {

    //Tamaño del iconito de cada stat
    private static final int TAMANO_ICONO_STAT = 12;
    private static final int ALTO_FILA_STAT = 15;

    //Estos íconos
    private static final Image ICONO_ESCUDO = cargarImagen("/resources/icono_escudo.png");
    private static final Image ICONO_VIDA = cargarImagen("/resources/icono_vida.png");
    private static final Image ICONO_ATAQUE = cargarImagen("/resources/icono_ataque.png");
    private static final Font FONT_PIXEL = cargarFontPixel();

    private static Image cargarImagen(String ruta) {
        try {
            return new ImageIcon(Main.class.getResource(ruta)).getImage();
        } catch (Exception e) {
            return null; //si todavía no existe el archivo no lo carga
        }
    }





    //Aqui cargamos la font de los stats
    private static Font cargarFontPixel() {
        try {
            InputStream in = Main.class.getResourceAsStream("/resources/fonts/pixel.ttf");
            Font base = Font.createFont(Font.TRUETYPE_FONT, in);
            return base.deriveFont(Font.PLAIN, 5.5f);
        } catch (Exception e) {
            return null; //si falla se deja la font por defecto
        }
    }

    private Image imagenPieza = null;
    private Integer escudo = null;
    private Integer vida = null;
    private Integer ataque = null;



    //Guarda la imagen de la pieza y los numeros
    public void mostrarPieza(Image imagenPieza, int escudo, int vida, int ataque) {
        this.imagenPieza = imagenPieza;
        this.escudo = escudo;
        this.vida = vida;
        this.ataque = ataque;
        repaint();
    }



    public void ocultarStats() {
        this.imagenPieza = null;
        this.escudo = null;
        this.vida = null;
        this.ataque = null;
        repaint();
    }




    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (imagenPieza == null) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();

        int ancho = getWidth();
        int alto = getHeight();

        //La pieza es la mayoria de la casilla
        int margen = 4;
        g2.drawImage(imagenPieza, margen, margen, ancho - (margen * 2), alto - (margen * 2), this);

        //Los stats van chiquitos arriba a la derecha
        int xStats = ancho - 36;
        int yInicial = 4;

        if (FONT_PIXEL != null) {
            g2.setFont(FONT_PIXEL);
        }

        dibujarStat(g2, ICONO_ESCUDO, String.valueOf(escudo), xStats, yInicial, Color.BLUE);
        dibujarStat(g2, ICONO_VIDA, String.valueOf(vida), xStats, yInicial + ALTO_FILA_STAT, Color.RED);
        dibujarStat(g2, ICONO_ATAQUE, String.valueOf(ataque), xStats, yInicial + (ALTO_FILA_STAT * 2), Color.GRAY);

        g2.dispose();
    }






    //Pinta los stats en la pantalla
    private void dibujarStat(Graphics2D g2, Image icono, String texto, int x, int y, Color colorTexto) {
        if (icono != null) {
            g2.drawImage(icono, x, y, TAMANO_ICONO_STAT, TAMANO_ICONO_STAT, this);
        }

        g2.setColor(colorTexto);
        g2.drawString(texto, x + TAMANO_ICONO_STAT + 3, y + TAMANO_ICONO_STAT - 1);
    }
}
