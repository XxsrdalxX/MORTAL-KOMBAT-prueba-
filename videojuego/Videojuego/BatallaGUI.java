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
        setTitle("☠️ Batalla Mortal Kombat ☠️");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(25, 25, 30));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel superior: Información de los personajes y barras de vida
        JPanel panelInfo = new JPanel(new GridLayout(2, 2, 8, 5));
        panelInfo.setBackground(new Color(35, 35, 45));
        panelInfo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 0, 0), 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        // Etiquetas y barras de vida con estilo
        labelJugadorNombre = new JLabel("Jugador: " + jugador.getNombre());
        labelJugadorNombre.setForeground(new Color(230, 230, 230));
        labelJugadorNombre.setFont(new Font("Arial", Font.BOLD, 16));

        barraJugadorVida = new JProgressBar(0, jugador.getVidaMaxima());
        barraJugadorVida.setValue(jugador.getVida());
        barraJugadorVida.setStringPainted(true);
        barraJugadorVida.setForeground(new Color(0, 170, 0));
        barraJugadorVida.setBackground(new Color(50, 50, 60));
        barraJugadorVida.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        labelEnemigoNombre = new JLabel("Enemigo: " + enemigo.getNombre());
        labelEnemigoNombre.setForeground(new Color(230, 230, 230));
        labelEnemigoNombre.setFont(new Font("Arial", Font.BOLD, 16));

        barraEnemigoVida = new JProgressBar(0, enemigo.getVidaMaxima());
        barraEnemigoVida.setValue(enemigo.getVida());
        barraEnemigoVida.setStringPainted(true);
        barraEnemigoVida.setForeground(new Color(180, 0, 0));
        barraEnemigoVida.setBackground(new Color(50, 50, 60));
        barraEnemigoVida.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

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
        areaMensajes.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(areaMensajes);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(180, 0, 0), 1));
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior: Botones de acción con colores
        JPanel panelBotones = new JPanel(new GridLayout(2, 3, 10, 10));
        panelBotones.setBackground(new Color(35, 35, 45));
        panelBotones.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 0, 0), 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        JButton btnAtacar = crearBotonEstilizado("Atacar", new Color(170, 0, 0), "/Resources/Golpe.jpg", 48, 48);
        JButton btnCurar = crearBotonEstilizado("Curarse", new Color(0, 130, 0), "/Resources/Curacion.jpg", 48, 48);
        JButton btnHabilidad = crearBotonEstilizado("Habilidad Especial", new Color(0, 0, 150), "/Resources/Habilidad.jpg", 48, 48);
        JButton btnCombo = crearBotonEstilizado("Combo", new Color(130, 0, 130), "/Resources/Golpe.jpg", 48, 48);
        JButton btnFatality = crearBotonEstilizado("Fatality", new Color(100, 0, 0), "/Resources/Fatality.jpg", 48, 48);
       

        panelBotones.add(btnAtacar);
        panelBotones.add(btnCurar);
        panelBotones.add(btnHabilidad);
        panelBotones.add(btnCombo);
        panelBotones.add(btnFatality);
      
        add(panelBotones, BorderLayout.SOUTH);

        // Deshabilitar botones al inicio
        btnCombo.setEnabled(false);
        btnFatality.setEnabled(false);

        // Agregar listeners a los botones
        agregarListeners(btnAtacar, btnCurar, btnHabilidad, btnFatality, btnCombo );
    }

    // Crea un botón estilizado con color e imagen
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

    // Agrega los listeners a los botones
    private void agregarListeners(JButton btnAtacar, JButton btnCurar, JButton btnHabilidad, JButton btnFatality,
            JButton btnCombo ) {
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