package castlevania;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {

        System.setProperty("sun.awt.exception.handler", ManejadorExcepciones.class.getName());



        try {
            Font fuenteMain = Font.createFont(Font.TRUETYPE_FONT, Main.class.getResourceAsStream("/resources/fonts/main.ttf"));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fuenteMain);
        } catch (Exception e) {
        }

        try {
            Font fuentePixel = Font.createFont(Font.TRUETYPE_FONT, Main.class.getResourceAsStream("/resources/fonts/pixel.ttf"));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fuentePixel);
        } catch (Exception e) {
        }



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
