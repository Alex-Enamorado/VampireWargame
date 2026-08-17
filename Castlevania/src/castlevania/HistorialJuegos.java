package castlevania;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HistorialJuegos extends JPanel implements ActionListener {

    private JButton volver = new JButton();
    private JTextArea texto = new JTextArea();

    public HistorialJuegos() {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(39, 34, 40));

        //Crea mi titulo
        JLabel titulo = new JLabel("Mi Historial de Juegos", SwingConstants.CENTER);
        titulo.setForeground(new Color(169, 47, 67));
        titulo.setFont(new Font("Angel wish", Font.BOLD, 48));
        titulo.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        //Area display el historial
        texto.setEditable(false);
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        texto.setFont(new Font("Practical", Font.PLAIN, 10));
        texto.setBackground(new Color(29, 29, 28));
        texto.setForeground(Color.WHITE);
        texto.setText(construirTexto());

        JScrollPane scroll = new JScrollPane(texto);
        scroll.setOpaque(true);
        scroll.setBackground(new Color(29, 29, 28));
        scroll.getViewport().setOpaque(true);
        scroll.getViewport().setBackground(new Color(29, 29, 28));
        scroll.setBorder(BorderFactory.createLineBorder(new Color(98, 24, 21), 2));

        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setOpaque(false);
        contenedor.setBorder(BorderFactory.createEmptyBorder(0, 200, 0, 200));
        contenedor.add(scroll, BorderLayout.CENTER);


        //Se customiza el boton de volver;
        volver.setText("Volver");
        volver.setContentAreaFilled(false);
        volver.setForeground(new Color(204, 0, 11));
        volver.setFont(new Font("Angel wish", Font.BOLD, 30));
        volver.setFocusable(false);
        volver.setBorderPainted(false);
        volver.addActionListener(this);

        JPanel abajo = new JPanel();
        abajo.setOpaque(false);
        abajo.add(volver);

        this.add(titulo, BorderLayout.NORTH);
        this.add(contenedor, BorderLayout.CENTER);
        this.add(abajo, BorderLayout.SOUTH);
    }

    private String construirTexto() {
        String usuario = Sesion.jugadorActual.getUsuario();
        StringBuilder sb = new StringBuilder();

        RegistroPartida[] registros = Sesion.historialPartidas.getRegistros();
        int cantidad = Sesion.historialPartidas.getCantidad();

        //Se recorre del mas reciente al mas antiguo
        boolean hayAlguno = false;

        for (int i = cantidad - 1; i >= 0; i--) {
            RegistroPartida r = registros[i];

            if (r.participo(usuario)) {
                sb.append(r.getFecha()).append("\n");
                sb.append(r.getMensaje()).append("\n");
                sb.append("--------------------------------------------\n");
                hayAlguno = true;
            }
        }

        if (!hayAlguno) {
            sb.append("Todavía no has jugado ninguna partida.\n");
        }

        return sb.toString();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == volver) {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setContentPane(new MenuPrincipal());
            frame.revalidate();
            frame.repaint();
        }
    }
}
