package Videojuego;

import Personajes.*;
import javax.swing.*;
import java.awt.*;

public class SeleccionPJGUI extends JFrame {

    private Personaje personajeSeleccionado;

    public SeleccionPJGUI() {
        setTitle("Selecciona tu personaje");
        setSize(1000, 900);
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

        // Agregar imágenes a los botones
        configurarImagenBoton(btnLiuKang, "/Resources/LiuKang.jpg");
        configurarImagenBoton(btnRaiden, "/Resources/Raiden.jpg");
        configurarImagenBoton(btnScorpion, "/Resources/Scorpion.jpg");
        configurarImagenBoton(btnSubZero, "/Resources/SubZero.jpg");
        configurarImagenBoton(btnKitana, "/Resources/Kitana.jpg");

        // Agregar botones al panel
        panelPersonajes.add(btnLiuKang);
        panelPersonajes.add(btnRaiden);
        panelPersonajes.add(btnScorpion);
        panelPersonajes.add(btnSubZero);
        panelPersonajes.add(btnKitana);

        add(panelPersonajes, BorderLayout.CENTER);

        // Listeners para los botones
        btnLiuKang.addActionListener(e -> seleccionarPersonaje(new LiuKang(100, 10, Estados.NORMAL, 20, "Liu Kang")));
        btnRaiden.addActionListener(e -> seleccionarPersonaje(new Raiden(100, 10, Estados.NORMAL, 35, "Raiden")));
        btnScorpion.addActionListener(
                e -> seleccionarPersonaje(new Scorpion("Fuego", 5, 100, Estados.NORMAL, 35, "Scorpion")));
        btnSubZero.addActionListener(
                e -> seleccionarPersonaje(new SubZero("Hielo", 100, 10, Estados.NORMAL, 35, "Sub-Zero")));
        btnKitana.addActionListener(e -> seleccionarPersonaje(new Kitana(90, 10, Estados.NORMAL, 20, "Kitana")));

        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setVisible(true); // Muestra la ventana
    }

    // Método para configurar la imagen de un botón
    private void configurarImagenBoton(JButton boton, String rutaImagen) {
        ImageIcon icono = new ImageIcon(getClass().getResource(rutaImagen)); // Carga la imagen
        Image imagenEscalada = icono.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Escala la imagen
        boton.setIcon(new ImageIcon(imagenEscalada)); // Establece la imagen como ícono del botón
        boton.setHorizontalTextPosition(SwingConstants.CENTER); // Centra el texto
        boton.setVerticalTextPosition(SwingConstants.BOTTOM); // Coloca el texto debajo de la imagen
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