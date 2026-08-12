package castlevania;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashSet;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import javax.swing.JLabel;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainMenu extends JPanel implements ActionListener {

    private JButton Ini_sesion = new JButton();
    private JButton Crear_j = new JButton();
    private JButton salir = new JButton();

    public MainMenu() {
        this.setLayout(new BorderLayout());

        // Musica
//        try {
//            Clip clip = AudioSystem.getClip();
//            clip.open(AudioSystem.getAudioInputStream(Main.class.getResource("/resources/ost.wav")));
//            clip.loop(Clip.LOOP_CONTINUOUSLY);
//        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
//        }

        //BACKGROUNS 
        ImageIcon background = new ImageIcon(Main.class.getResource("/resources/mainmenu_background1.png"));
        JLabel background_image = new JLabel();

        background_image.setLayout(null); 
        background_image.setIcon(background); 

        //titulo.getAlignmentX();
        JLabel titulo = new JLabel();
        titulo.setText("Vampire Wargame");
        titulo.setForeground(new Color(169, 47, 67));
        titulo.setFont(new Font("Old English Text MT", Font.BOLD, 98));
//        titulo.setBorder(new EmptyBorder(80, 80, 80, 80));
//        titulo.setHorizontalAlignment(SwingConstants.LEFT);
//        titulo.setVerticalAlignment(SwingConstants.TOP);
        titulo.setBounds(50, 200, 1000, 100);

        //Boton
        Ini_sesion.setContentAreaFilled(false);
        Ini_sesion.setText("Iniciar Sesion");
        Ini_sesion.setForeground(new Color(204, 0, 11));
        Ini_sesion.setFont(new Font("Old English Text MT", Font.BOLD, 38));
        Ini_sesion.setBounds(50, 350, 300, 80);
        Ini_sesion.setFocusable(false);
//        Ini_sesion.setOpaque(true);
//        Ini_sesion.setBackground(new Color(0,0,0,50));

        Ini_sesion.setBorderPainted(false);
        Ini_sesion.addActionListener(this);

        //Boton
        Crear_j.setContentAreaFilled(false);
        Crear_j.setText("Crear Jugador");
        Crear_j.setForeground(new Color(204, 0, 11));
        Crear_j.setFont(new Font("Old English Text MT", Font.BOLD, 38));
        Crear_j.setBounds(50, 450, 300, 80);
        Crear_j.setFocusable(false);
//        Crear_j.setOpaque(true);
//        Crear_j.setBackground(new Color(0,0,0,50));

        Crear_j.setBorderPainted(false);
        Crear_j.addActionListener(this);

        //Boton
        salir.setContentAreaFilled(false);
        salir.setText("Salir");
        salir.setForeground(new Color(204, 0, 11));
        salir.setFont(new Font("Old English Text MT", Font.BOLD, 38));
        salir.setBounds(-20, 550, 300, 80);
        salir.setFocusable(false);
//        salir.setOpaque(true);
//        salir.setBackground(new Color(0,0,0,50));

        salir.setBorderPainted(false);
        salir.addActionListener(this);

//        Ini_sesion.setHorizontalAlignment(SwingConstants.LEFT);
//        Ini_sesion.setVerticalAlignment(SwingConstants.TOP);
        //FRAME
//        background_image.add(titulo);
//        background_image.add(Ini_sesion);
//        background_image.add(Crear_j);
//        background_image.add(salir);
//        this.add(background_image);
//        this.setVisible(true);
        background_image.add(titulo);
        background_image.add(Ini_sesion);
        background_image.add(Crear_j);
        background_image.add(salir);

        this.add(background_image);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Ini_sesion) {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setContentPane(new IniciarSesion());
            frame.revalidate();
            frame.repaint();
            frame.setVisible(true);

        }

        if (e.getSource() == Crear_j) {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setContentPane(new CrearJugador());
            frame.revalidate();
            frame.repaint();
            frame.setVisible(true);

        }

        if (e.getSource() == salir) {
            System.exit(0);
        }

    }

}
