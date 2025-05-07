package Personajes;

import Videojuego.Estados;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Raiden extends Personaje {
    private int cooldownHabilidad = 0; // Cooldown de la habilidad especial

    public Raiden(int vida, int defensa, Estados estado, int poder, String nombre) {
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
        String mensaje = nombre + " usa su habilidad especial: ¡Rayo Destructor!";
        areaMensajes.append(mensaje + "\n");
        objetivo.setEstado(Estados.PARALIZADO, 2); // Paraliza al objetivo por 2 turnos
        cooldownHabilidad = 2; // Cooldown de 2 turnos
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