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

    // APLICAR EFECTO PARA QUEMAR AL OBJETIVO
    public void Quemar(Personaje objetivo, JTextArea areaMensajes) {
        if (objetivo.getEstado() != Estados.QUEMADO) {
            objetivo.setEstado(Estados.QUEMADO, 5); // Cambiar el estado del objetivo a QUEMADO por 5 turnos
            areaMensajes.append(this.nombre + " quema a " + objetivo.getNombre() + " con su ataque de fuego!\n");
            areaMensajes.append("¡" + objetivo.getNombre() + " perderá vida durante 5 turnos!\n");

            // Simular el daño por quemadura durante 5 turnos
            for (int i = 1; i <= 5; i++) {
                try {
                    Thread.sleep(1000); // Simula un retraso de 1 segundo entre turnos
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                objetivo.setVida(objetivo.getVida() - 1); // Resta 1 de vida al objetivo
                areaMensajes.append("Turno " + i + ": " + objetivo.getNombre() + " pierde 1 de vida. Vida restante: "
                        + objetivo.getVida() + "\n");

                // Verificar si el objetivo ha sido derrotado
                if (objetivo.getVida() <= 0) {
                    areaMensajes.append(
                            objetivo.getNombre() + " ha sido derrotado por el fuego de " + this.nombre + "!\n");
                    break;
                }
            }
        } else {
            areaMensajes.append(objetivo.getNombre() + " ya está quemado y no puede ser quemado de nuevo.\n");
        }
    }

    @Override
    public void habilidadEspecial(Personaje objetivo, JTextArea areaMensajes) {
        areaMensajes.append(this.nombre + " usa LANZA INFERNAL\n");
        this.Quemar(objetivo, areaMensajes);
    }
}