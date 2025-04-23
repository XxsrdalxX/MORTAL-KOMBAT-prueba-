package Videojuego;

import Personajes.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ModoHistoria extends JFrame {
    // ============================
    // Atributos principales
    // ============================
    private Personaje jugador;
    private List<Personaje> enemigos;
    private Personaje enemigoActual;
    private IAbot iaBot; // Instancia de la IA

    // Componentes de la interfaz gráfica
    private JTextArea areaMensajes;
    private JLabel labelJugadorNombre, labelEnemigoNombre;
    private JProgressBar barraJugadorVida, barraEnemigoVida;

    // ============================
    // Constructor
    // ============================
    public ModoHistoria(Personaje jugador) {
        this.jugador = jugador;
        this.enemigos = crearEnemigos();
        this.enemigoActual = enemigos.get(0);
        this.iaBot = new IAbot(); // Inicializa la IA

        configurarVentana();
        inicializarComponentes();
        actualizarInfo(); // Actualiza la información inicial de los personajes
        setVisible(true);
    }

    // ============================
    // Configuración de la ventana
    // ============================
    private void configurarVentana() {
        setTitle("Modo Historia - Mortal Kombat");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    // ============================
    // Inicialización de componentes
    // ============================
    private void inicializarComponentes() {
        // Panel superior: Información de los personajes
        JPanel panelInfo = new JPanel(new GridLayout(2, 2));

        // Nombre y barra de vida del jugador
        labelJugadorNombre = new JLabel(jugador.getNombre());
        barraJugadorVida = new JProgressBar(0, jugador.getVidaMaxima());
        barraJugadorVida.setValue(jugador.getVida());
        barraJugadorVida.setStringPainted(true);
        barraJugadorVida.setForeground(Color.GREEN);

        // Nombre y barra de vida del enemigo
        labelEnemigoNombre = new JLabel(enemigoActual.getNombre());
        barraEnemigoVida = new JProgressBar(0, enemigoActual.getVidaMaxima());
        barraEnemigoVida.setValue(enemigoActual.getVida());
        barraEnemigoVida.setStringPainted(true);
        barraEnemigoVida.setForeground(Color.RED);

        // Agregar componentes al panel
        panelInfo.add(labelJugadorNombre);
        panelInfo.add(labelEnemigoNombre);
        panelInfo.add(barraJugadorVida);
        panelInfo.add(barraEnemigoVida);

        add(panelInfo, BorderLayout.NORTH);

        // Panel central: Área de mensajes
        areaMensajes = new JTextArea();
        areaMensajes.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaMensajes);
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior: Botones de acción
        JPanel panelBotones = new JPanel(new GridLayout(1, 3));
        JButton btnAtacar = new JButton("Atacar");
        JButton btnCurar = new JButton("Curarse");
        JButton btnHabilidad = new JButton("Habilidad Especial");
        panelBotones.add(btnAtacar);
        panelBotones.add(btnCurar);
        panelBotones.add(btnHabilidad);
        add(panelBotones, BorderLayout.SOUTH);

        // Agregar listeners a los botones
        agregarListeners(btnAtacar, btnCurar, btnHabilidad);
    }

    private void agregarListeners(JButton btnAtacar, JButton btnCurar, JButton btnHabilidad) {
        btnAtacar.addActionListener(e -> turnoJugador("atacar"));
        btnCurar.addActionListener(e -> turnoJugador("curar"));
        btnHabilidad.addActionListener(e -> turnoJugador("habilidad"));
    }

    // ============================
    // Métodos de lógica del juego
    // ============================
    private List<Personaje> crearEnemigos() {
        List<Personaje> enemigos = new ArrayList<>();
        enemigos.add(new Raiden(100, 10, Estados.NORMAL, 15, "Raiden"));
        enemigos.add(new Kitana(120, 8, Estados.NORMAL, 20, "Kitana"));
        enemigos.add(new SubZero("Hielo", 150, 12, Estados.NORMAL, 25, "Sub-Zero"));
        enemigos.add(new Kitana(200, 15, Estados.NORMAL, 30, "Shao Kahn")); // Jefe final
        return enemigos;
    }

    private void actualizarInfo() {
        // Actualizar nombres y barras de vida
        labelJugadorNombre.setText(jugador.getNombre() + " - Vida: " + jugador.getVida());
        barraJugadorVida.setValue(jugador.getVida());

        labelEnemigoNombre.setText(enemigoActual.getNombre() + " - Vida: " + enemigoActual.getVida());
        barraEnemigoVida.setValue(enemigoActual.getVida());
    }

    private void verificarEstado() {
        if (enemigoActual.getVida() <= 0) {
            areaMensajes.append("¡Has derrotado a " + enemigoActual.getNombre() + "!\n");
            enemigos.remove(enemigoActual);
            if (enemigos.isEmpty()) {
                areaMensajes.append("¡Felicidades! Has completado el modo historia.\n");
                return;
            }
            enemigoActual = enemigos.get(0);
            areaMensajes.append("¡Nuevo enemigo: " + enemigoActual.getNombre() + "!\n");
        }

        if (jugador.getVida() <= 0) {
            areaMensajes.append("Has sido derrotado. Fin del modo historia.\n");
        }

        actualizarInfo();
    }

    // ============================
    // Turno del jugador
    // ============================
    private void turnoJugador(String accion) {
        if (jugador.getEstado() != Estados.NORMAL) {
            String mensaje = "P1: " + jugador.getNombre() + " está " + jugador.getEstado().toString().toLowerCase() + " y no puede actuar.";
            areaMensajes.append(mensaje + "\n");
            jugador.reducirTurnosEstado(); // Reducir el efecto del estado
            turnoEnemigo(); // Pasar el turno al enemigo
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
    
        turnoEnemigo(); // El enemigo actúa después del jugador
    }

    private void atacar() {
        jugador.atacar(enemigoActual);
        String mensaje = "P1: " + jugador.getNombre() + " atacó a " + enemigoActual.getNombre() + ".";
        areaMensajes.append(mensaje + "\n");
        verificarEstado();
    }

    private void curar() {
        jugador.curar();
        String mensaje = "P1: " + jugador.getNombre() + " se curó.";
        areaMensajes.append(mensaje + "\n");
        actualizarInfo();
    }
    private void usarHabilidadEspecial() {
        jugador.habilidadEspecial(enemigoActual);
        String mensaje = "P1: " + jugador.getNombre() + " usó su habilidad especial contra " + enemigoActual.getNombre() + ".";
        areaMensajes.append(mensaje + "\n");
        verificarEstado();
    }
    // ============================
    // Turno del enemigo
    // ============================
    private void turnoEnemigo() {
        if (enemigoActual.getEstado() == Estados.PARALIZADO) {
            String mensaje = "CPU: " + enemigoActual.getNombre() + " está paralizado y no puede actuar.";
            areaMensajes.append(mensaje + "\n");
            enemigoActual.reducirTurnosEstado(); // Reducir el efecto de parálisis
            return;
        }

        if (enemigoActual.getVida() > 0) { // Solo actúa si sigue vivo
            String accionEnemigo = iaBot.decidirAccion(enemigoActual, jugador, null);
            areaMensajes.append(accionEnemigo + "\n");
            verificarEstado();
        }
    }
}