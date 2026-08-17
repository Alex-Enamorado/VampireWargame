package castlevania;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MiCuenta extends JPanel implements ActionListener {

    private JButton cambiarContrasenia = new JButton();
    private JButton cerrarCuenta = new JButton();
    private JButton volver = new JButton();

    private final Color colorTextoBotones = new Color(204, 0, 11);
    private final Color colorTitulo = new Color(169, 47, 67);



    public MiCuenta() {
        this.setLayout(new BorderLayout());

        ImageIcon background = new ImageIcon(Main.class.getResource("/resources/mainmenu_background1.png"));
        JLabel background_image = new JLabel();
        background_image.setLayout(null);
        background_image.setIcon(background);



        Jugador j = Sesion.jugadorActual;

        JLabel titulo = new JLabel("Mi Cuenta");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setForeground(colorTitulo);
        titulo.setFont(new Font("Angel wish", Font.BOLD, 60));
        titulo.setBounds(520, 120, 560, 90);




        //Se utiliza un poco de html para poder hacer que los datos esten en mas de una linea
        JLabel info = new JLabel(
                "<html>Usuario: " + j.getUsuario() + "<br>"
                        + "Puntos: " + j.getPuntos() + "<br>"
                        + "Fecha de ingreso: " + j.getFechaIngreso() + "</html>"
        );
        info.setHorizontalAlignment(SwingConstants.CENTER);
        info.setForeground(Color.WHITE);
        info.setFont(new Font("Practical", Font.PLAIN, 10));
        info.setBounds(520, 230, 560, 100);

        configurarBoton(cambiarContrasenia, "Cambiar Contraseña", 650, 360);
        configurarBoton(cerrarCuenta, "Cerrar Cuenta", 650, 450);
        configurarBoton(volver, "Volver", 650, 540);

        background_image.add(titulo);
        background_image.add(info);
        background_image.add(cambiarContrasenia);
        background_image.add(cerrarCuenta);
        background_image.add(volver);

        this.add(background_image);
    }





    //Configuracion del boton
    private void configurarBoton(JButton boton, String texto, int x, int y) {
        boton.setText(texto);
        boton.setContentAreaFilled(false);
        boton.setForeground(colorTextoBotones);
        boton.setFont(new Font("Angel wish", Font.BOLD, 29));
        boton.setBounds(x, y, 300, 80);
        boton.setFocusable(false);
        boton.setBorderPainted(false);
        boton.addActionListener(this);
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

        if (e.getSource() == volver) {
            frame.setContentPane(new MenuPrincipal());
            frame.revalidate();
            frame.repaint();
            return;
        }

        if (e.getSource() == cambiarContrasenia) {

            String nueva = JOptionPane.showInputDialog(this, "Escribe tu nueva contraseña (5 caracteres):");

            if (nueva == null) {
                return; //cancelo
            }

            if (nueva.length() != 5) {
                JOptionPane.showMessageDialog(this, "La contraseña debe tener exactamente 5 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Sesion.jugadorActual.setContrasenia(nueva);
            JOptionPane.showMessageDialog(this, "Contraseña actualizada.");
            return;
        }

        if (e.getSource() == cerrarCuenta) {

            int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Seguro que quieres cerrar (desactivar) tu cuenta?",
                    "Cerrar cuenta", JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                Sesion.jugadorActual.setActivo(false);
                Sesion.cerrarSesion();
                frame.setContentPane(new MainMenu());
                frame.revalidate();
                frame.repaint();
            }
        }
    }
}
