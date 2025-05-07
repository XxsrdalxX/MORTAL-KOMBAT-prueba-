package Videojuego;

import Personajes.*;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class BatallaGUI extends JFrame {
    private Personaje jugador;
    private Personaje enemigo;
    private JTextArea areaMensajes;
    private JProgressBar barraJugadorVida, barraEnemigoVida;
    private IAbot iaBot = new IAbot(); // Instancia de la IA para el enemigo
    private int contadorTurnos = 0; // Contador de turnos
    private HashMap<String, Runnable> fatalitys; // Almacena los fatalitys y sus efectos
    



    private JLabel labelJugadorNombre, labelEnemigoNombre;

    public BatallaGUI(Personaje jugador, Personaje enemigo) {

        
        this.jugador = jugador;
        this.enemigo = enemigo;

        iniciarBatalla();
    }

    private void iniciarBatalla() {
        setTitle("Batalla Mortal Kombat");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior: Información de los personajes y barras de vida
        JPanel panelInfo = new JPanel(new GridLayout(2, 2));
        inicializarBarrasDeVida(panelInfo);
        add(panelInfo, BorderLayout.NORTH);

        // Panel central: Área de mensajes
        areaMensajes = new JTextArea();
        areaMensajes.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaMensajes);
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior: Botones de acción
        JPanel panelBotones = new JPanel(new GridLayout(2, 3));
        JButton btnAtacar = new JButton("Atacar");
        JButton btnCurar = new JButton("Curarse");
        JButton btnHabilidad = new JButton("Habilidad Especial");
        JButton btnCombo = new JButton("Combo");
        JButton btnFatality = new JButton("Fatality");
        JButton btnSalir = new JButton("Salir");
      
        panelBotones.add(btnAtacar);
        panelBotones.add(btnCurar);
        panelBotones.add(btnHabilidad);
        panelBotones.add(btnCombo);
        panelBotones.add(btnFatality);
        panelBotones.add(btnSalir);
        add(panelBotones, BorderLayout.SOUTH);

        // Listeners para los botones
        btnAtacar.addActionListener(e -> atacar());
        btnCurar.addActionListener(e -> curar());
        btnHabilidad.addActionListener(e -> usarHabilidadEspecial());
        btnFatality.addActionListener(e -> realizarFatality(btnFatality));
        btnCombo.addActionListener(e -> usarCombo(btnCombo));
        btnCombo.setEnabled(false); // Deshabilitar el botón Combo al inicio
        btnFatality.setEnabled(false); // Deshabilitar el botón Fatality al inicio
 // Agregar listeners a los botones
 agregarListeners(btnAtacar, btnCurar, btnHabilidad, btnFatality, btnCombo);

        setVisible(true);
    }

    
    private void agregarListeners(JButton btnAtacar, JButton btnCurar, JButton btnHabilidad, JButton btnFatality,
            JButton btnCombo) {
        btnAtacar.addActionListener(e -> turnoJugador("atacar"));
        btnCurar.addActionListener(e -> turnoJugador("curar"));
        btnHabilidad.addActionListener(e -> turnoJugador("habilidad"));
        btnFatality.addActionListener(e -> realizarFatality(btnFatality));
        btnCombo.addActionListener(e -> usarCombo(btnCombo));
    }


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

    private void actualizarBarrasDeVida() {
        barraJugadorVida.setValue(jugador.getVida());
        barraEnemigoVida.setValue(enemigo.getVida());
    }
    private void actualizarInfo() {
        // Actualizar nombres y barras de vida
        labelJugadorNombre.setText(jugador.getNombre() + " - Vida: " + jugador.getVida());
        barraJugadorVida.setValue(jugador.getVida());

        labelEnemigoNombre.setText(enemigo.getNombre() + " - Vida: " + enemigo.getVida());
        barraEnemigoVida.setValue(enemigo.getVida());
    }

    private void realizarFatality(JButton btnFatality) {
        if (enemigo.getVida() <= 30) {
            // Inicializar el mapa de fatalitys si no está inicializado
            if (fatalitys == null) {
                fatalitys = new HashMap<>();
                fatalitys.put("QWEERWWQ", () -> {
                    areaMensajes.append("¡Fatality!\n");
                    enemigo.setVida(0); // Matar al enemigo
                    areaMensajes.append("Has realizado un Fatality a " + enemigo.getNombre() + ".\n");
                    verificarEstado(); // Verificar si el enemigo ha sido derrotado
                });
            }

            // Mostrar la secuencia requerida al jugador
            String secuenciaRequerida = "QWEERWWQ"; 
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
        ComboManager comboManager = new ComboManager(areaMensajes, jugador, enemigo);
        comboManager.mostrarVentanaCombo(); // Llama al método que selecciona un combo aleatorio

        // Deshabilitar el botón de combo y reiniciar el contador de turnos
        btnCombo.setEnabled(false);
        contadorTurnos = 0; // Reiniciar el contador de turnos
        actualizarInfo(); // Actualizar las barras de vida
        verificarEstado();
    }

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
        jugador.atacar(enemigo);
        areaMensajes.append("P1: " + jugador.getNombre() + " atacó a " + enemigo.getNombre() + "\n");
        actualizarBarrasDeVida();
        verificarEstado();
        turnoEnemigo();
    }

    private void curar() {
        jugador.curar();
        areaMensajes.append("P1: " + jugador.getNombre() + " se curó.\n");
        actualizarBarrasDeVida();
        turnoEnemigo();
    }

    private void usarHabilidadEspecial() {
        jugador.habilidadEspecial(enemigo, areaMensajes);
        areaMensajes.append("P1: " + jugador.getNombre() + " usó su habilidad especial contra " + enemigo.getNombre() + "\n");
        actualizarBarrasDeVida();
        verificarEstado();
        turnoEnemigo();
    }

    private void turnoEnemigo() {
        if (enemigo.getEstado() == Estados.PARALIZADO) {
            areaMensajes.append("CPU: " + enemigo.getNombre() + " está paralizado y no puede actuar.\n");
            enemigo.reducirTurnosEstado(); // Reducir el efecto de parálisis
            return;
        }
    
        iaBot.decidirAccion(enemigo, jugador, areaMensajes); // El enemigo decide su acción
        actualizarBarrasDeVida(); // Actualizar las barras de vida después de la acción del enemigo
        verificarEstado(); // Verificar el estado después de la acción del enemigo
    }
    
    private void verificarEstado() {
        if (enemigo.getVida() <= 0) {
            areaMensajes.append("¡Has derrotado a " + enemigo.getNombre() + "!\n");
            JOptionPane.showMessageDialog(this, "¡Ganaste la batalla!");
            dispose(); // Cierra la ventana
        }

        if (jugador.getVida() <= 0) {
            areaMensajes.append("Has sido derrotado por " + enemigo.getNombre() + ".\n");
            JOptionPane.showMessageDialog(this, "¡Perdiste la batalla!");
            dispose(); // Cierra la ventana
        }
    }
}
    