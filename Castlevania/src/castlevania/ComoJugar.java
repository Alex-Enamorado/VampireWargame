package castlevania;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ComoJugar extends JPanel implements ActionListener {

    private JButton botonContinuar = new JButton();

    private final Color colorTextoBotones = new Color(204, 0, 11);
    private final Color colorTitulo = new Color(169, 47, 67);
    private final Color colorTexto = new Color(235, 220, 210);

    private final String jugador;
    private final String oponente;

    //Pantalla de como jugar, se muestra antes de entrar al tablero
    public ComoJugar(String jugador, String oponente) {
        this.jugador = jugador;
        this.oponente = oponente;

        this.setLayout(new BorderLayout());

        ImageIcon background = new ImageIcon(Main.class.getResource("/resources/mainmenu_background1.png"));
        JLabel background_image = new JLabel();
        background_image.setLayout(null);
        background_image.setIcon(background);

        int boxWidth = 1100;
        int boxX = (1600 - boxWidth) / 2;

        JLabel titulo = new JLabel("Cómo Jugar");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(colorTitulo);
        titulo.setFont(new Font("Angel wish", Font.BOLD, 50));
        titulo.setBounds(boxX, 30, boxWidth, 60);

        int filaY = 110;
        agregarPieza(background_image, "/resources/vampiro_blanco.png",
                "Vampiro",
                "Escudo 5, Vida 4, Ataque 3. Se mueve una casilla en cualquier dirección. Puede absorber sangre: al atacar drena 1 de vida al enemigo y se cura 1 de vida.",
                boxX, filaY);

        filaY += 140;
        agregarPieza(background_image, "/resources/lobo_blanco.png",
                "Hombre Lobo",
                "Escudo 2, Vida 5, Ataque 5. Se mueve en línea recta (horizontal, vertical o diagonal) hasta 2 casillas, sin poder saltar piezas en el camino.",
                boxX, filaY);

        filaY += 140;
        agregarPieza(background_image, "/resources/necromante_blanco.png",
                "Necrómante",
                "Escudo 1, Vida 3, Ataque 4. Se mueve una casilla en cualquier dirección. Puede lanzar un ataque a distancia de hasta 2 casillas que ignora el escudo, invocar un zombie en cualquier parte del tablero (útil como distracción u obstáculo) y ordenar ataques a través de sus zombies.",
                boxX, filaY);

        filaY += 140;
        agregarPieza(background_image, "/resources/zombie_blanco.png",
                "Zombie",
                "Escudo 0, Vida 1, Ataque 1. No puede moverse por sí mismo. Solo aparece invocado por un Necrómante. Sirve como obstáculo o para sus ataques a distancia.",
                boxX, filaY);

        int botonAncho = 220;
        int botonAlto = 60;
        botonContinuar.setText("Continuar");
        botonContinuar.setContentAreaFilled(false);
        botonContinuar.setForeground(colorTextoBotones);
        botonContinuar.setFont(new Font("Angel wish", Font.BOLD, 30));
        botonContinuar.setBounds(boxX + boxWidth - botonAncho, filaY + 100, botonAncho, botonAlto);
        botonContinuar.setFocusable(false);
        botonContinuar.setBorderPainted(false);
        botonContinuar.addActionListener(this);

        background_image.add(titulo);
        background_image.add(botonContinuar);

        this.add(background_image);
    }

    private void agregarPieza(JLabel contenedor, String rutaImagen, String nombre, String descripcion, int x, int y) {
        ImageIcon icono = new ImageIcon(Main.class.getResource(rutaImagen));
        Image imagenEscalada = icono.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);

        JLabel imagen = new JLabel(new ImageIcon(imagenEscalada));
        imagen.setBounds(x, y, 100, 100);

        JLabel nombreLabel = new JLabel(nombre);
        nombreLabel.setForeground(colorTitulo);
        nombreLabel.setFont(new Font("Angel wish", Font.BOLD, 26));
        nombreLabel.setBounds(x + 120, y, 400, 35);

        JTextArea descripcionArea = new JTextArea(descripcion);
        descripcionArea.setForeground(colorTexto);
        descripcionArea.setFont(new Font("Blackletter Shadow", Font.PLAIN, 16));
        descripcionArea.setLineWrap(true);
        descripcionArea.setWrapStyleWord(true);
        descripcionArea.setOpaque(false);
        descripcionArea.setEditable(false);
        descripcionArea.setFocusable(false);
        descripcionArea.setBounds(x + 120, y + 40, 960, 90);

        contenedor.add(imagen);
        contenedor.add(nombreLabel);
        contenedor.add(descripcionArea);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

        if (e.getSource() == botonContinuar) {
            Tablero tablero = new Tablero(jugador, oponente);
            frame.setContentPane(tablero);
            frame.revalidate();
            frame.repaint();
        }
    }
}
