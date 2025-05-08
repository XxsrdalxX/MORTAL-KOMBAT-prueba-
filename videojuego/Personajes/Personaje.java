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

    public int getPoder() {
        return poder;
    }

    public void setPoder(int poder) {
        this.poder = Math.max(0, poder); // Asegura que el poder no sea negativo
    }

    public int getResistencia() {
        return defensa; // La resistencia está representada por la defensa
    }

    public void setResistencia(int resistencia) {
        this.defensa = Math.max(0, resistencia); // Asegura que la resistencia no sea negativa
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

    public void setVidaMaxima(int vidaMaxima) {
        this.vidaMaxima = Math.max(0, vidaMaxima); // Asegura que la vida máxima no sea negativa
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public String getNombre() {
        return nombre;
    }

    // Reducir turnos del estado
    public void reducirTurnosEstado(JTextArea areaMensajes) {
        if (turnosEstado > 0) {
            turnosEstado--;
            if (turnosEstado == 0) {
                estado = Estados.NORMAL; // Vuelve al estado normal

                // Registrar el mensaje en el área de texto
                String mensaje = nombre + " ya no está afectado por ningún estado.";
                areaMensajes.append(mensaje + "\n");
            }
        }
    }

    // Métodos de combate
    public void atacar(Personaje objetivo, JTextArea areaMensajes) {
        int daño = Math.max(0, poder - objetivo.defensa);
        objetivo.setVida(objetivo.getVida() - daño);

        // Registrar el mensaje en el área de texto
        String mensaje = nombre + " atacó a " + objetivo.getNombre() + " causando " + daño + " de daño.";
        areaMensajes.append(mensaje + "\n");
    }

    public void curar(JTextArea areaMensajes) {
        int curacion = 20; // Cantidad fija de curación
        setVida(vida + curacion);

        // Registrar el mensaje en el área de texto
        String mensaje = nombre + " se curó " + curacion + " puntos de vida.";
        areaMensajes.append(mensaje + "\n");
    }

    // Método abstracto para la habilidad especial
    public abstract void habilidadEspecial(Personaje objetivo, JTextArea areaMensajes);
}