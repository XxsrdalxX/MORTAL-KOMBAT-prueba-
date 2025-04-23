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
    private int contadorTurnos = 0; // Contador de turnos

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
        this.contadorTurnos = 0; // Inicializa el contador de turnos

        configurarVentana();
        inicializarComponentes();
        ComboManager comboManager = new ComboManager(areaMensajes, jugador, enemigoActual); // Inicializa el
                                                                                            // ComboManager
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
        JPanel panelBotones = new JPanel(new GridLayout(2, 5));
        JButton btnAtacar = new JButton("Atacar");
        JButton btnCurar = new JButton("Curarse");
        JButton btnHabilidad = new JButton("Habilidad Especial");
        JButton btnFatality = new JButton("Fatality");
        JButton btnCombo = new JButton("Combo");// Botón Combo// Botón
                                                // para combos
        btnCombo.setEnabled(false); // Deshabilitar el botón Combo al inicio
        btnFatality.setEnabled(false); // Deshabilitar el botón Fatality al inicio

        panelBotones.add(btnAtacar);
        panelBotones.add(btnCurar);
        panelBotones.add(btnHabilidad);
        panelBotones.add(btnFatality); // Agregar botón Fatality al panel
        panelBotones.add(btnCombo); // Agregar botón Combo al panel
        panelBotones.add(new JButton("Salir")); // Botón de salir (puedes agregar funcionalidad)
        add(panelBotones, BorderLayout.SOUTH);

        // Agregar listeners a los botones
        agregarListeners(btnAtacar, btnCurar, btnHabilidad, btnFatality, btnCombo);
    }

    private void agregarListeners(JButton btnAtacar, JButton btnCurar, JButton btnHabilidad, JButton btnFatality,
            JButton btnCombo) {
        btnAtacar.addActionListener(e -> turnoJugador("atacar"));
        btnCurar.addActionListener(e -> turnoJugador("curar"));
        btnHabilidad.addActionListener(e -> turnoJugador("habilidad"));
        btnFatality.addActionListener(e -> realizarFatality(btnFatality));
        btnCombo.addActionListener(e -> usarCombo(btnCombo));
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
        // Habilitar o deshabilitar el botón "Fatality"
        JButton btnFatality = (JButton) ((JPanel) getContentPane().getComponent(2)).getComponent(3);
        btnFatality.setEnabled(enemigoActual.getVida() <= 30);

        actualizarInfo();
    }

    private void realizarFatality(JButton btnFatality) {
        if (enemigoActual.getVida() <= 30) {
            areaMensajes.append("¡Fatality! " + jugador.getNombre() + " ejecutó un movimiento final contra "
                    + enemigoActual.getNombre() + ".\n");
            enemigoActual.setVida(0); // Elimina al enemigo
            verificarEstado(); // Verifica el estado del juego
            btnFatality.setEnabled(false); // Deshabilita el botón después de usarlo
        }

    }

    private void verificarActivacionCombo() {
        if (contadorTurnos >= 3) {
            JButton btnCombo = (JButton) ((JPanel) getContentPane().getComponent(2)).getComponent(4); // Botón Combo
            btnCombo.setEnabled(true); // Habilitar el botón de combo
            areaMensajes.append("¡El botón de Combo está ahora disponible!\n");
        }
    }

    private void usarCombo(JButton btnCombo) {
        ComboManager comboManager = new ComboManager(areaMensajes, jugador, enemigoActual);
        comboManager.mostrarVentanaCombo(); // Llama al método que selecciona un combo aleatorio

        // Deshabilitar el botón de combo y reiniciar el contador de turnos
        btnCombo.setEnabled(false);
        contadorTurnos = 0; // Reiniciar el contador de turnos
        actualizarInfo(); // Actualizar las barras de vida
    }

    // ============================
    // Turno del jugador
    // ============================
    private void turnoJugador(String accion) {
        if (jugador.getEstado() != Estados.NORMAL) {
            String mensaje = "P1: " + jugador.getNombre() + " está " + jugador.getEstado().toString().toLowerCase()
                    + " y no puede actuar.";
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
        contadorTurnos++; // Incrementar el contador de turnos
        verificarActivacionCombo(); // Verificar si se puede activar el combo
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
        String mensaje = "P1: " + jugador.getNombre() + " usó su habilidad especial contra " + enemigoActual.getNombre()
                + ".";
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
            String accionEnemigo = iaBot.decidirAccion(enemigoActual, jugador, areaMensajes);
            verificarEstado();
        }
    }
}
