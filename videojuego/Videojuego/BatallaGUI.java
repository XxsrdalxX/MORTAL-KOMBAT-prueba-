package Videojuego;

import Personajes.*;
import javax.swing.*;
import java.awt.*;

public class BatallaGUI extends JFrame {
    private Personaje jugador;
    private Personaje enemigo;
    private JTextArea areaMensajes;
    private JProgressBar barraJugadorVida, barraEnemigoVida;

    public BatallaGUI(Personaje jugador, Personaje enemigo) {
        this.jugador = jugador;
        this.enemigo = enemigo;

        setTitle("Batalla Mortal Kombat");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior: Información de los personajes y barras de vida
        JPanel panelInfo = new JPanel(new GridLayout(2, 1));
        inicializarBarrasDeVida(panelInfo);
        add(panelInfo, BorderLayout.NORTH);

        // Panel central: Área de mensajes
        areaMensajes = new JTextArea();
        areaMensajes.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaMensajes);
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior: Botones de acción
        JPanel panelBotones = new JPanel(new GridLayout(1, 3));
        JButton btnAtacar = new JButton("Atacar");
        JButton btnCurar = new JButton("Curarse");
        JButton btnHabilidad = new JButton("Habilidad Especial");
        panelBotones.add(btnAtacar);
        panelBotones.add(btnCurar);
        panelBotones.add(btnHabilidad);
        add(panelBotones, BorderLayout.SOUTH);

        // Listeners para los botones
        btnAtacar.addActionListener(e -> atacar());
        btnCurar.addActionListener(e -> curar());
        btnHabilidad.addActionListener(e -> usarHabilidadEspecial());

        setVisible(true);
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

    private void atacar() {
        jugador.atacar(enemigo);
        areaMensajes.append(jugador.getNombre() + " atacó a " + enemigo.getNombre() + "\n");
        actualizarBarrasDeVida();
        verificarEstado();
        turnoEnemigo();
    }

    private void curar() {
        jugador.curar();
        areaMensajes.append(jugador.getNombre() + " se curó.\n");
        actualizarBarrasDeVida();
        turnoEnemigo();
    }

    private void usarHabilidadEspecial() {
        jugador.habilidadEspecial(enemigo, areaMensajes);
        areaMensajes.append(jugador.getNombre() + " usó su habilidad especial contra " + enemigo.getNombre() + "\n");
        actualizarBarrasDeVida();
        verificarEstado();
        turnoEnemigo();
    }

    private void turnoEnemigo() {
        enemigo.atacar(jugador);
        areaMensajes.append(enemigo.getNombre() + " atacó a " + jugador.getNombre() + "\n");
        actualizarBarrasDeVida();
        verificarEstado();
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