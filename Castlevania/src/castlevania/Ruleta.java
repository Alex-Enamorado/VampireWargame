/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package castlevania;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;

/**
 *
 * @author aluk
 */
public class Ruleta extends JPanel{

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
    private boolean cayoEnGris = false;
    private int indiceResultado;



    public Ruleta() {

        setOpaque(false);
        ImageIcon icono = new ImageIcon(Main.class.getResource("/resources/RULETA111.png"));

        iconLobo = new ImageIcon(Main.class.getResource("/resources/rul_lobo.png")).getImage();
        iconVampiro = new ImageIcon(Main.class.getResource("/resources/rul_vampiro.png")).getImage();
        iconNecromante = new ImageIcon(Main.class.getResource("/resources/rul_necromante.png")).getImage();
        iconLoboBW = new ImageIcon(Main.class.getResource("/resources/rul_lobo-b.png")).getImage();
        iconVampiroBW = new ImageIcon(Main.class.getResource("/resources/rul_vampiro-b.png")).getImage();
        iconNecromanteBW = new ImageIcon(Main.class.getResource("/resources/rul_necromante-b.png")).getImage();


        imagenRuleta = icono.getImage();

        timer=new Timer(20,e->{

            angulo += velocidad;
            repaint();
            velocidad *= 0.985;

            if (velocidad < 0.3) {
                timer.stop();
                if (onResultado != null) {
                    onResultado.accept(calcularResultado());
                }
            }



        });


    }


    private java.util.function.Consumer<TipoPieza> onResultado;

    public void setOnResultado(java.util.function.Consumer<TipoPieza> callback) {
        this.onResultado = callback;
    }

    public boolean cayoEnGris() {
        return cayoEnGris;
    }

    private TipoPieza calcularResultado() {

        double anguloNormalizado = (270 - angulo) % 360;

        if (anguloNormalizado < 0) {
            anguloNormalizado += 360;
        }

        int indice = (int) (anguloNormalizado / 60);
        indiceResultado = indice;

        TipoPieza resultado = secciones[indice];

        System.out.println("Resultado de la ruleta: " + resultado + " (sección " + indice + ")");

        return resultado;
    }


    public void actualizarDisponible(int lobos, int vampiros, int necromantes){

        this.lobosVivos = lobos;
        this.vampirosVivos = vampiros;
        this.necromantesVivos = necromantes;

        repaint();
    }

    public boolean resultadoEsGris() {

        TipoPieza tipo = secciones[indiceResultado];

        if (tipo == TipoPieza.hombre_lobo) {
            if (lobosVivos == 0) return true;
            return lobosVivos == 1 && indiceResultado == 3;
        }

        if (tipo == TipoPieza.vampiro) {
            if (vampirosVivos == 0) return true;
            return vampirosVivos == 1 && indiceResultado == 4;
        }

        if (tipo == TipoPieza.necromante) {
            if (necromantesVivos == 0) return true;
            return necromantesVivos == 1 && indiceResultado == 5;
        }

        return false;
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

            // Si se perdi un lobo ponemos gris uno de los dos
            if (tipo == TipoPieza.hombre_lobo && lobosVivos == 1 && i == 3) {
                img = imagenGris(tipo);
            }

            // Si se perdieron los dos lobos los dos grises
            if (tipo == TipoPieza.hombre_lobo && lobosVivos == 0) {
                img = imagenGris(tipo);
            }

            // Si se perdio un vampiro ponemos gris uno de los dos
            if (tipo == TipoPieza.vampiro && vampirosVivos == 1 && i == 4) {
                img = imagenGris(tipo);
            }

            // Si se perdieron los dos vampiros los dos grises
            if (tipo == TipoPieza.vampiro && vampirosVivos == 0) {
                img = imagenGris(tipo);
            }

            // Si se perdio un necromante ponemos gris uno de los dos
            if (tipo == TipoPieza.necromante && necromantesVivos == 1 && i == 5) {
                img = imagenGris(tipo);
            }

            // Si se perdieron los dos necromantes los dos grises
            if (tipo == TipoPieza.necromante && necromantesVivos == 0) {
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

}
