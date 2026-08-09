package castlevania;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Tablero extends JPanel implements ActionListener {
    private static final int columnas=6;
    private static final int filas=6;

    private Pieza piezaSeleccionada;
    private int filaSeleccionada = -1;
    private int columnaSeleccionada = -1;
    private int jugadorActual = 1;
    private int piezasPerdidasJ1 = 0;
    private int piezasPerdidasJ2 = 0;
    private int girosUsados = 0;
    private TipoPieza tipoPermitido = null;

    private JTextArea logArea = new JTextArea();
    private JScrollPane logScroll = new JScrollPane(logArea);


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
        actualizarRuletaDisponibilidad();
        hacerPanelLog();
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
        ruleta.setOnResultado(this::procesarResultadoRuleta);
        marcador.setOpaque(false);

        this.add(marcador);
        this.add(ruleta);
        this.add(girar);
    }

    private void actualizarRuletaDisponibilidad() {
        boolean hayLobo = false;
        boolean hayVampiro = false;
        boolean hayNecro = false;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Pieza p = tablero[i][j];

                if (p == null || p.getDuenio() != jugadorActual) {
                    continue;
                }

                if (p.getTipo() == TipoPieza.hombre_lobo) {
                    hayLobo = true;
                }
                if (p.getTipo() == TipoPieza.vampiro) {
                    hayVampiro = true;
                }
                if (p.getTipo() == TipoPieza.necromante) {
                    hayNecro = true;
                }
            }
        }

        ruleta.actualizarDisponible(hayLobo, hayVampiro, hayNecro);
    }


    private void registrarLog(String mensaje) {
        logArea.append(mensaje + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }


    private void hacerPanelLog() {
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setBackground(new Color(29, 29, 28));
        logArea.setForeground(Color.WHITE);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        logScroll.setBounds(1220, 5, 360, 852);
        logScroll.setBorder(BorderFactory.createLineBorder(new Color(98, 24, 21), 2));

        this.add(logScroll);
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


                    case zombie:

                        if (tablero[i][j].getDuenio() == 1) {
                            pos[i][j].setIcon(new ImageIcon(
                                    Main.class.getResource("/resources/zombie.png")));
                        } else {
                            pos[i][j].setIcon(new ImageIcon(
                                    Main.class.getResource("/resources/zombie.png")));
                        }

                        break;


                }
            }


        }


    }


    private void procesarResultadoRuleta(TipoPieza resultado) {
        girosUsados++;
        System.out.println("Resultado ruleta: " + resultado);
        System.out.println("Giros usados: " + girosUsados);

        if (jugadorTienePieza(jugadorActual, resultado)) {
            tipoPermitido = resultado;
            JOptionPane.showMessageDialog(this, "Debes mover: " + resultado);
            girar.setEnabled(false);
            return;
        }

        int girosPermitidos = calcularGirosPermitidos(jugadorActual);

        if (girosUsados < girosPermitidos) {
            JOptionPane.showMessageDialog(this,
                    "Ya no tienes piezas de " + resultado + ". Gira de nuevo.");
            ruleta.girar();


            if (jugadorTienePieza(jugadorActual, resultado)) {
                tipoPermitido = resultado;
                JOptionPane.showMessageDialog(this, "Debes mover: " + resultado);
                registrarLog("Jugador " + jugadorActual + " debe mover: " + resultado);
                girar.setEnabled(false);
                return;
            }


            return;
        }

        JOptionPane.showMessageDialog(this, "Sin piezas disponibles. Pierdes el turno.");
        girar.setEnabled(false);
        pasarTurno();
    }

    private boolean jugadorTienePieza(int duenio, TipoPieza tipo) {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Pieza p = tablero[i][j];

                if (p != null && p.getDuenio() == duenio && p.getTipo() == tipo) {
                    return true;
                }
            }
        }
        return false;
    }

    private int calcularGirosPermitidos(int duenio) {
        int perdidas;

        if (duenio == 1) {
            perdidas = piezasPerdidasJ1;
        } else {
            perdidas = piezasPerdidasJ2;
        }

        if (perdidas >= 4) {
            return 3;
        }

        if (perdidas >= 2) {
            return 2;
        }

        return 1;
    }

    private void pasarTurno() {
        if (jugadorActual == 1) {
            jugadorActual = 2;
        } else {
            jugadorActual = 1;
        }

        girosUsados = 0;
        tipoPermitido = null;
        girar.setEnabled(true);
        System.out.println("Turno de jugador: " + jugadorActual);
        registrarLog("--- Turno de jugador " + jugadorActual + " ---");
    }



    private void intentarMover(int filaDestino, int columnaDestino) {

        if (piezaSeleccionada.getTipo() == TipoPieza.necromante) {
            int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Invocar Zombie aquí? (No = mover el Necrómante)",
                    "Necrómante", JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                invocarZombie(filaDestino, columnaDestino);
                finalizarAccion();
                return;
            }
        }

        if (!piezaSeleccionada.puedeMover(filaDestino, columnaDestino)) {
            System.out.println("Movimiento inválido");
            return;
        }

        tablero[filaSeleccionada][columnaSeleccionada] = null;
        tablero[filaDestino][columnaDestino] = piezaSeleccionada;
        piezaSeleccionada.setPosicion(filaDestino, columnaDestino);

        registrarLog("Jugador " + jugadorActual + " movió " + piezaSeleccionada.getTipo() +
                " a (" + filaDestino + "," + columnaDestino + ")");

        mostrarPiezas();
        finalizarAccion();
    }


    private void invocarZombie(int fila, int columna) {
        int duenio = piezaSeleccionada.getDuenio();
        tablero[fila][columna] = new Zombie(duenio, fila, columna);
        mostrarPiezas();
        registrarLog("Jugador " + duenio + " invocó un Zombie en (" + fila + "," + columna + ")");
        System.out.println("Zombie invocado en " + fila + "," + columna);
    }


    private void intentarAtacar(int filaDestino, int columnaDestino) {
        Pieza objetivo = tablero[filaDestino][columnaDestino];

        int Dfila = Math.abs(filaDestino - filaSeleccionada);
        int Dcolumna = Math.abs(columnaDestino - columnaSeleccionada);

        boolean esAdyacente = Dfila <= 1 && Dcolumna <= 1;

        if (!esAdyacente) {
            System.out.println("Esa pieza no está adyacente, no puedes atacar así");
            return;
        }

        objetivo.recibirDanio(piezaSeleccionada.getAtaque(), false);

        if (objetivo.estaViva()) {
            JOptionPane.showMessageDialog(this,
                    "Se atacó la pieza " + objetivo.getTipo() +
                            "; le quedan " + objetivo.getEscudo() + " de escudo y " +
                            objetivo.getVida() + " de vida");
            registrarLog("Jugador " + jugadorActual + " atacó " + objetivo.getTipo() +
                    " (queda " + objetivo.getEscudo() + " escudo, " + objetivo.getVida() + " vida)");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Se destruyó la pieza " + objetivo.getTipo());
            registrarLog("Se destruyó " + objetivo.getTipo() + " del jugador " + objetivo.getDuenio());

            tablero[filaDestino][columnaDestino] = null;
            registrarPiezaPerdida(objetivo.getDuenio());
        }

        mostrarPiezas();
        finalizarAccion();
    }

    private void registrarPiezaPerdida(int duenio) {
        if (duenio == 1) {
            piezasPerdidasJ1++;
        } else {
            piezasPerdidasJ2++;
        }
        System.out.println("Piezas perdidas J1: " + piezasPerdidasJ1);
        System.out.println("Piezas perdidas J2: " + piezasPerdidasJ2);
    }

    private void finalizarAccion() {
        piezaSeleccionada = null;
        filaSeleccionada = -1;
        columnaSeleccionada = -1;
        pasarTurno();
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

                    if (piezaSeleccionada == null) {

                        if (tablero[i][j] == null) {
                            System.out.println("Casilla vacía");
                            return;
                        }

                        if (tablero[i][j].getDuenio() != jugadorActual) {
                            System.out.println("Esa pieza no es tuya");
                            return;
                        }

                        if (tipoPermitido == null) {
                            System.out.println("Primero gira la ruleta");
                            return;
                        }

                        if (tablero[i][j].getTipo() != tipoPermitido) {
                            System.out.println("Debes mover: " + tipoPermitido);
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



                    if (tablero[i][j] == null) {
                        intentarMover(i, j);
                    } else {
                        intentarAtacar(i, j);
                    }

                    return;


                }
            }

        }
    }
}
