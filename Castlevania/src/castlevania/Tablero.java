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


    private int loboPerdidoJ1=0;
    private int vampiroPerdidoJ1=0;
    private int necromantePerdidoJ1=0;

    private int loboPerdidoJ2=0;
    private int vampiroPerdidoJ2=0;
    private int necromantePerdidoJ2=0;





    private int girosUsados = 0;

    //Nombres de los dos jugadores de esta partida (para mensajes finales y puntos)
    private String nombreJugador1 = "Jugador 1";
    private String nombreJugador2 = "Jugador 2";
    private boolean partidaTerminada = false;

    private TipoPieza tipoPermitido = null;
    JLabel indicadorJugador = new JLabel();
    private JTextArea logArea = new JTextArea();
    private JScrollPane logScroll = new JScrollPane(logArea);
    private JButton retirarse = new JButton();


    private JButton[][] pos = new JButton[6][6];
    private Pieza[][] tablero = new Pieza[6][6];

    JPanel grid = new JPanel();
    int cont = 1;
    private Ruleta ruleta=new Ruleta();
    JButton girar = new JButton();


    public Tablero() {
        this("Jugador 1", "Jugador 2");
    }

    public Tablero(String nombreJugador1, String nombreJugador2) {
        this.nombreJugador1 = nombreJugador1;
        this.nombreJugador2 = nombreJugador2;

        this.setLayout(null);
        this.setBackground(new Color(40, 39, 39));
        grid.setLayout(new GridLayout(6, 6));

        hacerCuadricula();
        colocarPieza();
        hacerRuleta();
        actualizarRuletaDisponibilidad();
        hacerPanelLog();
        hacerBotonRetirarse();
        mostrarPiezas();

        grid.setBounds(350, 5, 852, 852);
        this.add(grid);

    }

    private void hacerBotonRetirarse() {
        retirarse.setText("Retirarse");
        retirarse.setContentAreaFilled(false);
        retirarse.setForeground(new Color(204, 0, 11));
        retirarse.setFont(new Font("Old English Text MT", Font.BOLD, 22));
        retirarse.setBounds(25, 20, 300, 60);
        retirarse.setFocusable(false);
        retirarse.setBorderPainted(true);
        retirarse.setBorder(BorderFactory.createLineBorder(new Color(169, 47, 67), 2));
        retirarse.addActionListener(this);

        this.add(retirarse);
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

        int lobosPerdidos;
        int vampirosPerdidos;
        int necromantesPerdidos;

        if (jugadorActual == 1) {
            lobosPerdidos = loboPerdidoJ1;
            vampirosPerdidos = vampiroPerdidoJ1;
            necromantesPerdidos = necromantePerdidoJ1;
        } else {
            lobosPerdidos = loboPerdidoJ2;
            vampirosPerdidos = vampiroPerdidoJ2;
            necromantesPerdidos = necromantePerdidoJ2;
        }

        int lobosVivos = 2 - lobosPerdidos;
        int vampirosVivos = 2 - vampirosPerdidos;
        int necromantesVivos = 2 - necromantesPerdidos;

        ruleta.actualizarDisponible(
                lobosVivos,
                vampirosVivos,
                necromantesVivos
        );
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
                                    Main.class.getResource("/resources/lobo_blanco.png")));
                        } else {
                            pos[i][j].setIcon(new ImageIcon(
                                    Main.class.getResource("/resources/lobo_negro.png")));
                        }

                        break;

                    case vampiro:

                        if (tablero[i][j].getDuenio() == 1) {
                            pos[i][j].setIcon(new ImageIcon(
                                    Main.class.getResource("/resources/vampiro_blanco.png")));
                        } else {
                            pos[i][j].setIcon(new ImageIcon(
                                    Main.class.getResource("/resources/vampiro_negro.png")));
                        }

                        break;

                    case necromante:

                        if (tablero[i][j].getDuenio() == 1) {
                            pos[i][j].setIcon(new ImageIcon(
                                    Main.class.getResource("/resources/necromante_blanco.png")));
                        } else {
                            pos[i][j].setIcon(new ImageIcon(
                                    Main.class.getResource("/resources/necromante_negro.png")));
                        }

                        break;


                    case zombie:

                        if (tablero[i][j].getDuenio() == 1) {
                            pos[i][j].setIcon(new ImageIcon(
                                    Main.class.getResource("/resources/zombie_blanco.png")));
                        } else {
                            pos[i][j].setIcon(new ImageIcon(
                                    Main.class.getResource("/resources/zombie_negro.png")));
                        }

                        break;


                }
            }


        }


    }


    private void procesarResultadoRuleta(TipoPieza resultado) {

        if (resultado == null) {
            return;
        }

        girosUsados++;

        System.out.println("Resultado: " + resultado);
        System.out.println("Giros usados: " + girosUsados);

        // PRIMERO preguntamos si el sector donde cayó es GRIS
        if (ruleta.resultadoEsGris()) {

            int girosPermitidos = calcularGirosPermitidos(jugadorActual);

            System.out.println("Cayó en GRIS.");
            System.out.println("Giros permitidos: " + girosPermitidos);
            System.out.println("Giros usados: " + girosUsados);

            // Todavía puede volver a tirar
            if (girosUsados < girosPermitidos) {

                JOptionPane.showMessageDialog(
                        this,
                        "Cayó en una pieza eliminada.\n" +
                                "Puedes girar nuevamente."
                );

                girar.setEnabled(true);
                return;
            }

            // Ya no tiene más oportunidades
            JOptionPane.showMessageDialog(
                    this,
                    "Cayó en una pieza eliminada.\n" +
                            "Ya no tienes más giros.\n" +
                            "Pierdes el turno."
            );

            girar.setEnabled(false);
            pasarTurno();
            return;
        }

        // SI NO ES GRIS:
        // comprobamos si el jugador tiene esa pieza
        if (jugadorTienePieza(jugadorActual, resultado)) {

            tipoPermitido = resultado;

            JOptionPane.showMessageDialog(
                    this,
                    "Cayó en: " + resultado +
                            "\nDebes mover esa pieza."
            );

            girar.setEnabled(false);
            return;
        }

        // Por seguridad, si no tiene la pieza
        int girosPermitidos = calcularGirosPermitidos(jugadorActual);

        System.out.println("El jugador no tiene esa pieza.");
        System.out.println("Giros permitidos: " + girosPermitidos);

        if (girosUsados < girosPermitidos) {

            JOptionPane.showMessageDialog(
                    this,
                    "No tienes esa pieza.\n" +
                            "Puedes girar nuevamente."
            );

            girar.setEnabled(true);
            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "No tienes esa pieza.\n" +
                        "Ya no tienes más giros.\n" +
                        "Pierdes el turno."
        );

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

        return 1 + perdidas;
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
        actualizarRuletaDisponibilidad();
        System.out.println("Turno de jugador: " + jugadorActual);
        registrarLog("--- Turno de jugador " + jugadorActual + " ---");
        if (jugadorActual == 1){
            indicadorJugador.setText("Turno de Jugador 1");


        }else if(jugadorActual==2){
            indicadorJugador.setText("Turno de Jugador 2");


        }

        this.add(indicadorJugador);


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

        //El Necrómante puede lanzar su lanza hasta 2 casillas, en línea recta (horizontal o vertical)
        boolean esLanzaNecromante = piezaSeleccionada.getTipo() == TipoPieza.necromante
                && ((Dfila <= 2 && Dcolumna == 0) || (Dcolumna <= 2 && Dfila == 0));

        if (esAdyacente) {
            atacarAdyacente(objetivo, filaDestino, columnaDestino);
            return;
        }

        if (esLanzaNecromante) {
            //Ataque de lanza: 2 de daño, ignora el escudo
            objetivo.recibirDanio(2, true);
            registrarLog("Jugador " + jugadorActual + " lanzó su lanza contra " + objetivo.getTipo());
            aplicarResultadoAtaque(objetivo, filaDestino, columnaDestino);
            return;
        }

        //Ataque a través de Zombie: el Necrómante puede ordenar el ataque sin importar
        //la distancia, siempre que el enemigo esté pegado a un Zombie propio
        if (piezaSeleccionada.getTipo() == TipoPieza.necromante
                && hayZombiePropioAdyacente(jugadorActual, filaDestino, columnaDestino)) {

            objetivo.recibirDanio(1, false);
            registrarLog("Jugador " + jugadorActual + " ordenó atacar a través de un Zombie contra " + objetivo.getTipo());
            aplicarResultadoAtaque(objetivo, filaDestino, columnaDestino);
            return;
        }

        System.out.println("Esa pieza no está al alcance");
    }

    //Revisa si hay un Zombie del mismo dueño en alguna de las 8 casillas alrededor de (fila, columna)
    private boolean hayZombiePropioAdyacente(int duenio, int fila, int columna) {

        for (int i = fila - 1; i <= fila + 1; i++) {
            for (int j = columna - 1; j <= columna + 1; j++) {

                if (i == fila && j == columna) {
                    continue;
                }
                if (i < 0 || i >= filas || j < 0 || j >= columnas) {
                    continue;
                }

                Pieza p = tablero[i][j];

                if (p != null && p.getDuenio() == duenio && p.getTipo() == TipoPieza.zombie) {
                    return true;
                }
            }
        }

        return false;
    }

    //Ataque contra una pieza adyacente: normal o especial (según el tipo de pieza atacante)
    private void atacarAdyacente(Pieza objetivo, int filaDestino, int columnaDestino) {

        if (piezaSeleccionada.getTipo() == TipoPieza.vampiro) {
            int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Absorber sangre? (quita 1 punto y cura 1 punto al Vampiro)\n"
                            + "Si eliges 'No' se hace un ataque normal.",
                    "Habilidad del Vampiro", JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                objetivo.recibirDanio(1, false);
                piezaSeleccionada.vida += 1; //Pieza está en el mismo paquete, se puede tocar el campo directo
                registrarLog("Jugador " + jugadorActual + " absorbió sangre de " + objetivo.getTipo());
                aplicarResultadoAtaque(objetivo, filaDestino, columnaDestino);
                return;
            }
        }

        //Ataque normal
        objetivo.recibirDanio(piezaSeleccionada.getAtaque(), false);
        registrarLog("Jugador " + jugadorActual + " atacó " + objetivo.getTipo());
        aplicarResultadoAtaque(objetivo, filaDestino, columnaDestino);
    }

    //Muestra el resultado del ataque, retira la pieza si murió y revisa si alguien ganó
    private void aplicarResultadoAtaque(Pieza objetivo, int filaDestino, int columnaDestino) {

        if (objetivo.estaViva()) {
            JOptionPane.showMessageDialog(this,
                    "Se atacó la pieza " + objetivo.getTipo() +
                            "; le quedan " + objetivo.getEscudo() + " de escudo y " +
                            objetivo.getVida() + " de vida");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Se destruyó la pieza " + objetivo.getTipo());
            registrarLog("Se destruyó " + objetivo.getTipo() + " del jugador " + objetivo.getDuenio());

            int duenioDerrotado = objetivo.getDuenio();
            tablero[filaDestino][columnaDestino] = null;
            registrarPiezaPerdida(duenioDerrotado, objetivo.getTipo());

            if (jugadorSinPiezas(duenioDerrotado)) {
                mostrarPiezas();
                terminarPorVictoria(jugadorActual, duenioDerrotado);
                return;
            }
        }

        mostrarPiezas();
        finalizarAccion();
    }

    private void registrarPiezaPerdida(int duenio, TipoPieza tipo) {

        if (duenio == 1) {

            piezasPerdidasJ1++;

            if (tipo == TipoPieza.hombre_lobo) {
                loboPerdidoJ1++;
            }

            if (tipo == TipoPieza.vampiro) {
                vampiroPerdidoJ1++;
            }

            if (tipo == TipoPieza.necromante) {
                necromantePerdidoJ1++;
            }

        } else {

            piezasPerdidasJ2++;

            if (tipo == TipoPieza.hombre_lobo) {
                loboPerdidoJ2++;
            }

            if (tipo == TipoPieza.vampiro) {
                vampiroPerdidoJ2++;
            }

            if (tipo == TipoPieza.necromante) {
                necromantePerdidoJ2++;
            }
        }

        actualizarRuletaDisponibilidad();
    }



    private void finalizarAccion() {
        piezaSeleccionada = null;
        filaSeleccionada = -1;
        columnaSeleccionada = -1;

        if (!partidaTerminada) {
            pasarTurno();
        }
    }

    private String nombreDeJugador(int numero) {
        if (numero == 1) {
            return nombreJugador1;
        }
        return nombreJugador2;
    }

    //Revisa si el jugador "duenio" ya no tiene ninguna pieza en el tablero
    private boolean jugadorSinPiezas(int duenio) {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (tablero[i][j] != null && tablero[i][j].getDuenio() == duenio) {
                    return false;
                }
            }
        }
        return true;
    }

    private void terminarPorVictoria(int ganador, int perdedor) {
        partidaTerminada = true;
        girar.setEnabled(false);
        retirarse.setEnabled(false);

        String mensaje = nombreDeJugador(ganador) + " venció a " + nombreDeJugador(perdedor)
                + ". ¡Felicidades, has ganado 3 puntos!";

        registrarLog(mensaje);
        cerrarPartida(ganador, mensaje);
    }

    private void terminarPorRetiro(int retirado) {
        partidaTerminada = true;
        girar.setEnabled(false);
        retirarse.setEnabled(false);

        int ganador = (retirado == 1) ? 2 : 1;

        String mensaje = nombreDeJugador(retirado) + " se ha retirado. ¡Felicidades, "
                + nombreDeJugador(ganador) + ", has ganado 3 puntos!";

        registrarLog(mensaje);
        cerrarPartida(ganador, mensaje);
    }

    //Le da los 3 puntos al ganador, guarda el registro en el historial y regresa al Menú Principal
    private void cerrarPartida(int numeroGanador, String mensaje) {

        Jugador ganador = Sesion.gestorJugadores.buscarPorUsuario(nombreDeJugador(numeroGanador));
        if (ganador != null) {
            ganador.sumarPuntos(3);
        }

        RegistroPartida registro = new RegistroPartida(nombreJugador1, nombreJugador2, mensaje);
        Sesion.registrarPartida(registro);

        JOptionPane.showMessageDialog(this, mensaje);

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setContentPane(new MenuPrincipal());
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == retirarse) {
            int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Seguro que quieres retirarte? Perderás la partida.",
                    "Retirarse", JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                terminarPorRetiro(jugadorActual);
            }
            return;
        }

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
