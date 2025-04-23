package Personajes;
import Videojuego.Estados;
import javax.swing.JOptionPane;

public class SubZero extends Personaje {
    private int cooldownHabilidad = 0; // Cooldown de la habilidad especial

    public SubZero(String tipo, int vida, int defensa, Estados estado, int poder, String nombre) {
        super(vida, defensa, estado, poder, nombre);
    }

    @Override
    public void habilidadEspecial(Personaje objetivo) {
        if (cooldownHabilidad > 0) {
            JOptionPane.showMessageDialog(null, nombre + " no puede usar su habilidad especial. Cooldown restante: " + cooldownHabilidad + " turnos.", "Cooldown", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (objetivo.getEstado() == Estados.NORMAL) {
            JOptionPane.showMessageDialog(null, nombre + " usa su habilidad especial: ¡Congelación!", "Habilidad Especial", JOptionPane.INFORMATION_MESSAGE);
            objetivo.setEstado(Estados.CONGELADO, 2); // Congela al objetivo por 2 turnos
            cooldownHabilidad = 3; // Cooldown de 3 turnos
        } else {
            JOptionPane.showMessageDialog(null, objetivo.getNombre() + " ya está afectado por un estado.", "Estado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void reducirCooldown() {
        if (cooldownHabilidad > 0) {
            cooldownHabilidad--;
        }
    }
}