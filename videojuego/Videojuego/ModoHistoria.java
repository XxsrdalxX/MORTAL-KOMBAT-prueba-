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
    private Objetos gestorObjetos; // Instancia para gestionar los efectos de los objetos

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
        this.bolsa = new ArrayList<>(); // Inicializa la bolsa vacía
        this.gestorObjetos = new Objetos(jugador); // Inicializa el gestor de objetos

        configurarVentana();
        inicializarComponentes();
        actualizarInfo(); // Actualiza la información inicial de los personajes
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        setVisible(true); // Mostrar la ventana
    }

    // ============================
    // Configuración de la ventana
    // ============================
    private void configurarVentana() {
        setTitle("☠️ MORTAL KOMBAT - Modo Historia ☠️");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(25, 25, 30));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    // ============================
    // Inicialización de componentes
    // ============================
    private void inicializarComponentes() {
        // Panel superior: Información del jugador y enemigo
        JPanel panelInfo = new JPanel(new GridLayout(2, 2, 8, 5));
        panelInfo.setBackground(new Color(35, 35, 45));
        panelInfo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 0, 0), 1),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));

        labelJugadorNombre = new JLabel(jugador.getNombre());
        labelJugadorNombre.setForeground(new Color(230, 230, 230));
        labelJugadorNombre.setFont(new Font("Arial", Font.BOLD, 16));

        barraJugadorVida = new JProgressBar(0, jugador.getVidaMaxima());
        barraJugadorVida.setValue(jugador.getVida());
        barraJugadorVida.setStringPainted(true);
        barraJugadorVida.setForeground(new Color(0, 170, 0));

        labelEnemigoNombre = new JLabel(enemigoActual.getNombre());
        labelEnemigoNombre.setForeground(new Color(230, 230, 230));
        labelEnemigoNombre.setFont(new Font("Arial", Font.BOLD, 16));

        barraEnemigoVida = new JProgressBar(0, enemigoActual.getVidaMaxima());
        barraEnemigoVida.setValue(enemigoActual.getVida());
        barraEnemigoVida.setStringPainted(true);
        barraEnemigoVida.setForeground(new Color(180, 0, 0));

        panelInfo.add(labelJugadorNombre);
        panelInfo.add(labelEnemigoNombre);
        panelInfo.add(barraJugadorVida);
        panelInfo.add(barraEnemigoVida);
        add(panelInfo, BorderLayout.NORTH);

        // Panel central: Área de mensajes
        areaMensajes = new JTextArea();
        areaMensajes.setEditable(false);
        areaMensajes.setBackground(new Color(40, 40, 50));
        areaMensajes.setForeground(new Color(230, 230, 230));
        areaMensajes.setFont(new Font("Consolas", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(areaMensajes);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(180, 0, 0), 1));
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior: Botones de acción
        JPanel panelBotones = new JPanel(new GridLayout(2, 6, 10, 10));
        panelBotones.setBackground(new Color(35, 35, 45));
        panelBotones.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 0, 0), 1),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));

        JButton btnAtacar = crearBotonEstilizado("Atacar", new Color(170, 0, 0), "/Resources/Golpe.jpg", 64, 64);
        JButton btnCurar = crearBotonEstilizado("Curarse", new Color(0, 130, 0), "/Resources/Curacion.jpg", 64, 64);
        JButton btnHabilidad = crearBotonEstilizado("Habilidad Especial", new Color(0, 0, 150), "/Resources/Habilidad.jpg", 64, 64);
        JButton btnFatality = crearBotonEstilizado("Fatality", new Color(100, 0, 0), "/Resources/Fatality.jpg", 64, 64);
        JButton btnCombo = crearBotonEstilizado("Combo", new Color(130, 0, 130), "/Resources/Golpe.jpg", 64, 64);
        JButton btnBolsa = crearBotonEstilizado("Ver Bolsa", new Color(150, 75, 0), "/Resources/Golpe.jpg", 64, 64);

        panelBotones.add(btnAtacar);
        panelBotones.add(btnCurar);
        panelBotones.add(btnHabilidad);
        panelBotones.add(btnFatality);
        panelBotones.add(btnCombo);
        panelBotones.add(btnBolsa);
        add(panelBotones, BorderLayout.SOUTH);

        agregarListeners(btnAtacar, btnCurar, btnHabilidad, btnFatality, btnCombo, btnBolsa);
    }

    private JButton crearBotonEstilizado(String texto, Color colorFondo, String rutaImagen, int anchoImagen, int altoImagen) {
        JButton boton = new JButton(texto);
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);

        if (rutaImagen != null) {
            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(rutaImagen));
            Image imagenRedimensionada = iconoOriginal.getImage().getScaledInstance(anchoImagen, altoImagen, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(imagenRedimensionada));
            boton.setHorizontalTextPosition(SwingConstants.CENTER);
            boton.setVerticalTextPosition(SwingConstants.BOTTOM);
        }

        boton.setPreferredSize(new Dimension(100, 100));
        return boton;
    }

    private void agregarListeners(JButton btnAtacar, JButton btnCurar, JButton btnHabilidad, JButton btnFatality, JButton btnCombo, JButton btnBolsa) {
        btnAtacar.addActionListener(e -> turnoJugador("atacar"));
        btnCurar.addActionListener(e -> turnoJugador("curar"));
        btnHabilidad.addActionListener(e -> turnoJugador("habilidad"));
        btnFatality.addActionListener(e -> realizarFatality(btnFatality));
        btnCombo.addActionListener(e -> usarCombo(btnCombo));
        btnBolsa.addActionListener(e -> mostrarBolsa());
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
            JOptionPane.showMessageDialog(this, "Tu bolsa está vacía.", "Bolsa de Objetos",
                    JOptionPane.INFORMATION_MESSAGE);
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