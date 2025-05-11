package Videojuego;

import Personajes.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.swing.*;

public class ModoHistoria extends JFrame {
    // ============================
    // Atributos principales
    // ============================
    private Personaje jugador;
    private HashMap<String, Runnable> fatalitys; // Almacena los fatalitys y sus efectos
    private List<Personaje> enemigos;
    private Personaje enemigoActual;
    private IAbot iaBot; // Instancia de la IA
    private int contadorTurnos = 0; // Contador de turnos
  private List<String> bolsa; // Bolsa de objetos (almacena nombres de objetos)
private Objetos gestorObjetos; // Instancia para gestionar los efectos de los objet
    
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
        this.bolsa = new ArrayList<>(); // Inicializa la bolsa vacía
    this.gestorObjetos = new Objetos(jugador); // Inicializa el gestor de objetos


        configurarVentana();
        inicializarComponentes();
        ComboManager comboManager = new ComboManager(areaMensajes, jugador, enemigoActual); // Inicializa el                                                                                 // ComboManager
        actualizarInfo(); // Actualiza la información inicial de los personajes
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        setVisible(true); // Mostrar la ventana

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
        JButton btnCombo = new JButton("Combo");// Botón Combo
        JButton btnBolsa = new JButton("Ver Bolsa");
                                                
        btnCombo.setEnabled(false); // Deshabilitar el botón Combo al inicio
        btnFatality.setEnabled(false); // Deshabilitar el botón Fatality al inicio

        panelBotones.add(btnAtacar);
        panelBotones.add(btnCurar);
        panelBotones.add(btnHabilidad);
        panelBotones.add(btnFatality); // Agregar botón Fatality al panel
        panelBotones.add(btnCombo); // Agregar botón Combo al panel
        panelBotones.add(btnBolsa); // Botón de salir
        add(panelBotones, BorderLayout.SOUTH);

        // Agregar listeners a los botones
        agregarListeners(btnAtacar, btnCurar, btnHabilidad, btnFatality, btnCombo, btnBolsa);
    }

    private void agregarListeners(JButton btnAtacar, JButton btnCurar, JButton btnHabilidad, JButton btnFatality,
            JButton btnCombo, JButton btnBolsa) {
        btnAtacar.addActionListener(e -> turnoJugador("atacar"));
        btnCurar.addActionListener(e -> turnoJugador("curar"));
        btnHabilidad.addActionListener(e -> turnoJugador("habilidad"));
        btnFatality.addActionListener(e -> realizarFatality(btnFatality));
        btnCombo.addActionListener(e -> usarCombo(btnCombo));
        btnBolsa.addActionListener(e -> mostrarBolsa()); // Mostrar bolsa de objetos
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
// ============================
// Verificar estado del juego
// ============================
private void verificarEstado() {
    if (enemigoActual.getVida() <= 0) {
        areaMensajes.append("¡Has derrotado a " + enemigoActual.getNombre() + "!\n");
        enemigos.remove(enemigoActual);

        // Generar un objeto aleatorio y agregarlo a la bolsa
        String nuevoObjeto = generarObjetoAleatorio();
        bolsa.add(nuevoObjeto);
        areaMensajes.append("¡Has obtenido un objeto: " + nuevoObjeto + "!\n");

        if (enemigos.isEmpty()) {
            areaMensajes.append("¡Felicidades! Has completado el modo historia.\n");
            JOptionPane.showMessageDialog(this, "¡Felicidades! Has completado el modo historia.", "Victoria",
                    JOptionPane.INFORMATION_MESSAGE);
            System.exit(0); // Cierra el programa
            return;
        }
        enemigoActual = enemigos.get(0);
        areaMensajes.append("¡Nuevo enemigo: " + enemigoActual.getNombre() + "!\n");
    }

    if (jugador.getVida() <= 0) {
        areaMensajes.append("Has sido derrotado. Fin del modo historia.\n");
        JOptionPane.showMessageDialog(this, "Has sido derrotado. Fin del modo historia.", "Derrota",
                JOptionPane.INFORMATION_MESSAGE);
        System.exit(0); // Cierra el programa
    }

    actualizarInfo();
}
    private void realizarFatality(JButton btnFatality) {
        if (enemigoActual.getVida() <= 30) {
            // Inicializar el mapa de fatalitys si no está inicializado
            if (fatalitys == null) {
                fatalitys = new HashMap<>();
                fatalitys.put("QWEERWWQ", () -> {
                    areaMensajes.append("¡Fatality!\n");
                    enemigoActual.setVida(0); // Matar al enemigo
                    areaMensajes.append("Has realizado un Fatality a " + enemigoActual.getNombre() + ".\n");
                    verificarEstado(); // Verificar si el enemigo ha sido derrotado
                });
            }

            // Mostrar la secuencia requerida al jugador
            String secuenciaRequerida = "QWEERWWQ"; // Puedes cambiar esta secuencia según sea necesario
            String mensaje = "Para realizar el Fatality, ingresa la siguiente secuencia:\n" + secuenciaRequerida;

            // Solicitar la secuencia al jugador
            String secuenciaIngresada = JOptionPane.showInputDialog(this,
                    mensaje,
                    "Realizar Fatality",
                    JOptionPane.PLAIN_MESSAGE);

            // Validar la secuencia ingresada
            if (secuenciaIngresada != null && fatalitys.containsKey(secuenciaIngresada)) {
                fatalitys.get(secuenciaIngresada).run(); // Ejecutar el Fatality
            } else {
                areaMensajes.append("Secuencia incorrecta. No se pudo realizar el Fatality.\n");
            }
        } else {
            areaMensajes.append("El enemigo tiene demasiada vida para realizar un Fatality.\n");
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
        verificarEstado();
    }

    // ============================
    // Turno del jugador
    // ============================
    private void turnoJugador(String accion) {
        if (jugador.getEstado() != Estados.NORMAL) {
            String mensaje = "P1: " + jugador.getNombre() + " está " + jugador.getEstado().toString().toLowerCase()
                    + " y no puede actuar.";
            areaMensajes.append(mensaje + "\n");
            jugador.reducirTurnosEstado(areaMensajes); // Reducir el efecto del estado
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
        jugador.atacar(enemigoActual, areaMensajes); // Pasar el área de mensajes
        String mensaje = "P1: " + jugador.getNombre() + " atacó a " + enemigoActual.getNombre() + ".";
        areaMensajes.append(mensaje + "\n");
        verificarEstado();
    }

    private void curar() {
        jugador.curar(areaMensajes); // Pasar el área de mensajes
        String mensaje = "P1: " + jugador.getNombre() + " se curó.";
        areaMensajes.append(mensaje + "\n");
        actualizarInfo();
    }

    private void usarHabilidadEspecial() {
        jugador.habilidadEspecial(enemigoActual, areaMensajes); // Pasar el área de mensajes
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
            areaMensajes.append("CPU: " + enemigoActual.getNombre() + " está paralizado y no puede actuar.\n");
            enemigoActual.reducirTurnosEstado(areaMensajes); // Reducir el efecto de parálisis
            return;
        }

        iaBot.decidirAccion(enemigoActual, jugador, areaMensajes); // Llama al método de la IA para decidir la acción
        verificarEstado(); // Verificar el estado del jugador y enemigo
    }

     // ============================
    // Generar objeto aleatorio
    // ============================
 private String generarObjetoAleatorio() {
    // Lista de nombres de objetos disponibles
    String[] objetosDisponibles = {
        "Poción de Salud",
        "Elixir de Poder",
        "Escudo de Resistencia",
        "Antídoto",
        "Aumento de Vida Máxima"
    };

    // Seleccionar un objeto aleatorio
    int indice = new Random().nextInt(objetosDisponibles.length);
    return objetosDisponibles[indice];
}

// ============================
// Mostrar y usar objetos de la bolsa
// ============================
private void mostrarBolsa() {
    if (bolsa.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Tu bolsa está vacía.", "Bolsa de Objetos", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    // Mostrar los objetos en la bolsa y permitir seleccionar uno
    String objetoSeleccionado = (String) JOptionPane.showInputDialog(
            this,
            "Selecciona un objeto para usar:",
            "Bolsa de Objetos",
            JOptionPane.PLAIN_MESSAGE,
            null,
            bolsa.toArray(),
            null);

    if (objetoSeleccionado != null) {
        gestorObjetos.usarObjeto(objetoSeleccionado); // Usar el objeto
        bolsa.remove(objetoSeleccionado); // Eliminar el objeto de la bolsa
        areaMensajes.append("Has usado el objeto: " + objetoSeleccionado + ".\n");
        actualizarInfo(); // Actualizar la información del jugador
    }
}
}