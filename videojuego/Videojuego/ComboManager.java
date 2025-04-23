package Videojuego;

import javax.swing.*;

import Personajes.Personaje;
import java.util.Random;
import java.util.HashMap;
import java.awt.*;

public class ComboManager {
    private HashMap<String, Runnable> combos; // Almacena los combos y sus efectos
    private JTextArea areaMensajes; // Referencia al área de mensajes para mostrar resultados
    private Personaje jugador;
    private Personaje enemigoActual;

    public ComboManager(JTextArea areaMensajes, Personaje jugador, Personaje enemigoActual) {
        this.jugador = jugador;
        this.enemigoActual = enemigoActual;
        this.areaMensajes = areaMensajes;
        inicializarCombos();
    }

    // Inicializa los combos disponibles
    private void inicializarCombos() {
        combos = new HashMap<>();
        combos.put("WASD", () -> realizarCombo("Combo de Ataque Rápido", 50));
        combos.put("QWE", () -> realizarCombo("Combo de Habilidad Especial", 100));
        combos.put("ASD", () -> realizarCombo("Combo de Curación", -30)); // Curación
    }

    // Muestra una ventana para que el usuario ingrese un combo
    public void mostrarVentanaCombo() {
        String comboEsperado = obtenerComboAleatorio(); // Selecciona un combo aleatorio

        JDialog dialogoCombo = new JDialog((JFrame) SwingUtilities.getWindowAncestor(areaMensajes),
                "¡Ingresa el Combo!", true);
        dialogoCombo.setSize(800, 400);
        dialogoCombo.setLayout(new BorderLayout());
        dialogoCombo.setLocationRelativeTo(null);

        // Etiqueta con instrucciones
        JLabel labelInstrucciones = new JLabel("Ingresa el combo: " + comboEsperado, SwingConstants.CENTER);
        labelInstrucciones.setFont(new Font("Arial", Font.BOLD, 64)); // Cambiar tamaño del texto
        dialogoCombo.add(labelInstrucciones, BorderLayout.NORTH);

        // Campo de texto para ingresar el combo
        JTextField campoCombo = new JTextField();
        campoCombo.setHorizontalAlignment(JTextField.CENTER); // Centrar el texto ingresado
        campoCombo.setFont(new Font("Arial", Font.BOLD, 64)); // Cambiar tamaño del texto ingresado
        dialogoCombo.add(campoCombo, BorderLayout.CENTER);

        // Panel inferior con temporizador y botón confirmar
        JPanel panelInferior = new JPanel(new BorderLayout());

        // Etiqueta para mostrar el tiempo restante
        JLabel labelTemporizador = new JLabel("Tiempo restante: 10 segundos", SwingConstants.CENTER);
        labelTemporizador.setFont(new Font("Arial", Font.BOLD, 48));
        panelInferior.add(labelTemporizador, BorderLayout.NORTH);

        // Botón para confirmar el combo
        JButton btnConfirmar = new JButton("Confirmar");
        panelInferior.add(btnConfirmar, BorderLayout.SOUTH);

        dialogoCombo.add(panelInferior, BorderLayout.SOUTH);

        // Temporizador para actualizar el tiempo restante
        final int[] tiempoRestante = { 10 }; // Tiempo inicial en segundos
        Timer temporizador = new Timer(1000, e -> {
            tiempoRestante[0]--;
            labelTemporizador.setText("Tiempo restante: " + tiempoRestante[0] + " segundos");
            if (tiempoRestante[0] <= 0) {
                ((Timer) e.getSource()).stop(); // Detener el temporizador
                dialogoCombo.dispose(); // Cerrar el diálogo
                areaMensajes.append("Combo fallido: No ingresaste el combo a tiempo.\n");
            }
        });
        temporizador.start();

        // Acción del botón confirmar
        btnConfirmar.addActionListener(e -> {
            String comboIngresado = campoCombo.getText();
            if (comboIngresado.equals(comboEsperado)) {
                areaMensajes.append("¡Combo exitoso! Realizaste el combo: " + comboEsperado + "\n");
                ejecutarCombo(comboEsperado);
            } else {
                areaMensajes.append("Combo fallido: El combo ingresado es incorrecto.\n");
            }
            temporizador.stop(); // Detener el temporizador
            dialogoCombo.dispose(); // Cerrar el diálogo
        });

        dialogoCombo.setVisible(true);
    }

    // Ejecuta el efecto del combo
    private void ejecutarCombo(String combo) {
        if (combos.containsKey(combo)) {
            combos.get(combo).run();
        } else {
            areaMensajes.append("Combo desconocido.\n");
        }
    }

    // Lógica para realizar un combo
    private void realizarCombo(String nombreCombo, int efecto) {
        areaMensajes.append("¡" + nombreCombo + " ejecutado!\n");
        if (efecto > 0) {
            // Daño al enemigo
            enemigoActual.setVida(enemigoActual.getVida() - efecto);
            areaMensajes.append("El enemigo recibió " + efecto + " de daño.\n");
        } else {
            // Curación al jugador
            jugador.setVida(jugador.getVida() - efecto); // Efecto negativo para curar
            areaMensajes.append("El jugador se curó " + (-efecto) + " puntos de vida.\n");
        }

    }

    private String obtenerComboAleatorio() {
        Object[] keys = combos.keySet().toArray(); // Obtiene las claves del HashMap como un arreglo
        Random random = new Random();
        return (String) keys[random.nextInt(keys.length)]; // Selecciona una clave aleatoria
    }
}