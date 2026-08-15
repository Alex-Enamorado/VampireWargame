package castlevania;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrearJugador extends JPanel implements ActionListener {

    private JTextField campoUsuario = new JTextField();
    private JPasswordField campoContrasenia = new JPasswordField();
    private JButton botonCrear = new JButton();
    private JButton botonVolver = new JButton();
    private JButton botonMostrarContrasenia = new JButton();

    private final char echoCharOculto;
    private boolean contraseniaVisible = false;

    private final Color colorTextoBotones = new Color(204, 0, 11);
    private final Color colorTitulo = new Color(169, 47, 67);
    private final Color colorCampoFondo = new Color(30, 18, 20);
    private final Color colorCampoTexto = new Color(235, 220, 210);
    private final Color colorCampoBorde = new Color(169, 47, 67);

    //Pantalla crear jugador
    public CrearJugador() {
        this.setLayout(new BorderLayout());

        ImageIcon background = new ImageIcon(Main.class.getResource("/resources/mainmenu_background1.png"));
        JLabel background_image = new JLabel();
        background_image.setLayout(null);
        background_image.setIcon(background);

        int boxWidth = 560;
        int boxX = (1600 - boxWidth) / 2;

        echoCharOculto = campoContrasenia.getEchoChar();


        //Crea titulo de crear jugador
        JLabel titulo = new JLabel("Crear Jugador");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(colorTitulo);
        titulo.setFont(new Font("Angel wish", Font.BOLD, 60));
        titulo.setBounds(boxX, 150, boxWidth, 90);

        //Crear label de usuario
        JLabel labelUsuario = new JLabel("Usuario:");
        labelUsuario.setForeground(colorTextoBotones);
        labelUsuario.setFont(new Font("Angel wish", Font.BOLD, 28));
        labelUsuario.setBounds(boxX, 280, 150, 40);
        //Crea label de contrasseña
        JLabel labelContrasenia = new JLabel("Contraseña (5):");
        labelContrasenia.setForeground(colorTextoBotones);
        labelContrasenia.setFont(new Font("Angel wish", Font.BOLD, 24));
        labelContrasenia.setBounds(boxX, 350, 200, 40);

        //Da espacio para escribir el usuario
        campoUsuario.setBounds(boxX + 160, 280, 360, 35);
        campoUsuario.setFont(new Font("Blackletter Shadow", Font.PLAIN, 18));
        estilizarCampo(campoUsuario);

        //Crea el espacio para escribir la constraseña
        campoContrasenia.setBounds(boxX + 210, 350, 230, 35);
        campoContrasenia.setFont(new Font("Blackletter Shadow", Font.PLAIN, 18));
        estilizarCampo(campoContrasenia);


        //Customoizacion de boton de contraseña
        botonMostrarContrasenia.setText("Ver");
        botonMostrarContrasenia.setContentAreaFilled(false);
        botonMostrarContrasenia.setForeground(colorTextoBotones);
        botonMostrarContrasenia.setFont(new Font("Blackletter Shadow", Font.BOLD, 14));
        botonMostrarContrasenia.setBounds(boxX + 450, 350, 90, 35);
        botonMostrarContrasenia.setFocusable(false);
        botonMostrarContrasenia.setBorder(BorderFactory.createLineBorder(colorCampoBorde, 1));
        botonMostrarContrasenia.addActionListener(this);

        int botonAncho = 300;
        int botonX = boxX + (boxWidth - botonAncho) / 2;
        //Customiazacion boton de crear
        botonCrear.setContentAreaFilled(false);
        botonCrear.setText("Crear");
        botonCrear.setForeground(colorTextoBotones);
        botonCrear.setFont(new Font("Angel wish", Font.BOLD, 38));
        botonCrear.setBounds(botonX, 430, botonAncho, 80);
        botonCrear.setFocusable(false);
        botonCrear.setBorderPainted(false);
        botonCrear.addActionListener(this);
        //Customaizacion boton de volver
        botonVolver.setContentAreaFilled(false);
        botonVolver.setText("Volver");
        botonVolver.setForeground(colorTextoBotones);
        botonVolver.setFont(new Font("Angel wish", Font.BOLD, 38));
        botonVolver.setBounds(botonX, 520, botonAncho, 80);
        botonVolver.setFocusable(false);
        botonVolver.setBorderPainted(false);
        botonVolver.addActionListener(this);
        //panel en si
        background_image.add(titulo);
        background_image.add(labelUsuario);
        background_image.add(campoUsuario);
        background_image.add(labelContrasenia);
        background_image.add(campoContrasenia);
        background_image.add(botonMostrarContrasenia);
        background_image.add(botonCrear);
        background_image.add(botonVolver);

        this.add(background_image);
    }
    //Esto permite darle customizacion a  los campos de texto
    private void estilizarCampo(JTextField campo) {
        campo.setBackground(colorCampoFondo);
        campo.setForeground(colorCampoTexto);
        campo.setCaretColor(colorCampoTexto);
        campo.setOpaque(true);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(colorCampoBorde, 2),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);


        //permite mostrar la contrse;a
        if (e.getSource() == botonMostrarContrasenia) { //Si se presion mostrar contraseña
            contraseniaVisible = !contraseniaVisible;
            if (contraseniaVisible) {
                campoContrasenia.setEchoChar((char) 0);
                botonMostrarContrasenia.setText("Ocultar");
            } else {
                campoContrasenia.setEchoChar(echoCharOculto);
                botonMostrarContrasenia.setText("Ver");
            }
            return;
        }
        //regresa al menu principal
        if (e.getSource() == botonVolver) {
            frame.setContentPane(new MainMenu());
            frame.revalidate();
            frame.repaint();
            return;
        }

        if (e.getSource() == botonCrear) {

            String usuario = campoUsuario.getText().trim(); //trim elimina los espacios al principio y al final
            String contrasenia = new String(campoContrasenia.getPassword());

            if (usuario.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Escribe un usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (contrasenia.length() != 5) {
                JOptionPane.showMessageDialog(this, "La contraseña debe tener exactamente 5 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Jugador nuevo = new Jugador(usuario, contrasenia);
            boolean exito = Sesion.gestorJugadores.registrar(nuevo);

            if (!exito) {
                JOptionPane.showMessageDialog(this, "Ese usuario ya existe, elige otro.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //Si el registro termina con exito automaticamente esta iniciado sesion en el nuevo
            Sesion.jugadorActual = nuevo;

            frame.setContentPane(new MenuPrincipal());
            frame.revalidate();
            frame.repaint();
        }
    }
}
