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
    private Timer timer;
    private double angulo = 0;
    private double velocidad=0;
    Random rn = new Random();


    public Ruleta() {

        setOpaque(false);
        ImageIcon icono = new ImageIcon(Main.class.getResource("/resources/RULETA111.png"));
        imagenRuleta = icono.getImage();

        timer=new Timer(20,e->{

            angulo+=velocidad;
            System.out.println(angulo);
            repaint();

            velocidad *= 0.985;

            if (velocidad < 0.3) {
                timer.stop();
            }

        });


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

        g2.dispose();
    }


    public void girar(){
//        angulo +=10;
//        System.out.println("Angulo= " + angulo);
//        repaint();
        angulo=rn.nextInt(0,361);
        velocidad=10;
        timer.start();
    }

}
