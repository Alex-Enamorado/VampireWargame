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

    private Pieza piezaSeleccionada;
    private int filaSeleccionada = -1;
    private int columnaSeleccionada = -1;


    private JButton[][] pos = new JButton[6][6];
    private Pieza[][] tablero = new Pieza[6][6];

    JPanel grid = new JPanel();
    int cont = 1;
    private Ruleta ruleta=new Ruleta();
    JButton girar = new JButton();


    public Tablero() {
        this.setLayout(null);
        this.setBackground(new Color(40, 39, 39));
        grid.setLayout(new GridLayout(6, 6));

        hacerCuadricula();
        colocarPieza();
        hacerRuleta();
        mostrarPiezas();

        grid.setBounds(350, 5, 852, 852);
        this.add(grid);

    }





    private void hacerCuadricula(){
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

                pos[i][j].addActionListener(this);
                grid.add(pos[i][j]);
            }
        }

    }

    private void hacerRuleta(){
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
    }


    private void colocarPieza(){
        //Jugador arriba
        tablero[0][0] = new HombreLobo(2,0,0);
        tablero[0][1] = new Vampiro(2,0,1);
        tablero[0][2] = new Necromante(2,0,2);
        tablero[0][3] = new Necromante(2,0,3);
        tablero[0][4] = new Vampiro(2,0,4);
        tablero[0][5] = new HombreLobo(2,0,5);
        //Jugador abajo
        tablero[5][0] = new HombreLobo(1,5,0);
        tablero[5][1] = new Vampiro(1,5,1);
        tablero[5][2] = new Necromante(1,5,2);
        tablero[5][3] = new Necromante(1,5,3);
        tablero[5][4] = new Vampiro(1,5,4);
        tablero[5][5] = new HombreLobo(1,5,5);



    }

    private void mostrarPiezas(){
        for (int i=0;i<filas;i++){
            for (int j=0;j<columnas;j++){
                if (tablero[i][j] == null) {
                    pos[i][j].setIcon(null);
                    continue;
            }


                switch (tablero[i][j].getTipo()) {

                    case hombre_lobo:

                        if (tablero[i][j].getDuenio() == 1) {
                            pos[i][j].setIcon(new ImageIcon(
                                    Main.class.getResource("/resources/lobo.png")));
                        } else {
                            pos[i][j].setIcon(new ImageIcon(
                                    Main.class.getResource("/resources/lobo.png")));
                        }

                        break;

                    case vampiro:

                        if (tablero[i][j].getDuenio() == 1) {
                            pos[i][j].setIcon(new ImageIcon(
                                    Main.class.getResource("/resources/vampiro.png")));
                        } else {
                            pos[i][j].setIcon(new ImageIcon(
                                    Main.class.getResource("/resources/vampiro.png")));
                        }

                        break;

                    case necromante:

                        if (tablero[i][j].getDuenio() == 1) {
                            pos[i][j].setIcon(new ImageIcon(
                                    Main.class.getResource("/resources/necromancer.png")));
                        } else {
                            pos[i][j].setIcon(new ImageIcon(
                                    Main.class.getResource("/resources/necromancer.png")));
                        }

                        break;


                }
            }


        }


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==girar){
                System.out.println("Girando");
                ruleta.girar();
        }

        for (int i = 0; i < filas; i++) {

            for (int j = 0; j < columnas; j++) {

                if (e.getSource() == pos[i][j]) {

                    // No hay pieza seleccionada
                    if (piezaSeleccionada == null) {

                        if (tablero[i][j] == null) {
                            System.out.println("Casilla vacía");
                            return;
                        }

                        piezaSeleccionada = tablero[i][j];
                        filaSeleccionada = i;
                        columnaSeleccionada = j;

                        System.out.println("Pieza seleccionada");
                        System.out.println("Tipo: " + piezaSeleccionada.getTipo());
                        System.out.println("Fila: " + filaSeleccionada);
                        System.out.println("Columna: " + columnaSeleccionada);

                        return;
                    }

                    // Ya hay una pieza seleccionada

                    if (tablero[i][j] != null &&
                            tablero[i][j].getDuenio() == piezaSeleccionada.getDuenio()) {

                        System.out.println("No puedes seleccionar otra pieza.");
                        return;
                    }

                    System.out.println("Intentar mover o atacar.");

                    return;
                }
            }

        }
    }
}
