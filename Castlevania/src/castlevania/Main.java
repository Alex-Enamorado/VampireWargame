package castlevania;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        MyFrame ventana = new MyFrame();
        MainMenu mainmenu = new MainMenu();
        Tablero tablero = new Tablero();

//        ventana.add(mainmenu);
//        ventana.add(tablero);

        ventana.setContentPane(mainmenu);
        ventana.revalidate();
        ventana.repaint();
        ventana.setVisible(true);

    }
}
