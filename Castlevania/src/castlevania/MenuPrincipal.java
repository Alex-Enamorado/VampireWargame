package castlevania;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JPanel implements ActionListener {

    private JButton jugar = new JButton();
    private JButton miCuenta = new JButton();
    private JButton ranking = new JButton();
    private JButton historial = new JButton();
    private JButton cerrarSesion = new JButton();

    private final Color colorTextoBotones = new Color(204, 0, 11);
    private final Color colorTitulo = new Color(169, 47, 67);

    public MenuPrincipal() {
        this.setLayout(new BorderLayout());

        ImageIcon background = new ImageIcon(Main.class.getResource("/resources/mainmenu_background1.png"));
        JLabel background_image = new JLabel();
        background_image.setLayout(null);
        background_image.setIcon(background);

        int boxWidth = 560;
        int boxX = (1600 - boxWidth) / 2;

        String nombre = (Sesion.jugadorActual != null) ? Sesion.jugadorActual.getUsuario() : "";

        JLabel titulo = new JLabel("Bienvenido, " + nombre);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(colorTitulo);
        titulo.setFont(new Font("Angel wish", Font.BOLD, 48));
        titulo.setBounds(boxX, 100, boxWidth, 80);

        int botonAncho = 400;
        int botonX = boxX + (boxWidth - botonAncho) / 2;
        int y = 230;
        int paso = 90;

        configurarBoton(jugar, "Jugar Vampire Wargame", botonX, y);
        y += paso;
        configurarBoton(miCuenta, "Mi Cuenta", botonX, y);
        y += paso;
        configurarBoton(ranking, "Ranking de Jugadores", botonX, y);
        y += paso;
        configurarBoton(historial, "Mi Historial de Juegos", botonX, y);
        y += paso;
        configurarBoton(cerrarSesion, "Cerrar Sesión", botonX, y);

        background_image.add(titulo);
        background_image.add(jugar);
        background_image.add(miCuenta);
        background_image.add(ranking);
        background_image.add(historial);
        background_image.add(cerrarSesion);

        this.add(background_image);
    }

    private void configurarBoton(JButton boton, String texto, int x, int y) {
        boton.setText(texto);
        boton.setContentAreaFilled(false);
        boton.setForeground(colorTextoBotones);
        boton.setFont(new Font("Angel wish", Font.BOLD, 30));
        boton.setBounds(x, y, 400, 70);
        boton.setFocusable(false);
        boton.setBorderPainted(false);
        boton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

        if (e.getSource() == jugar) {
            iniciarNuevaPartida(frame);
            return;
        }

        if (e.getSource() == miCuenta) {
            frame.setContentPane(new MiCuenta());
            frame.revalidate();
            frame.repaint();
            return;
        }

        if (e.getSource() == ranking) {
            frame.setContentPane(new RankingJugadores());
            frame.revalidate();
            frame.repaint();
            return;
        }

        if (e.getSource() == historial) {
            frame.setContentPane(new HistorialJuegos());
            frame.revalidate();
            frame.repaint();
            return;
        }

        if (e.getSource() == cerrarSesion) {
            Sesion.cerrarSesion();
            frame.setContentPane(new MainMenu());
            frame.revalidate();
            frame.repaint();
        }
    }

    private void iniciarNuevaPartida(JFrame frame) {

        //Buscamos oponentes disponibles
        Jugador[] todos = Sesion.gestorJugadores.getJugadores();
        int cantidad = Sesion.gestorJugadores.getCantidad();

        String[] oponentes = new String[cantidad];
        int total = 0;

        for (int i = 0; i < cantidad; i++) {
            Jugador j = todos[i];
            if (j.isActivo() && !j.getUsuario().equalsIgnoreCase(Sesion.jugadorActual.getUsuario())) {
                oponentes[total] = j.getUsuario();
                total++;
            }
        }

        if (total == 0) {
            JOptionPane.showMessageDialog(this,
                    "No hay otro jugador registrado para jugar en contra.",
                    "Sin oponentes", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] listaFinal = new String[total];
        for (int i = 0; i < total; i++) {
            listaFinal[i] = oponentes[i];
        }

        String oponenteElegido = (String) JOptionPane.showInputDialog(
                this,
                "Elige tu oponente:",
                "Nueva Partida",
                JOptionPane.PLAIN_MESSAGE,
                null,
                listaFinal,
                listaFinal[0]
        );

        if (oponenteElegido == null) {
            return; //el jugador canceló
        }

        Tablero tablero = new Tablero(Sesion.jugadorActual.getUsuario(), oponenteElegido);
        frame.setContentPane(tablero);
        frame.revalidate();
        frame.repaint();
    }
}
