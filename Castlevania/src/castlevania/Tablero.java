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

    //Estos arreglos usan el índice 1 para el jugador 1 y el índice 2 para el jugador 2 (el índice 0 no se usa)
    private int[] piezasPerdidas = new int[3];
    private int[] lobosPerdidos = new int[3];
    private int[] vampirosPerdidos = new int[3];
    private int[] necromantesPerdidos = new int[3];


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
    private JButton habilidadEspecial = new JButton();
    private boolean usarHabilidadEspecial = false;


    private BotonPieza[][] pos = new BotonPieza[6][6];
    private Pieza[][] tablero = new Pieza[6][6];

    JPanel grid = new JPanel();
    private Ruleta ruleta=new Ruleta();
    JButton girar = new JButton();


    public Tablero() {
        this("Jugador 1", "Jugador 2");
    }

    //Arma toda la pantalla del tablero: cuadrícula, piezas, ruleta, log y botones
    public Tablero(String nombreJugador1, String nombreJugador2) {
        this.nombreJugador1 = nombreJugador1;
        this.nombreJugador2 = nombreJugador2;

        this.setLayout(null);
        this.setBackground(new Color(16, 16, 16));
        grid.setLayout(new GridLayout(6, 6));

        hacerCuadricula();
        colocarPieza();
        hacerRuleta();
        actualizarRuletaDisponibilidad();
        hacerPanelLog();
        hacerBotonRetirarse();
        hacerBotonHabilidadEspecial();
        configurarIndicadorJugador();
        mostrarPiezas();

        grid.setBounds(350, 5, 852, 852);
        this.add(grid);

    }

    //Etiqueta debajo de la ruleta que dice de quién es el turno (usando el nombre real de la cuenta)
    private void configurarIndicadorJugador() {
        indicadorJugador.setBounds(25, 460, 300, 50);
        indicadorJugador.setHorizontalAlignment(SwingConstants.CENTER);
        indicadorJugador.setForeground(new Color(204, 0, 11));
        indicadorJugador.setFont(new Font("Angel wish", Font.BOLD, 26));
        indicadorJugador.setText("Turno de " + nombreJugador1);

        this.add(indicadorJugador);
    }

    //Crea el botón de Retirarse arriba a la izquierda
    private void hacerBotonRetirarse() {
        retirarse.setText("Retirarse");
        retirarse.setContentAreaFilled(false);
        retirarse.setForeground(new Color(204, 0, 11));
        retirarse.setFont(new Font("Angel wish", Font.BOLD, 22));
        retirarse.setBounds(25, 10, 300, 60);
        retirarse.setFocusable(false);
        retirarse.setBorderPainted(true);
        retirarse.setBorder(BorderFactory.createLineBorder(new Color(169, 47, 67), 2));
        retirarse.addActionListener(this);

        this.add(retirarse);
    }

    //Crea el botón de Habilidad Especial abajo a la izquierda
    private void hacerBotonHabilidadEspecial() {
        habilidadEspecial.setText("Habilidad Especial");
        habilidadEspecial.setContentAreaFilled(false);
        habilidadEspecial.setForeground(new Color(204, 0, 11));
        habilidadEspecial.setFont(new Font("Angel wish", Font.BOLD, 20));
        habilidadEspecial.setBounds(25, 792, 300, 60);
        habilidadEspecial.setFocusable(false);
        habilidadEspecial.setBorderPainted(true);
        habilidadEspecial.setBorder(BorderFactory.createLineBorder(new Color(169, 47, 67), 2));
        habilidadEspecial.setEnabled(false);
        habilidadEspecial.addActionListener(this);

        this.add(habilidadEspecial);
    }

    //Habilita/deshabilita y rotula el botón de habilidad especial según la pieza seleccionada
    private void actualizarBotonHabilidadEspecial() {
        usarHabilidadEspecial = false;
        habilidadEspecial.setText("Habilidad Especial");
        habilidadEspecial.setEnabled(false);

        if (piezaSeleccionada == null) {
            return;
        }

        if (piezaSeleccionada.getTipo() == TipoPieza.vampiro) {
            habilidadEspecial.setText("Absorber Sangre");
            habilidadEspecial.setEnabled(true);
        }

        if (piezaSeleccionada.getTipo() == TipoPieza.necromante) {
            habilidadEspecial.setText("Invocar Zombie");
            habilidadEspecial.setEnabled(true);
        }
    }



    //Crea los 36 botones del tablero y les pone el color ajedrezado
    private void hacerCuadricula(){
        for (int i = 0; i < filas; i++) {
            for (int j=0; j < columnas;j++) {

                pos[i][j] = new BotonPieza();
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

    //Crea el botón de girar, la ruleta y el selector encima
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

    //Le dice a la ruleta cuántas piezas de cada tipo le quedan vivas al jugador actual
    private void actualizarRuletaDisponibilidad() {

        int lobosVivos = 2 - lobosPerdidos[jugadorActual];
        int vampirosVivos = 2 - vampirosPerdidos[jugadorActual];
        int necromantesVivos = 2 - necromantesPerdidos[jugadorActual];

        ruleta.actualizarDisponible(
                lobosVivos,
                vampirosVivos,
                necromantesVivos
        );
    }


    //Agrega una línea al panel de log y baja el scroll hasta el final
    private void registrarLog(String mensaje) {
        logArea.append(mensaje + "\n\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    //Convierte fila/columna a una casilla tipo 1,1 empezando abajo a la izquierda (solo para el log)
    private String nombreCasilla(int fila, int columna) {
        int numeroFila = filas - fila;
        int numeroColumna = columna + 1;
        return numeroFila + "," + numeroColumna;
    }

    //Le pone estilo al panel de texto del log y lo coloca a la derecha
    private void hacerPanelLog() {
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setBackground(new Color(29, 29, 28));
        logArea.setForeground(Color.WHITE);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 20));

        logScroll.setBounds(1220, 5, 360, 852);
        logScroll.setBorder(BorderFactory.createLineBorder(new Color(98, 24, 21), 2));

        this.add(logScroll);
    }


    //Pone las piezas iniciales de los dos jugadores en el tablero
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

    //Dibuja en cada casilla la imagen de la pieza que tenga (o la esconde si está vacía)
    private void mostrarPiezas(){
        for (int i=0;i<filas;i++){
            for (int j=0;j<columnas;j++){
                if (tablero[i][j] == null) {
                    pos[i][j].ocultarStats();
                    continue;
            }

                String rutaImagen;

                switch (tablero[i][j].getTipo()) {

                    case hombre_lobo:
                        rutaImagen = (tablero[i][j].getDuenio() == 1)
                                ? "/resources/lobo_blanco.png"
                                : "/resources/lobo_negro.png";
                        break;

                    case vampiro:
                        rutaImagen = (tablero[i][j].getDuenio() == 1)
                                ? "/resources/vampiro_blanco.png"
                                : "/resources/vampiro_negro.png";
                        break;

                    case necromante:
                        rutaImagen = (tablero[i][j].getDuenio() == 1)
                                ? "/resources/necromante_blanco.png"
                                : "/resources/necromante_negro.png";
                        break;

                    case zombie:
                        rutaImagen = (tablero[i][j].getDuenio() == 1)
                                ? "/resources/zombie_blanco.png"
                                : "/resources/zombie_negro.png";
                        break;

                    default:
                        rutaImagen = null;
                }

                Image imagenPieza = new ImageIcon(Main.class.getResource(rutaImagen)).getImage();

                pos[i][j].mostrarPieza(
                        imagenPieza,
                        tablero[i][j].getEscudo(),
                        tablero[i][j].getVida(),
                        tablero[i][j].getAtaque()
                );
            }


        }


    }


    //Recibe el resultado de la ruleta y decide si el jugador debe mover, girar de nuevo o perder el turno
    private void procesarResultadoRuleta(TipoPieza resultado) {

        girosUsados++;

        System.out.println("Resultado: " + resultado);
        System.out.println("Giros usados: " + girosUsados);

        //Si cayó en una casilla gris, resultado viene null y cuenta como fallo
        if (resultado != null && jugadorTienePieza(jugadorActual, resultado)) {

            tipoPermitido = resultado;

            String nombrePieza;
            if (resultado == TipoPieza.vampiro) {
                nombrePieza = "Vampiro";
            } else if (resultado == TipoPieza.hombre_lobo) {
                nombrePieza = "Hombre Lobo";
            } else if (resultado == TipoPieza.necromante) {
                nombrePieza = "Necromante";
            } else {
                nombrePieza = resultado.toString();
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Cayó en: " + nombrePieza +
                            "\nDebes mover esa pieza."
            );

            //Ya encontró una pieza válida, no puede volver a girar
            girar.setEnabled(false);

            return;
        }

        //Cayó en una pieza gris (ya eliminada)
        int girosPermitidos = calcularGirosPermitidos(jugadorActual);

        System.out.println("Cayó en GRIS.");
        System.out.println("Giros permitidos: " + girosPermitidos);
        System.out.println("Giros usados: " + girosUsados);

        //Todavía tiene otro intento
        if (girosUsados < girosPermitidos) {

            JOptionPane.showMessageDialog(
                    this,
                    "Cayó en una pieza eliminada.\n" +
                            "Puedes girar nuevamente."
            );

            //El jugador tiene que presionar GIRAR
            girar.setEnabled(true);

            return;
        }

        //Ya no le quedan giros, pierde el turno
        JOptionPane.showMessageDialog(
                this,
                "Cayó en una pieza eliminada.\n" +
                        "Ya no tienes más giros.\n" +
                        "Pierdes el turno."
        );

        girar.setEnabled(false);

        pasarTurno();
    }

    //Revisa si el jugador "duenio" todavía tiene alguna pieza del tipo dado
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
        // 1 giro base + 1 giro extra por cada 2 piezas perdidas
        // (perdidas=0 o 1 -> 1 giro; perdidas=2 o 3 -> 2 giros; perdidas=4 o 5 -> 3 giros)
        return 1 + (piezasPerdidas[duenio] / 2);
    }

    //Cambia el turno al otro jugador y reinicia los giros de la ruleta
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

        if (jugadorActual == 1) {
            indicadorJugador.setText("Turno de " + nombreJugador1);
        } else {
            indicadorJugador.setText("Turno de " + nombreJugador2);
        }
    }



    //Mueve la pieza seleccionada a la casilla vacía elegida (o invoca un Zombie si el Necrómante usó su habilidad)
    private void intentarMover(int filaDestino, int columnaDestino) {

        if (piezaSeleccionada.getTipo() == TipoPieza.necromante && usarHabilidadEspecial) {
            invocarZombie(filaDestino, columnaDestino);
            finalizarAccion();
            return;
        }

        if (!piezaSeleccionada.puedeMover(filaDestino, columnaDestino)) {
            System.out.println("Movimiento inválido");
            return;
        }

        //El Hombre Lobo se mueve hasta 2 casillas, pero no puede saltar sobre una pieza
        if (hayPiezaEnElCamino(filaDestino, columnaDestino)) {
            System.out.println("Hay una pieza en el camino");
            return;
        }

        tablero[filaSeleccionada][columnaSeleccionada] = null;
        tablero[filaDestino][columnaDestino] = piezaSeleccionada;
        piezaSeleccionada.setPosicion(filaDestino, columnaDestino);

        registrarLog("Jugador " + jugadorActual + " movió " + piezaSeleccionada.getTipo() +
                " a " + nombreCasilla(filaDestino, columnaDestino));

        mostrarPiezas();
        finalizarAccion();
    }


    //Pone un Zombie nuevo en la casilla elegida por el Necrómante
    private void invocarZombie(int fila, int columna) {
        int duenio = piezaSeleccionada.getDuenio();
        tablero[fila][columna] = new Zombie(duenio, fila, columna);
        mostrarPiezas();
        registrarLog("Jugador " + duenio + " invocó un Zombie en " + nombreCasilla(fila, columna));
        System.out.println("Zombie invocado en " + fila + "," + columna);
    }


    //Decide qué tipo de ataque hacer según la distancia entre la pieza seleccionada y el objetivo
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

        if (piezaSeleccionada.getTipo() == TipoPieza.vampiro && usarHabilidadEspecial) {
            objetivo.recibirDanio(1, false);
            piezaSeleccionada.vida += 1; //Pieza está en el mismo paquete, se puede tocar el campo directo
            registrarLog("Jugador " + jugadorActual + " absorbió sangre de " + objetivo.getTipo());
            aplicarResultadoAtaque(objetivo, filaDestino, columnaDestino);
            return;
        }

        //Ataque normal
        objetivo.recibirDanio(piezaSeleccionada.getAtaque(), false);
        registrarLog("Jugador " + jugadorActual + " atacó " + objetivo.getTipo());
        aplicarResultadoAtaque(objetivo, filaDestino, columnaDestino);
    }

    //Muestra el resultado del ataque, retira la pieza si murió y revisa si alguien ganó
    private void aplicarResultadoAtaque(Pieza objetivo, int filaDestino, int columnaDestino) {

        if (objetivo.estaViva()) {
            JOptionPane.showMessageDialog(this, "Se atacó la pieza " + objetivo.getTipo());
            registrarLog("Se atacó la pieza " + objetivo.getTipo() +
                    "; le quedan " + objetivo.getEscudo() + " de escudo y " +
                    objetivo.getVida() + " de vida");
        } else {
            JOptionPane.showMessageDialog(this, "Se destruyó la pieza " + objetivo.getTipo());
            registrarLog("Se destruyó " + objetivo.getTipo() + " del jugador " + objetivo.getDuenio());

            int duenioDerrotado = objetivo.getDuenio();
            tablero[filaDestino][columnaDestino] = null;
            registrarPiezaPerdida(duenioDerrotado, objetivo.getTipo());

            if (jugadorSinPiezas(duenioDerrotado)) {
                mostrarPiezas();
                terminarPorVictoria(jugadorActual, duenioDerrotado);
                return;
            }

            if (jugadorSoloTieneZombies(duenioDerrotado)) {
                mostrarPiezas();
                terminarPorVictoria(jugadorActual, duenioDerrotado);
                return;
            }
        }

        mostrarPiezas();
        finalizarAccion();
    }

    //Suma la pieza perdida a los contadores del jugador que la perdió
    private void registrarPiezaPerdida(int duenio, TipoPieza tipo) {


        if (tipo != TipoPieza.zombie) {
            piezasPerdidas[duenio]++;
        }

        if (tipo == TipoPieza.hombre_lobo) {
            lobosPerdidos[duenio]++;
        }

        if (tipo == TipoPieza.vampiro) {
            vampirosPerdidos[duenio]++;
        }

        if (tipo == TipoPieza.necromante) {
            necromantesPerdidos[duenio]++;
        }

        actualizarRuletaDisponibilidad();
    }



    //El Hombre Lobo salta la casilla del medio cuando se mueve 2 casillas; revisa que no haya pieza ahí
    private boolean hayPiezaEnElCamino(int filaDestino, int columnaDestino) {
        if (piezaSeleccionada.getTipo() != TipoPieza.hombre_lobo) {
            return false;
        }

        int dFila = filaDestino - filaSeleccionada;
        int dColumna = columnaDestino - columnaSeleccionada;

        if (Math.abs(dFila) == 2 || Math.abs(dColumna) == 2) {
            int filaMedio = filaSeleccionada + dFila / 2;
            int columnaMedio = columnaSeleccionada + dColumna / 2;

            return tablero[filaMedio][columnaMedio] != null;
        }

        return false;
    }

    //Pinta de verde las casillas vacías a las que la pieza seleccionada se puede mover
    private void marcarCasillasDeMovimiento() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (tablero[i][j] == null && piezaSeleccionada.puedeMover(i, j) && !hayPiezaEnElCamino(i, j)) {
                    pos[i][j].setBackground(new Color(46, 139, 46));
                }
            }
        }
    }

    //Regresa las casillas al color ajedrezado normal
    private void quitarMarcasDeMovimiento() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if ((i + j) % 2 == 0) {
                    pos[i][j].setBackground(new Color(98, 24, 21));
                } else {
                    pos[i][j].setBackground(new Color(29, 29, 28));
                }
            }
        }
    }

    //Limpia la selección actual y pasa el turno si la partida sigue
    private void finalizarAccion() {
        quitarMarcasDeMovimiento();
        piezaSeleccionada = null;
        filaSeleccionada = -1;
        columnaSeleccionada = -1;
        actualizarBotonHabilidadEspecial();

        if (!partidaTerminada) {
            pasarTurno();
        }
    }

    //Devuelve el nombre real del jugador 1 o 2
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

    //Revisa si al jugador "duenio" ya no le queda ninguna pieza principal

    private boolean jugadorSoloTieneZombies(int duenio) {
        boolean tieneAlgunaPieza = false;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Pieza p = tablero[i][j];

                if (p != null && p.getDuenio() == duenio) {
                    tieneAlgunaPieza = true;

                    if (p.getTipo() != TipoPieza.zombie) {
                        return false; //todavía tiene al menos una pieza principal
                    }
                }
            }
        }

        return tieneAlgunaPieza;
    }

    //Termina la partida porque un jugador se quedó sin piezas
    private void terminarPorVictoria(int ganador, int perdedor) {
        partidaTerminada = true;
        girar.setEnabled(false);
        retirarse.setEnabled(false);
        habilidadEspecial.setEnabled(false);

        String mensaje = nombreDeJugador(ganador) + " venció a " + nombreDeJugador(perdedor)
                + ". ¡Felicidades, has ganado 3 puntos!";

        registrarLog(mensaje);
        cerrarPartida(ganador, mensaje);
    }

    //Termina la partida porque un jugador se retiró
    private void terminarPorRetiro(int retirado) {
        partidaTerminada = true;
        girar.setEnabled(false);
        retirarse.setEnabled(false);
        habilidadEspecial.setEnabled(false);

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

    //Maneja los clics de todos los botones: retirarse, girar, habilidad especial y las 36 casillas
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

        if (e.getSource() == habilidadEspecial) {
            usarHabilidadEspecial = true;
            habilidadEspecial.setEnabled(false);
            return;
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
                        actualizarBotonHabilidadEspecial();
                        marcarCasillasDeMovimiento();

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
