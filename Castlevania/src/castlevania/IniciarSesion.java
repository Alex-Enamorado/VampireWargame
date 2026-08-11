package castlevania;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IniciarSesion extends JPanel implements ActionListener {

    private JTextField campoUsuario = new JTextField();
    private JPasswordField campoContrasenia = new JPasswordField();
    private JButton botonEntrar = new JButton();
    private JButton botonVolver = new JButton();
    private JButton botonMostrarContrasenia = new JButton();

    // Guardamos el caracter original de "oculto" para poder restaurarlo
    private final char echoCharOculto;
    private boolean contraseniaVisible = false;

    // Colores del tema (mismos tonos que el resto de la app)
    private final Color colorTextoBotones = new Color(204, 0, 11);
    private final Color colorTitulo = new Color(169, 47, 67);
    private final Color colorCampoFondo = new Color(30, 18, 20);      // fondo oscuro tipo "pergamino gótico"
    private final Color colorCampoTexto = new Color(235, 220, 210);   // texto claro para contraste
    private final Color colorCampoBorde = new Color(169, 47, 67);     // borde rojo/vino a juego con el título

    public IniciarSesion() {
        this.setLayout(new BorderLayout());

        ImageIcon background = new ImageIcon(Main.class.getResource("/resources/mainmenu_background1.png"));
        JLabel background_image = new JLabel();
        background_image.setLayout(null);
        background_image.setIcon(background);

        // ===== Bloque centrado =====
        // La ventana (MyFrame) mide 1600 x 900, así que centramos un "recuadro"
        // lógico de 560 px de ancho horizontalmente.
        int boxWidth = 560;
        int boxX = (1600 - boxWidth) / 2;

        // Guardamos el echoChar por defecto (el que usa Swing para ocultar la contraseña)
        echoCharOculto = campoContrasenia.getEchoChar();

        // Titulo (centrado dentro del recuadro)
        JLabel titulo = new JLabel("Iniciar Sesión");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(colorTitulo);
        titulo.setFont(new Font("Old English Text MT", Font.BOLD, 70));
        titulo.setBounds(boxX, 150, boxWidth, 90);

        // Etiquetas
        JLabel labelUsuario = new JLabel("Usuario:");
        labelUsuario.setForeground(colorTextoBotones);
        labelUsuario.setFont(new Font("Old English Text MT", Font.BOLD, 28));
        labelUsuario.setBounds(boxX, 280, 150, 40);

        JLabel labelContrasenia = new JLabel("Contraseña:");
        labelContrasenia.setForeground(colorTextoBotones);
        labelContrasenia.setFont(new Font("Old English Text MT", Font.BOLD, 28));
        labelContrasenia.setBounds(boxX, 350, 150, 40);

        // Campos
        campoUsuario.setBounds(boxX + 160, 280, 360, 35);
        campoUsuario.setFont(new Font("Serif", Font.PLAIN, 18));
        estilizarCampo(campoUsuario);

        campoContrasenia.setBounds(boxX + 160, 350, 280, 35);
        campoContrasenia.setFont(new Font("Serif", Font.PLAIN, 18));
        estilizarCampo(campoContrasenia);

        // Boton para mostrar/ocultar la contraseña
        botonMostrarContrasenia.setText("Ver");
        botonMostrarContrasenia.setContentAreaFilled(false);
        botonMostrarContrasenia.setForeground(colorTextoBotones);
        botonMostrarContrasenia.setFont(new Font("Serif", Font.BOLD, 14));
        botonMostrarContrasenia.setBounds(boxX + 450, 350, 90, 35);
        botonMostrarContrasenia.setFocusable(false);
        botonMostrarContrasenia.setBorderPainted(true);
        botonMostrarContrasenia.setBorder(BorderFactory.createLineBorder(colorCampoBorde, 1));
        botonMostrarContrasenia.addActionListener(this);

        // Botones (mismo estilo que MainMenu, ahora centrados en el recuadro)
        int botonAncho = 300;
        int botonX = boxX + (boxWidth - botonAncho) / 2;

        botonEntrar.setContentAreaFilled(false);
        botonEntrar.setText("Entrar");
        botonEntrar.setForeground(colorTextoBotones);
        botonEntrar.setFont(new Font("Old English Text MT", Font.BOLD, 38));
        botonEntrar.setBounds(botonX, 430, botonAncho, 80);
        botonEntrar.setFocusable(false);
        botonEntrar.setBorderPainted(false);
        botonEntrar.addActionListener(this);

        botonVolver.setContentAreaFilled(false);
        botonVolver.setText("Volver");
        botonVolver.setForeground(colorTextoBotones);
        botonVolver.setFont(new Font("Old English Text MT", Font.BOLD, 38));
        botonVolver.setBounds(botonX, 520, botonAncho, 80);
        botonVolver.setFocusable(false);
        botonVolver.setBorderPainted(false);
        botonVolver.addActionListener(this);

        background_image.add(titulo);
        background_image.add(labelUsuario);
        background_image.add(campoUsuario);
        background_image.add(labelContrasenia);
        background_image.add(campoContrasenia);
        background_image.add(botonMostrarContrasenia);
        background_image.add(botonEntrar);
        background_image.add(botonVolver);

        this.add(background_image);
    }

    /**
     * Aplica el estilo visual (fondo, texto, borde) a un campo de texto
     * para que combine con el tema gótico de la aplicación.
     */
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

        if (e.getSource() == botonMostrarContrasenia) {
            contraseniaVisible = !contraseniaVisible;
            if (contraseniaVisible) {
                campoContrasenia.setEchoChar((char) 0); // 0 = muestra el texto tal cual
                botonMostrarContrasenia.setText("Ocultar");
            } else {
                campoContrasenia.setEchoChar(echoCharOculto);
                botonMostrarContrasenia.setText("Ver");
            }
            return;
        }

        if (e.getSource() == botonVolver) {
            frame.setContentPane(new MainMenu());
            frame.revalidate();
            frame.repaint();
            return;
        }

        if (e.getSource() == botonEntrar) {

//            String usuario = campoUsuario.getText().trim();
//            String contrasenia = new String(campoContrasenia.getPassword());
//
//            Jugador jugador = Sesion.gestorJugadores.validarLogin(usuario, contrasenia);
//
//            if (jugador == null) {
//                JOptionPane.showMessageDialog(this,
//                        "Usuario o contraseña incorrectos.",
//                        "Error", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//
//            Sesion.jugadorActual = jugador;
//            frame.setContentPane(new MenuPrincipal());
//            frame.revalidate();
//            frame.repaint();
        }
    }


}