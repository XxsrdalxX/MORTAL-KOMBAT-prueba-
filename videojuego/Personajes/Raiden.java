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
            JOptionPane.showMessageDialog(null, nombre + " no puede usar su habilidad especial. Cooldown restante: "
                    + cooldownHabilidad + " turnos.", "Cooldown", JOptionPane.INFORMATION_MESSAGE);
            reducirCooldown();
            return;
        }

        if (objetivo.getEstado() == Estados.NORMAL) {
            JOptionPane.showMessageDialog(null, nombre + " usa su habilidad especial: ¡Rayo Destructor!",
                    "Habilidad Especial", JOptionPane.INFORMATION_MESSAGE);
            objetivo.setEstado(Estados.PARALIZADO, 2); // Paraliza al objetivo por 2 turnos
            cooldownHabilidad = 2; // Cooldown de 2 turnos
        } else {
            JOptionPane.showMessageDialog(null, objetivo.getNombre() + " ya está afectado por un estado.", "Estado",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void reducirCooldown() {
        if (cooldownHabilidad > 0) {
            cooldownHabilidad--;
        }
    }
}