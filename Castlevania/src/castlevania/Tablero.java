package castlevania;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Tablero extends JPanel implements ActionListener {
    private static final int columnas=6;
    private static final int filas=6;


    private static JButton[][] pos = new JButton[6][6];

    JPanel grid = new JPanel();
    int cont = 1;
    private Ruleta ruleta=new Ruleta();
    JButton girar = new JButton();


    public Tablero() {


        this.setLayout(null);
        this.setBackground(new Color(40, 39, 39));

        grid.setLayout(new GridLayout(6, 6));


        
        //Colocar las piezas.
        for (int i = 0; i < filas; i++) {
            for (int j=0; j < columnas;j++) {

                pos[i][j] = new JButton();
                pos[i][j].setPreferredSize(new Dimension(10, 10));

                if ((i+j) % 2 == 0) {
//                pos[i].setBackground(new Color(169, 47, 67));
                    pos[i][j].setBackground(new Color(98, 24, 21));

                } else {
//                pos[i].setBackground(new Color(39, 34, 40));
                    pos[i][j].setBackground(new Color(29, 29, 28));

                }

//                if ((j%2==0)){
//                    cont+=2;
//                }
//
//                cont++;


                grid.add(pos[i][j]);
            }
        }


        girar.setBounds(25,150,300,300);
        girar.addActionListener(this);
        girar.setContentAreaFilled(false);
        girar.setBorderPainted(false);
        girar.setFocusable(false);


        ruleta= new Ruleta();
        ruleta.setBounds(25,150,300,300);



        ImageIcon selector = new ImageIcon(Main.class.getResource("/resources/selector.png"));
//        System.out.println("Ancho selector: " + selector.getIconWidth());
        JLabel marcador=new JLabel(selector);
        marcador.setBounds(20,0,300,300);
        marcador.setOpaque(false);



        this.add(marcador);
        this.add(ruleta);
        this.add(girar);






        grid.setBounds(350, 5, 852, 852);
        this.add(grid);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==girar){
                System.out.println("Girando");
                ruleta.girar();
        }

    }
}
