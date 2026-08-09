/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package castlevania;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;
import javax.swing.Timer;

/**
 *
 * @author aluk
 */
public class Ruleta extends JPanel{

    private Image imagenRuleta;
    private Image iconLobo;
    private Image iconVampiro;
    private Image iconNecromante;
    private Timer timer;
    private double angulo = 0;
    private double velocidad=0;
    Random rn = new Random();
    private final TipoPieza[] secciones = { TipoPieza.hombre_lobo,TipoPieza.vampiro,  TipoPieza.necromante,
                                             TipoPieza.hombre_lobo, TipoPieza.vampiro,  TipoPieza.necromante};

    private boolean hayLobo = true;
    private boolean hayVampiro = true;
    private boolean hayNecromante = true;







    public Ruleta() {

        setOpaque(false);
        ImageIcon icono = new ImageIcon(Main.class.getResource("/resources/RULETA111.png"));

        iconLobo = new ImageIcon(Main.class.getResource("/resources/rul_lobo.png")).getImage();
        iconVampiro = new ImageIcon(Main.class.getResource("/resources/rul_vampiro.png")).getImage();
        iconNecromante = new ImageIcon(Main.class.getResource("/resources/rul_necromante.png")).getImage();
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

    private TipoPieza calcularResultado() {
        double anguloNormalizado = (270-angulo) % 360;
        if (anguloNormalizado < 0) {
            anguloNormalizado += 360;
        }

        int indice = (int) (anguloNormalizado / 60);
        return secciones[indice];
    }


    public void actualizarDisponible(boolean lobo, boolean vampiro, boolean necromante){
        this.hayLobo=lobo;
        this.hayVampiro=vampiro;
        this.hayNecromante=necromante;
        repaint();

    }




    private boolean tipoDisponible(TipoPieza tipo){
        if(tipo== TipoPieza.hombre_lobo){
            return hayLobo;

        }
        if(tipo== TipoPieza.vampiro){
            return hayVampiro;

        }
        if(tipo == TipoPieza.necromante){
            return hayNecromante;
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

    @Override
    protected void paintComponent(Graphics g) {
        System.out.println("Repintnado");
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g.create();

        int cx = getWidth()/2;
        int cy = getHeight()/2;


        g2.rotate(Math.toRadians(angulo),cx,cy);




        g2.drawImage(imagenRuleta, 0, 0, getWidth(), getHeight(), this);
        double radioFraccion = 0.28;
        double tamanoFraccion = 0.15;

        int radio = (int)(getWidth()*radioFraccion);
        int tamanoIcono = (int)(getWidth()*tamanoFraccion);

        for(int i = 0; i < secciones.length;i++){
            TipoPieza tipo= secciones[i];
            if(!tipoDisponible(tipo)){
                continue;
            }

            double anguloSeccion = Math.toRadians(30+i*60);
            int x = cx + (int) (radio * Math.cos(anguloSeccion))- tamanoIcono/2;
            int y = cy + (int) (radio * Math.sin(anguloSeccion))- tamanoIcono/2;

            Image img = imagenParaTipo(tipo);
            g2.drawImage(img, x ,y, tamanoIcono, tamanoIcono,this);


        }



        g2.dispose();
    }


    public void girar(){

        angulo=rn.nextInt(0,361);
        velocidad=10;
        timer.start();
    }

}
