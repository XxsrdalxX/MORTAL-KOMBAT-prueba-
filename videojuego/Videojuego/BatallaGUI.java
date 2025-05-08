package Videojuego;

import Personajes.*;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class BatallaGUI extends JFrame {
    // Atributos principales
    private Personaje jugador;
    private Personaje enemigo;
    private JTextArea areaMensajes;
    private IAbot iaBot;
    private JProgressBar barraJugadorVida, barraEnemigoVida;
    private int contadorTurnos = 0; // Contador de turnos
    private HashMap<String, Runnable> fatalitys; // Almacena los fatalitys y sus efectos
    private JLabel labelJugadorNombre, labelEnemigoNombre;

    // Constructor
    public BatallaGUI(Personaje jugador, Personaje enemigo) {
        this.jugador = jugador;
        this.enemigo = enemigo;
        iaBot = new IAbot(); // Inicializar la IA para el enemigo
        iniciarBatalla();
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        setVisible(true); // Mostrar la ventana
    }

    // Método principal para inicializar la batalla
    private void iniciarBatalla() {
        setTitle("Batalla Mortal Kombat");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior: Información de los personajes y barras de vida
        JPanel panelInfo = new JPanel(new GridLayout(2, 2));
        inicializarBarrasDeVida(panelInfo);
        add(panelInfo, BorderLayout.NORTH);

        // Panel central: Área de mensajes
        inicializarAreaMensajes();

        // Panel inferior: Botones de acción
        inicializarBotones();
    }

    // Inicializa las barras de vida de los personajes
    private void inicializarBarrasDeVida(JPanel panelInfo) {
        // Barra de vida del jugador
        barraJugadorVida = new JProgressBar(0, jugador.getVidaMaxima());
        barraJugadorVida.setValue(jugador.getVida());
        barraJugadorVida.setStringPainted(true);
        barraJugadorVida.setForeground(Color.GREEN);
        panelInfo.add(new JLabel("Jugador: " + jugador.getNombre()));
        panelInfo.add(barraJugadorVida);

        // Barra de vida del enemigo
        barraEnemigoVida = new JProgressBar(0, enemigo.getVidaMaxima());
        barraEnemigoVida.setValue(enemigo.getVida());
        barraEnemigoVida.setStringPainted(true);
        barraEnemigoVida.setForeground(Color.RED);
        panelInfo.add(new JLabel("Enemigo: " + enemigo.getNombre()));
        panelInfo.add(barraEnemigoVida);
    }

    // Inicializa el área de mensajes
    private void inicializarAreaMensajes() {
        areaMensajes = new JTextArea();
        areaMensajes.setEditable(false);
        areaMensajes.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaMensajes.putClientProperty("Nimbus.Overrides", new UIDefaults() {
            {
                put("TextArea.lineSpacing", 0.5); // Ajustar el espaciado entre líneas
            }
        });
        JScrollPane scrollPane = new JScrollPane(areaMensajes);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Inicializa los botones de acción
    private void inicializarBotones() {
        JPanel panelBotones = new JPanel(new GridLayout(2, 3));
        JButton btnAtacar = new JButton("Atacar");
        JButton btnCurar = new JButton("Curarse");
        JButton btnHabilidad = new JButton("Habilidad Especial");
        JButton btnCombo = new JButton("Combo");
        JButton btnFatality = new JButton("Fatality");
        JButton btnOBJ = new JButton("Objetos");

        // Agregar botones al panel
        panelBotones.add(btnAtacar);
        panelBotones.add(btnCurar);
        panelBotones.add(btnHabilidad);
        panelBotones.add(btnCombo);
        panelBotones.add(btnFatality);
        panelBotones.add(btnOBJ);
        add(panelBotones, BorderLayout.SOUTH);

        // Configurar imágenes de los botones
        configurarImagenBoton(btnAtacar, "/Resources/Golpe.jpg");
        configurarImagenBoton(btnHabilidad, "/Resources/Habilidad.jpg");

        // Deshabilitar botones al inicio
        btnCombo.setEnabled(false);
        btnFatality.setEnabled(false);

        // Agregar listeners a los botones
        agregarListeners(btnAtacar, btnCurar, btnHabilidad, btnFatality, btnCombo, btnOBJ);
    }

    // Configura una imagen para un botón
    private void configurarImagenBoton(JButton boton, String rutaImagen) {
        ImageIcon icono = new ImageIcon(getClass().getResource(rutaImagen));
        Image imagenEscalada = icono.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        boton.setIcon(new ImageIcon(imagenEscalada));

        ImageIcon iconoHover = new ImageIcon(getClass().getResource(rutaImagen));
        Image imagenHover = iconoHover.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        boton.setRolloverIcon(new ImageIcon(imagenHover)); // Cambia el icono al pasar el mouse

    }

    // Agrega los listeners a los botones
    private void agregarListeners(JButton btnAtacar, JButton btnCurar, JButton btnHabilidad, JButton btnFatality,
            JButton btnCombo, JButton btnSalir) {
        // Listener para el botón "Salir"
        btnSalir.addActionListener(e -> {
            int respuesta = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que quieres salir?", "Salir",
                    JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                System.exit(0); // Cerrar el programa
            }
        });

        // Listeners para las acciones del jugador
        btnAtacar.addActionListener(e -> turnoJugador("atacar"));
        btnCurar.addActionListener(e -> turnoJugador("curar"));
        btnHabilidad.addActionListener(e -> turnoJugador("habilidad"));
        btnFatality.addActionListener(e -> realizarFatality(btnFatality));
        btnCombo.addActionListener(e -> usarCombo(btnCombo));
    }

    // Métodos de acción del jugador
    private void turnoJugador(String accion) {
        if (jugador.getEstado() != Estados.NORMAL) {
            areaMensajes.append("P1: " + jugador.getNombre() + " está " + jugador.getEstado().toString().toLowerCase()
                    + " y no puede actuar.\n");
            jugador.reducirTurnosEstado(areaMensajes);
            turnoEnemigo();
            return;
        }

        switch (accion) {
            case "atacar":
                atacar();
                break;
            case "curar":
                curar();
                break;
            case "habilidad":
                usarHabilidadEspecial();
                break;
            default:
                throw new IllegalArgumentException("Acción no válida: " + accion);
        }

        contadorTurnos++;
        verificarActivacionCombo();
        turnoEnemigo();
        verificarEstado();
    }

    private void atacar() {
        jugador.atacar(enemigo, areaMensajes);
        areaMensajes.append("P1: " + jugador.getNombre() + " atacó a " + enemigo.getNombre() + "\n");
        actualizarBarrasDeVida();
    }

    private void curar() {
        jugador.curar(areaMensajes);
        areaMensajes.append("P1: " + jugador.getNombre() + " se curó.\n");
        actualizarBarrasDeVida();
    }

    private void usarHabilidadEspecial() {
        jugador.habilidadEspecial(enemigo, areaMensajes);
        areaMensajes.append("P1: " + jugador.getNombre() + " usó su habilidad especial contra " + enemigo.getNombre()
                + ".\n");
    }

    private void turnoEnemigo() {
        if (enemigo.getEstado() == Estados.PARALIZADO) {
            areaMensajes.append("CPU: " + enemigo.getNombre() + " está paralizado y no puede actuar.\n");
            enemigo.reducirTurnosEstado(areaMensajes);
            return;
        }

        iaBot.decidirAccion(enemigo, jugador, areaMensajes);
        actualizarBarrasDeVida();
        verificarEstado();
    }

    private void verificarEstado() {
        if (enemigo.getVida() <= 0) {
            areaMensajes.append("¡Has derrotado a " + enemigo.getNombre() + "!\n");
            JOptionPane.showMessageDialog(this, "¡Ganaste la batalla!");
            dispose();
        } else if (jugador.getVida() <= 0) {
            areaMensajes.append("Has sido derrotado. Fin del juego.\n");
            JOptionPane.showMessageDialog(this, "Has sido derrotado. Fin del juego.", "Derrota",
                    JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    private void actualizarBarrasDeVida() {
        barraJugadorVida.setValue(jugador.getVida());
        barraEnemigoVida.setValue(enemigo.getVida());
    }

    private void verificarActivacionCombo() {
        if (contadorTurnos >= 3) {
            JButton btnCombo = (JButton) ((JPanel) getContentPane().getComponent(2)).getComponent(3);
            btnCombo.setEnabled(true);
            areaMensajes.append("¡El botón de Combo está ahora disponible!\n");
        }
    }

    private void usarCombo(JButton btnCombo) {
        ComboManager comboManager = new ComboManager(areaMensajes, jugador, enemigo);
        comboManager.mostrarVentanaCombo();

        btnCombo.setEnabled(false);
        contadorTurnos = 0;
        actualizarBarrasDeVida();
        verificarEstado();
    }

    private void realizarFatality(JButton btnFatality) {
        if (enemigo.getVida() <= enemigo.getVidaMaxima() * 0.3) {
            if (fatalitys == null) {
                fatalitys = new HashMap<>();
                fatalitys.put("QWEERWWQ", () -> {
                    areaMensajes.append("¡Fatality!\n");
                    enemigo.setVida(0);
                    areaMensajes.append("Has realizado un Fatality a " + enemigo.getNombre() + ".\n");
                    verificarEstado();
                });
            }

            String secuenciaRequerida = "QWEERWWQ";
            String mensaje = "Para realizar el Fatality, ingresa la siguiente secuencia:\n" + secuenciaRequerida;

            String secuenciaIngresada = JOptionPane.showInputDialog(this, mensaje, "Realizar Fatality",
                    JOptionPane.PLAIN_MESSAGE);

            if (secuenciaIngresada != null && fatalitys.containsKey(secuenciaIngresada)) {
                fatalitys.get(secuenciaIngresada).run();
                btnFatality.setEnabled(false);
            } else {
                areaMensajes.append("Secuencia incorrecta. No se pudo realizar el Fatality.\n");
            }
        } else {
            areaMensajes.append("El enemigo tiene demasiada vida para realizar un Fatality.\n");
        }
    }
}