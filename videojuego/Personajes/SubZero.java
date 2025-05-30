package Personajes;

import Videojuego.Estados;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class SubZero extends Personaje {
    private int cooldownHabilidad = 0; // Cooldown de la habilidad especial

    public SubZero(String tipo, int vida, int defensa, Estados estado, int poder, String nombre) {
        super(vida, defensa, estado, poder, nombre);
    }

    @Override
    public void habilidadEspecial(Personaje objetivo, JTextArea areaMensajes) {
        if (cooldownHabilidad > 0) {
            // Registrar el mensaje en el área de texto si la habilidad está en cooldown
            String mensaje = nombre + " no puede usar su habilidad especial. Cooldown restante: " + cooldownHabilidad + " turnos.";
            areaMensajes.append(mensaje + "\n");
            reducirCooldown();
            return;
        }
    
        if (objetivo.getEstado() == Estados.NORMAL) {
            // Usar la habilidad especial
            String mensaje = nombre + " usa su habilidad especial: ¡Congelación!";
            areaMensajes.append(mensaje + "\n");
            objetivo.setEstado(Estados.CONGELADO, 2); // Congela al objetivo por 2 turnos
            cooldownHabilidad = 3; // Cooldown de 3 turnos
        } else {
            // Registrar el mensaje si el objetivo ya está afectado por un estado
            String mensaje = objetivo.getNombre() + " ya está afectado por un estado.";
            areaMensajes.append(mensaje + "\n");
        }
    }

    public void reducirCooldown() {
        if (cooldownHabilidad > 0) {
            cooldownHabilidad--;
        }
    }
}