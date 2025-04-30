package Personajes;

import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import Videojuego.Estados;

public abstract class Personaje {
    protected String nombre;
    protected int vida;
    protected int vidaMaxima;
    protected int defensa;
    protected int poder;
    protected Estados estado = Estados.NORMAL; // Estado inicial
    protected int turnosEstado = 0; // Turnos restantes para el estado actual

    // Constructor
    public Personaje(int vida, int defensa, Estados estado, int poder, String nombre) {
        this.vida = vida;
        this.vidaMaxima = vida;
        this.defensa = defensa;
        this.estado = estado;
        this.poder = poder;
        this.nombre = nombre;
    }

    // Getters y setters
    public int getTurnosEstado() {
        return turnosEstado;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado, int turnos) {
        this.estado = estado;
        this.turnosEstado = turnos;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = Math.max(0, Math.min(vida, vidaMaxima)); // Asegura que la vida esté entre 0 y el máximo
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public String getNombre() {
        return nombre;
    }

    // Reducir turnos del estado
    public void reducirTurnosEstado() {
        if (turnosEstado > 0) {
            turnosEstado--;
            if (turnosEstado == 0) {
                estado = Estados.NORMAL; // Vuelve al estado normal
                JOptionPane.showMessageDialog(null, nombre + " ya no está afectado por ningún estado.", "Estado",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // Métodos de combate
    public void atacar(Personaje objetivo) {
        int daño = Math.max(0, poder - objetivo.defensa);
        objetivo.setVida(objetivo.getVida() - daño);
        JOptionPane.showMessageDialog(null,
                nombre + " atacó a " + objetivo.getNombre() + " causando " + daño + " de daño.", "Ataque",
                JOptionPane.INFORMATION_MESSAGE);

    }

    public void curar() {
        int curacion = 20; // Cantidad fija de curación
        setVida(vida + curacion);
        JOptionPane.showMessageDialog(null, nombre + " se curó " + curacion + " puntos de vida.", "Curación",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Método abstracto para la habilidad especial
    public abstract void habilidadEspecial(Personaje objetivo, JTextArea areaMensajes);
}