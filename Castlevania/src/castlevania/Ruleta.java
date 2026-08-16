/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package castlevania;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 *
 * @author aluk
 */
public class Ruleta extends JPanel implements ActionListener{

    private Tablero tablero;
    private Image imagenRuleta;
    private Image iconLobo;
    private Image iconVampiro;
    private Image iconNecromante;
    private Image iconLoboBW;
    private Image iconVampiroBW;
    private Image iconNecromanteBW;
    private Timer timer;
    private double angulo = 0;
    private double velocidad=0;
    Random rn = new Random();
    private final TipoPieza[] secciones = { TipoPieza.hombre_lobo,TipoPieza.vampiro,  TipoPieza.necromante,
                                             TipoPieza.hombre_lobo, TipoPieza.vampiro,  TipoPieza.necromante};

    private int lobosVivos = 2;
    private int vampirosVivos = 2;
    private int necromantesVivos = 2;



    public Ruleta(Tablero tablero) {

        this.tablero = tablero;
        setOpaque(false);
        ImageIcon icono = new ImageIcon(Main.class.getResource("/resources/RULETA111.png"));

        iconLobo = new ImageIcon(Main.class.getResource("/resources/rul_lobo.png")).getImage();
        iconVampiro = new ImageIcon(Main.class.getResource("/resources/rul_vampiro.png")).getImage();
        iconNecromante = new ImageIcon(Main.class.getResource("/resources/rul_necromante.png")).getImage();
        iconLoboBW = new ImageIcon(Main.class.getResource("/resources/rul_lobo-b.png")).getImage();
        iconVampiroBW = new ImageIcon(Main.class.getResource("/resources/rul_vampiro-b.png")).getImage();
        iconNecromanteBW = new ImageIcon(Main.class.getResource("/resources/rul_necromante-b.png")).getImage();


        imagenRuleta = icono.getImage();

        timer=new Timer(20,this);


    }



    private TipoPieza calcularResultado() {

        double anguloNormalizado = (270 - angulo) % 360;

        if (anguloNormalizado < 0) {
            anguloNormalizado += 360;
        }

        int indice = (int) (anguloNormalizado / 60);

        TipoPieza resultado = secciones[indice];

        System.out.println("Resultado de la ruleta: " + resultado + " (sección " + indice + ")");

        // Si cayó en un ícono gris, no cuenta como resultado válido
        if (esGris(resultado, indice)) {
            System.out.println("Cayó en una pieza eliminada, no cuenta.");
            return null;
        }

        return resultado;
    }


    // Misma regla que se usa para pintar el ícono en gris
    private boolean esGris(TipoPieza tipo, int i) {
        if (tipo == TipoPieza.hombre_lobo) {
            return lobosVivos == 0 || (lobosVivos == 1 && i == 3);
        }
        if (tipo == TipoPieza.vampiro) {
            return vampirosVivos == 0 || (vampirosVivos == 1 && i == 4);
        }
        if (tipo == TipoPieza.necromante) {
            return necromantesVivos == 0 || (necromantesVivos == 1 && i == 5);
        }
        return false;
    }


    public void actualizarDisponible(int lobos, int vampiros, int necromantes){

        this.lobosVivos = lobos;
        this.vampirosVivos = vampiros;
        this.necromantesVivos = necromantes;

        repaint();
    }











    private Image imagenParaTipo(TipoPieza tipo){
        if(tipo== TipoPieza.hombre_lobo){
            return iconLobo;
        }
        if(tipo== TipoPieza.vampiro){
            return iconVampiro;
        }
        if(tipo==TipoPieza.necromante){
            return iconNecromante;
        }
        return null;

    }


    private Image imagenGris(TipoPieza tipo){
        if (tipo== TipoPieza.hombre_lobo){
            return iconLoboBW;
        }
        if (tipo== TipoPieza.vampiro){
            return iconVampiroBW;
        }
        if (tipo== TipoPieza.necromante){
            return iconNecromanteBW;
        }
        return null;



    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        Graphics2D g2 = (Graphics2D) g.create();

        int cx = getWidth() / 2;
        int cy = getHeight() / 2;

        g2.rotate(Math.toRadians(angulo), cx, cy);

        g2.drawImage(
                imagenRuleta,
                0,
                0,
                getWidth(),
                getHeight(),
                this
        );

        double radioFraccion = 0.28;
        double tamanoFraccion = 0.15;

        int radio = (int) (getWidth() * radioFraccion);
        int tamanoIcono = (int) (getWidth() * tamanoFraccion);

        for (int i = 0; i < secciones.length; i++) {

            TipoPieza tipo = secciones[i];

            // Primero mostramos la imagen normal
            Image img = imagenParaTipo(tipo);

            // Si ya no queda esa pieza (o ya se perdió la de este lado) se pinta gris
            if (esGris(tipo, i)) {
                img = imagenGris(tipo);
            }

            double anguloSeccion = Math.toRadians(30 + i * 60);

            int x = cx
                    + (int) (radio * Math.cos(anguloSeccion))
                    - tamanoIcono / 2;

            int y = cy
                    + (int) (radio * Math.sin(anguloSeccion))
                    - tamanoIcono / 2;

            g2.drawImage(
                    img,
                    x,
                    y,
                    tamanoIcono,
                    tamanoIcono,
                    this
            );
        }

        g2.dispose();


    }



    public void girar(){

        angulo=rn.nextInt(0,361);
        velocidad=10;
        timer.start();
    }


    //Se ejecuta cada 20ms mientras gira la ruleta (evento del Timer)
    @Override
    public void actionPerformed(ActionEvent e) {

        angulo += velocidad;
        repaint();
        velocidad *= 0.985;

        if (velocidad < 0.3) {
            timer.stop();
            tablero.procesarResultadoRuleta(calcularResultado());
        }
    }

}
