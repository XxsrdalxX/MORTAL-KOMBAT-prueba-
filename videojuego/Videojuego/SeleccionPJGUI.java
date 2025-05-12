package Videojuego;

import Personajes.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Ventana de selección de personajes para el juego Mortal Kombat
 * Permite al usuario elegir entre varios personajes clásicos de la saga
 */
public class SeleccionPJGUI extends JFrame {

    // Constantes para los colores del estilo Mortal Kombat
    private static final Color MK_NEGRO = new Color(10, 10, 10);
    private static final Color MK_DORADO = new Color(255, 215, 0);
    private static final Color MK_ROJO = new Color(187, 0, 0);
    
    // Dimensiones de los botones y márgenes
    private static final int ANCHO_BOTON_IMAGEN = 150;
    private static final int ALTO_BOTON_IMAGEN = 150;
    private static final int MARGEN = 15;
    
    // Fuente personalizada para el estilo MK
    private static final Font FUENTE_TITULO = new Font("Arial", Font.BOLD, 28);
    private static final Font FUENTE_BOTON = new Font("Arial", Font.BOLD, 16);
    
    // Almacena el personaje seleccionado por el usuario
    private Personaje personajeSeleccionado;

    /**
     * Constructor: configura la ventana de selección de personajes
     */
    public SeleccionPJGUI() {
        inicializarVentana();
        configurarComponentes();
    }
    
    /**
     * Configura las propiedades básicas de la ventana
     */
    private void inicializarVentana() {
        setTitle("MORTAL KOMBAT - Selección de Personajes");
        setSize(1000, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0, MARGEN));
        getContentPane().setBackground(MK_NEGRO);
    }
    
    /**
     * Configura y añade todos los componentes de la interfaz
     */
    private void configurarComponentes() {
        // Panel de título con estilo MK
        JPanel panelTitulo = crearPanelTitulo();
        add(panelTitulo, BorderLayout.NORTH);

        // Panel de selección de personajes
        JPanel panelPersonajes = crearPanelPersonajes();
        add(panelPersonajes, BorderLayout.CENTER);
        
        // Panel inferior (espacio)
        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(MK_NEGRO);
        panelInferior.setPreferredSize(new Dimension(getWidth(), 50));
        add(panelInferior, BorderLayout.SOUTH);

        // Centrar y mostrar ventana
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    /**
     * Crea el panel con el título estilizado
     */
    private JPanel crearPanelTitulo() {
        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTitulo.setBackground(MK_NEGRO);
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(MARGEN, 0, MARGEN, 0));
        
        JLabel titulo = new JLabel("SELECCIONA TU LUCHADOR", SwingConstants.CENTER);
        titulo.setFont(FUENTE_TITULO);
        titulo.setForeground(MK_DORADO);
        
        panelTitulo.add(titulo);
        return panelTitulo;
    }
    
    /**
     * Crea el panel que contiene los botones de personajes
     */
    private JPanel crearPanelPersonajes() {
        JPanel panelExterno = new JPanel(new FlowLayout(FlowLayout.CENTER, MARGEN, MARGEN));
        panelExterno.setBackground(MK_NEGRO);
        
        JPanel panelPersonajes = new JPanel(new GridLayout(1, 5, MARGEN, MARGEN));
        panelPersonajes.setBackground(MK_NEGRO);
        
        // Crear botones para cada personaje
        JButton btnLiuKang = crearBotonPersonaje("Liu Kang", "/Resources/LiuKang.jpg",
                e -> seleccionarPersonaje(new LiuKang(100, 10, Estados.NORMAL, 20, "Liu Kang")));
                
        JButton btnRaiden = crearBotonPersonaje("Raiden", "/Resources/Raiden.jpg",
                e -> seleccionarPersonaje(new Raiden(100, 10, Estados.NORMAL, 35, "Raiden")));
                
        JButton btnScorpion = crearBotonPersonaje("Scorpion", "/Resources/Scorpion.jpg",
                e -> seleccionarPersonaje(new Scorpion("Fuego", 5, 100, Estados.NORMAL, 35, "Scorpion")));
                
        JButton btnSubZero = crearBotonPersonaje("Sub-Zero", "/Resources/SubZero.jpg",
                e -> seleccionarPersonaje(new SubZero("Hielo", 100, 10, Estados.NORMAL, 35, "Sub-Zero")));
                
        JButton btnKitana = crearBotonPersonaje("Kitana", "/Resources/Kitana.jpg",
                e -> seleccionarPersonaje(new Kitana(90, 10, Estados.NORMAL, 20, "Kitana")));
        
        // Añadir botones al panel
        panelPersonajes.add(btnLiuKang);
        panelPersonajes.add(btnRaiden);
        panelPersonajes.add(btnScorpion);
        panelPersonajes.add(btnSubZero);
        panelPersonajes.add(btnKitana);
        
        panelExterno.add(panelPersonajes);
        return panelExterno;
    }
    
    /**
     * Crea un botón personalizado para la selección de personaje
     */
    private JButton crearBotonPersonaje(String nombre, String rutaImagen, ActionListener accion) {
        JButton boton = new JButton();
        
        // Configuración de aspecto
        boton.setLayout(new BorderLayout());
        boton.setBackground(MK_NEGRO);
        boton.setBorder(BorderFactory.createLineBorder(MK_DORADO, 2));
        boton.setFocusPainted(false);
        
        // Configurar imagen
        configurarImagenBoton(boton, rutaImagen);
        
        // Configurar texto
        JLabel etiquetaNombre = new JLabel(nombre, SwingConstants.CENTER);
        etiquetaNombre.setFont(FUENTE_BOTON);
        etiquetaNombre.setForeground(MK_DORADO);
        etiquetaNombre.setBackground(MK_NEGRO);
        etiquetaNombre.setOpaque(true);
        
        boton.add(etiquetaNombre, BorderLayout.SOUTH);
        
        // Efectos de hover (cambio de color al pasar el ratón)
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBorder(BorderFactory.createLineBorder(MK_ROJO, 3));
                etiquetaNombre.setForeground(MK_ROJO);
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBorder(BorderFactory.createLineBorder(MK_DORADO, 2));
                etiquetaNombre.setForeground(MK_DORADO);
            }
        });
        
        // Añadir acción
        boton.addActionListener(accion);
        
        return boton;
    }

    /**
     * Configura la imagen del botón de personaje
     */
    private void configurarImagenBoton(JButton boton, String ruta) {
        try {
            ImageIcon icono = new ImageIcon(getClass().getResource(ruta));
            Image img = icono.getImage(); 
            Image imgRedimensionada = img.getScaledInstance(ANCHO_BOTON_IMAGEN, ALTO_BOTON_IMAGEN, Image.SCALE_SMOOTH);
            JLabel labelImagen = new JLabel(new ImageIcon(imgRedimensionada));
            labelImagen.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            labelImagen.setBackground(MK_NEGRO);
            labelImagen.setOpaque(true);
            boton.add(labelImagen, BorderLayout.CENTER);
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen: " + ruta);
            boton.setText("Error imagen");
        }
    }
    
    /**
     * Maneja la selección de un personaje
     */
    private void seleccionarPersonaje(Personaje personaje) {
        personajeSeleccionado = personaje;
        
        // Crear un panel personalizado para el mensaje
        JPanel panelMensaje = new JPanel(new BorderLayout(10, 10));
        panelMensaje.setBackground(MK_NEGRO);
        panelMensaje.setBorder(BorderFactory.createLineBorder(MK_DORADO, 2));
        
        JLabel mensaje = new JLabel("Has seleccionado a " + personaje.getNombre(), SwingConstants.CENTER);
        mensaje.setFont(new Font("Arial", Font.BOLD, 18));
        mensaje.setForeground(MK_DORADO);
        mensaje.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        panelMensaje.add(mensaje, BorderLayout.CENTER);
        
        // Mostrar diálogo personalizado
        JOptionPane.showOptionDialog(
            this, 
            panelMensaje, 
            "KOMBATIENTE SELECCIONADO", 
            JOptionPane.DEFAULT_OPTION, 
            JOptionPane.PLAIN_MESSAGE, 
            null, 
            new Object[]{"KOMBATIR"}, 
            null
        );
        
        dispose(); // Cierra la ventana
    }

    /**
     * Devuelve el personaje seleccionado por el usuario
     */
    public Personaje getPersonajeSeleccionado() {
        return personajeSeleccionado;
    }
}