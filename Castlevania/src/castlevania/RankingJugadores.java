package castlevania;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RankingJugadores extends JPanel implements ActionListener {

    private JButton volver = new JButton();
    private JTextArea texto = new JTextArea();

    public RankingJugadores() {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(39, 34, 40));

        JLabel titulo = new JLabel("Ranking de Jugadores", SwingConstants.CENTER);
        titulo.setForeground(new Color(169, 47, 67));
        titulo.setFont(new Font("Old English Text MT", Font.BOLD, 48));
        titulo.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));

        texto.setEditable(false);
        texto.setFont(new Font("Serif", Font.PLAIN, 20));
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

        volver.setText("Volver");
        volver.setContentAreaFilled(false);
        volver.setForeground(new Color(204, 0, 11));
        volver.setFont(new Font("Old English Text MT", Font.BOLD, 30));
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
        Jugador[] ordenados = Sesion.gestorJugadores.ordenarPorPuntos();

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-6s %-25s %s\n", "Pos.", "Usuario", "Puntos"));
        sb.append("--------------------------------------------\n");

        int posicion = 0;

        for (int i = 0; i < ordenados.length; i++) {
            Jugador j = ordenados[i];

            if (!j.isActivo()) {
                continue; //solo se muestran jugadores activos
            }

            posicion++;
            sb.append(String.format("%-6d %-25s %d\n", posicion, j.getUsuario(), j.getPuntos()));
        }

        if (posicion == 0) {
            sb.append("Todavía no hay jugadores registrados.\n");
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
