package castlevania;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {


        System.setProperty("sun.awt.exception.handler", ManejadorExcepciones.class.getName());


        Thread.setDefaultUncaughtExceptionHandler((hilo, error) -> {
            error.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Ocurrió un error inesperado:\n" + error.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        });

        try {
            MyFrame ventana = new MyFrame();
            MainMenu mainmenu = new MainMenu();
            ventana.setContentPane(mainmenu);
            ventana.revalidate();
            ventana.repaint();
            ventana.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "No se pudo iniciar el programa:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
