package Videojuego;

import Personajes.*;
import javax.swing.*;
import java.awt.*;

public class Videojuego {

    public static void main(String[] args) {
        // Crear la ventana principal
        JFrame ventanaPrincipal = new JFrame("Mortal Kombat");
        ventanaPrincipal.setSize(400, 300);
        ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaPrincipal.setLayout(new BorderLayout());

        // Título del juego
        JLabel titulo = new JLabel("¡Bienvenido a Mortal Kombat!", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        ventanaPrincipal.add(titulo, BorderLayout.NORTH);

        // Panel de botones para seleccionar el modo de juego
        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 10, 10));
        JButton btnModoHistoria = new JButton("Modo Historia (Torres)");
        JButton btnBatallaRapida = new JButton("Batalla Rápida");
        panelBotones.add(btnModoHistoria);
        panelBotones.add(btnBatallaRapida);
        ventanaPrincipal.add(panelBotones, BorderLayout.CENTER);

        // Acción para el botón "Modo Historia"
        btnModoHistoria.addActionListener(e -> {
            ventanaPrincipal.dispose(); // Cierra la ventana principal
            SeleccionPJGUI seleccion = new SeleccionPJGUI();
            seleccion.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    Personaje jugador1 = seleccion.getPersonajeSeleccionado(); // Selección de personaje
                    if (jugador1 != null) {
                        new ModoHistoria(jugador1); // Inicia el modo historia
                    }
                }
            });
        });

        btnBatallaRapida.addActionListener(e -> {
            ventanaPrincipal.dispose(); // Cierra la ventana principal
            SeleccionPJGUI seleccion = new SeleccionPJGUI();
            seleccion.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    Personaje jugador1 = seleccion.getPersonajeSeleccionado(); // Selección de personaje
                    if (jugador1 != null) {
                        IAbot ia = new IAbot();
                        Personaje cpu = ia.seleccionarPersonajeIA(); // Selección del enemigo
                        new BatallaGUI(jugador1, cpu); // Inicia la batalla rápida
                    } else {
                        JOptionPane.showMessageDialog(null, "No seleccionaste un personaje. Cerrando el juego.");
                    }
                }
            });
        });
        // Mostrar la ventana principal
        ventanaPrincipal.setVisible(true);

    }
}