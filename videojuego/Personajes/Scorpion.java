package Personajes;

import javax.swing.JTextArea;

import Videojuego.*;

public class Scorpion extends Personaje {

    public String tipo;

    public Scorpion(String tipo, int defensa, int vida, Estados estado, int poder, String nombre) {
        super(vida, defensa, estado, poder, nombre);
        this.tipo = "Fuego";
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public void habilidadEspecial(Personaje objetivo, JTextArea areaMensajes) {
        int dañoPorQuemadura = 5; // Daño inicial por quemadura
        objetivo.setVida(objetivo.getVida() - dañoPorQuemadura);
        objetivo.setEstado(Estados.QUEMADO, 1); // Quemar al objetivo por 3 turnos

        String mensaje = nombre + " usó su habilidad especial y quemó a " + objetivo.getNombre() + ".";
        areaMensajes.append(mensaje + "\n");
    }

}