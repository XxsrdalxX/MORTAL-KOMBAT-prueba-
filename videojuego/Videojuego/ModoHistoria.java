package Videojuego;

import Personajes.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ModoHistoria extends JFrame {
    private Personaje jugador;
    private List<Personaje> enemigos;
    private Personaje enemigoActual;
    private JTextArea areaMensajes;
    private JLabel labelJugadorVida, labelEnemigoVida;

    public ModoHistoria(Personaje jugador) {
        this.jugador = jugador;
        this.enemigos = crearEnemigos();
        this.enemigoActual = enemigos.get(0);

        setTitle("Modo Historia - Mortal Kombat");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior: Información de los personajes
        JPanel panelInfo = new JPanel(new GridLayout(2, 2));
        labelJugadorVida = new JLabel("Jugador - Vida: " + jugador.getVida());
        labelEnemigoVida = new JLabel("Enemigo - Vida: " + enemigoActual.getVida());
        panelInfo.add(labelJugadorVida);
        panelInfo.add(labelEnemigoVida);
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

    private List<Personaje> crearEnemigos() {
        List<Personaje> enemigos = new ArrayList<>();
        enemigos.add(new Raiden(100, 10, Estados.NORMAL, 15, "Raiden"));
        enemigos.add(new Kitana(120, 8, Estados.NORMAL, 20, "Kitana"));
        enemigos.add(new Raiden(150, 12, Estados.NORMAL, 25, "Sub-Zero"));
        enemigos.add(new Kitana(200, 15, Estados.NORMAL, 30, "Shao Kahn")); // Jefe final
        return enemigos;
    }

    private void actualizarInfo() {
        labelJugadorVida.setText("Jugador - Vida: " + jugador.getVida());
        labelEnemigoVida.setText("Enemigo - Vida: " + enemigoActual.getVida());
    }

    private void atacar() {
        jugador.atacar(enemigoActual);
        areaMensajes.append(jugador.getNombre() + " atacó a " + enemigoActual.getNombre() + "\n");
        verificarEstado();
    }

    private void curar() {
        jugador.curar();
        areaMensajes.append(jugador.getNombre() + " se curó.\n");
        actualizarInfo();
    }

    private void usarHabilidadEspecial() {
        jugador.habilidadEspecial(enemigoActual);
        areaMensajes.append(jugador.getNombre() + " usó su habilidad especial contra " + enemigoActual.getNombre() + "\n");
        verificarEstado();
    }

    private void verificarEstado() {
        if (enemigoActual.getVida() <= 0) {
            areaMensajes.append("¡Has derrotado a " + enemigoActual.getNombre() + "!\n");
            enemigos.remove(enemigoActual);
            if (enemigos.isEmpty()) {
                areaMensajes.append("¡Felicidades! Has completado el modo historia.\n");
                return;
            }
            enemigoActual = enemigos.get(0);
            areaMensajes.append("¡Nuevo enemigo: " + enemigoActual.getNombre() + "!\n");
        }

        if (jugador.getVida() <= 0) {
            areaMensajes.append("Has sido derrotado. Fin del modo historia.\n");
        }

        actualizarInfo();
    }
}