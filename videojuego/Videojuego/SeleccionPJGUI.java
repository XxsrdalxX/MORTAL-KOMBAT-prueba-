package Videojuego;

import Personajes.*;
import javax.swing.*;
import java.awt.*;

public class SeleccionPJGUI extends JFrame {

    private Personaje personajeSeleccionado;

    public SeleccionPJGUI() {
        setTitle("Selecciona tu personaje");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Permite cerrar solo esta ventana
        setLayout(new BorderLayout());

        // Título
        JLabel titulo = new JLabel("Selecciona tu personaje", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        // Panel de botones para los personajes
        JPanel panelPersonajes = new JPanel(new GridLayout(5, 1, 10, 10));
        JButton btnLiuKang = new JButton("Liu Kang");
        JButton btnRaiden = new JButton("Raiden");
        JButton btnScorpion = new JButton("Scorpion");
        JButton btnSubZero = new JButton("Sub-Zero");
        JButton btnKitana = new JButton("Kitana");

        panelPersonajes.add(btnLiuKang);
        panelPersonajes.add(btnRaiden);
        panelPersonajes.add(btnScorpion);
        panelPersonajes.add(btnSubZero);
        panelPersonajes.add(btnKitana);
        add(panelPersonajes, BorderLayout.CENTER);

        // Listeners para los botones
        btnLiuKang.addActionListener(e -> seleccionarPersonaje(new LiuKang(100, 5, Estados.NORMAL, 30, "Liu Kang")));
        btnRaiden.addActionListener(e -> seleccionarPersonaje(new Raiden(120, 5, Estados.NORMAL, 25, "Raiden")));
        btnScorpion.addActionListener(e -> seleccionarPersonaje(new Scorpion("Fuego", 5, 110, Estados.NORMAL, 35, "Scorpion")));
        btnSubZero.addActionListener(e -> seleccionarPersonaje(new SubZero("Hielo", 5, 115, Estados.NORMAL, 28, "Sub-Zero")));
        btnKitana.addActionListener(e -> seleccionarPersonaje(new Kitana(90, 5, Estados.NORMAL, 40, "Kitana")));

        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setVisible(true); // Muestra la ventana
    }

    private void seleccionarPersonaje(Personaje personaje) {
        personajeSeleccionado = personaje;
        JOptionPane.showMessageDialog(this, "Has seleccionado a " + personaje.getNombre());
        dispose(); // Cierra la ventana
    }

    public Personaje getPersonajeSeleccionado() {
        return personajeSeleccionado;
    }
}