package castlevania;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.InputStream;

//Es un JButton normal, solo que sabe dibujarse a sí mismo:
//la imagen de la pieza ocupando casi toda la casilla, y arriba a la derecha,
//bien chiquito, un iconito + un número por stat (escudo azul, vida roja, ataque gris)
public class BotonPieza extends JButton {

    //Tamaño del iconito de cada stat (chiquito, va encima de la pieza)
    private static final int TAMANO_ICONO_STAT = 12;
    private static final int ALTO_FILA_STAT = 15;

    //Estos íconos y la fuente se cargan UNA sola vez para todas las casillas
    private static final Image ICONO_ESCUDO = cargarImagen("/resources/icono_escudo.png");
    private static final Image ICONO_VIDA = cargarImagen("/resources/icono_vida.png");
    private static final Image ICONO_ATAQUE = cargarImagen("/resources/icono_ataque.png");
    private static final Font FONT_PIXEL = cargarFontPixel();

    private static Image cargarImagen(String ruta) {
        try {
            return new ImageIcon(Main.class.getResource(ruta)).getImage();
        } catch (Exception e) {
            System.out.println("No se pudo cargar el ícono: " + ruta);
            return null; //si todavía no existe el archivo, simplemente no se dibuja
        }
    }

    private static Font cargarFontPixel() {
        try {
            InputStream in = Main.class.getResourceAsStream("/resources/fonts/pixel.ttf");
            Font base = Font.createFont(Font.TRUETYPE_FONT, in);
            return base.deriveFont(Font.PLAIN, 11f);
        } catch (Exception e) {
            System.out.println("No se pudo cargar la fuente pixel, se usa una normal mientras tanto");
            return new Font("Monospaced", Font.PLAIN, 11);
        }
    }

    private Image imagenPieza = null;
    private Integer escudo = null;
    private Integer vida = null;
    private Integer ataque = null;

    //Guarda la imagen de la pieza y sus 3 números para dibujarlos en el próximo repaint
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

        //La pieza ocupa casi toda la casilla, como antes
        int margen = 4;
        g2.drawImage(imagenPieza, margen, margen, ancho - (margen * 2), alto - (margen * 2), this);

        //Los stats van chiquitos, apilados arriba a la derecha, encima de la pieza
        int xStats = ancho - 36;
        int yInicial = 4;

        g2.setFont(FONT_PIXEL);

        dibujarStat(g2, ICONO_ESCUDO, String.valueOf(escudo), xStats, yInicial, Color.BLUE);
        dibujarStat(g2, ICONO_VIDA, String.valueOf(vida), xStats, yInicial + ALTO_FILA_STAT, Color.RED);
        dibujarStat(g2, ICONO_ATAQUE, String.valueOf(ataque), xStats, yInicial + (ALTO_FILA_STAT * 2), Color.GRAY);

        g2.dispose();
    }

    private void dibujarStat(Graphics2D g2, Image icono, String texto, int x, int y, Color colorTexto) {
        if (icono != null) {
            g2.drawImage(icono, x, y, TAMANO_ICONO_STAT, TAMANO_ICONO_STAT, this);
        }

        g2.setColor(colorTexto);
        g2.drawString(texto, x + TAMANO_ICONO_STAT + 3, y + TAMANO_ICONO_STAT - 1);
    }
}
