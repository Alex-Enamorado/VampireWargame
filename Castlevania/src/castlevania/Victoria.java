package castlevania;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Victoria extends JPanel implements ActionListener {

    private JButton botonVolver = new JButton();

    private final Color colorTextoBotones = new Color(204, 0, 11);
    private final Color colorTitulo = new Color(169, 47, 67);
    private final Color colorTexto = new Color(235, 220, 210);

    //Pantalla que se muestra al terminar la partida
    public Victoria(String mensaje) {
        this.setLayout(new BorderLayout());

        ImageIcon background = new ImageIcon(Main.class.getResource("/resources/mainmenu_background1.png"));
        JLabel background_image = new JLabel();
        background_image.setLayout(null);
        background_image.setIcon(background);

        JLabel titulo = new JLabel("¡Partida Terminada!");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(colorTitulo);
        titulo.setFont(new Font("Angel wish", Font.BOLD, 60));
        titulo.setBounds(250, 300, 1100, 90);

        JLabel texto = new JLabel("<html><center>" + mensaje + "</center></html>");
        texto.setHorizontalAlignment(SwingConstants.CENTER);
        texto.setForeground(colorTexto);
        texto.setFont(new Font("Practical", Font.PLAIN, 12));
        texto.setBounds(250, 420, 1100, 100);

        botonVolver.setText("Volver al Menú");
        botonVolver.setContentAreaFilled(false);
        botonVolver.setForeground(colorTextoBotones);
        botonVolver.setFont(new Font("Angel wish", Font.BOLD, 30));
        botonVolver.setBounds(690, 560, 220, 60);
        botonVolver.setFocusable(false);
        botonVolver.setBorderPainted(false);
        botonVolver.addActionListener(this);

        background_image.add(titulo);
        background_image.add(texto);
        background_image.add(botonVolver);

        this.add(background_image);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

        if (e.getSource() == botonVolver) {
            frame.setContentPane(new MenuPrincipal());
            frame.revalidate();
            frame.repaint();
        }
    }
}
