package Personajes;

import Videojuego.Estados;
import javax.swing.*; // Importar Swing para la interfaz gráfica
import Videojuego.Estados; // Importar Videojuego para la clase Estados

public class Kitana extends Personaje {

    public Kitana(int vida, int defensa, Estados estado, int poder, String nombre) {
        super(vida, defensa, estado, poder, nombre);
    }

    @Override
    public void habilidadEspecial(Personaje objetivo, JTextArea areaMensajes) {
        // Lógica de la habilidad especial
        this.poder += 10; // Aumenta el poder como parte de la habilidad especial
        objetivo.setVida(objetivo.getVida() - this.poder); // Aplica daño al objetivo

        // Crear una ventana para mostrar el resultado
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Habilidad Especial");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(400, 200);
            objetivo.setEstado(Estados.PARALIZADO, 2);
            // Crear un mensaje con los detalles de la habilidad
            String mensaje = this.nombre + " usa su habilidad especial: ¡Tormenta de abanicos!\n" +
                    objetivo.getNombre() + " recibe " + this.poder + " de daño. y esta paralizado por 2 turnos.\n";

            JTextArea textArea = new JTextArea(mensaje);
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);

            frame.add(new JScrollPane(textArea));
            frame.setVisible(true);
        });
    }
}