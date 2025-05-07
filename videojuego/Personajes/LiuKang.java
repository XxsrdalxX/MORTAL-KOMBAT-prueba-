package Personajes;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import Videojuego.*;

/**
 *
 * @author Dylan
 */
public class LiuKang extends Personaje {

    public LiuKang(int vida, int defensa, Estados estado, int poder, String nombre) {
        super(vida, defensa, estado, poder, nombre);
    }

    @Override
    public void habilidadEspecial(Personaje objetivo, JTextArea areaMensajes) {
        // Registrar el uso de la habilidad especial
        String mensajeHabilidad = nombre + " usa su habilidad especial: ¡Patada Voladora!";
        areaMensajes.append(mensajeHabilidad + "\n");
    
        // Reducir la vida del objetivo
        int daño = this.poder;
        objetivo.setVida(objetivo.getVida() - daño);
    
        // Registrar el daño infligido
        String mensajeDaño = objetivo.getNombre() + " ha recibido " + daño + " de daño. Vida restante: " + objetivo.getVida();
        areaMensajes.append(mensajeDaño + "\n");
    
        // Aumentar el poder de Liu Kang
        this.poder += 10;
    
        // Registrar el aumento de poder
        String mensajePoder = nombre + " ha aumentado su poder a: " + this.poder;
        areaMensajes.append(mensajePoder + "\n");
    }

    private void mostrarMensaje(String mensaje) {
        // Muestra el mensaje en una ventana emergente
        JOptionPane.showMessageDialog(null, mensaje, "Habilidad Especial de Liu Kang", JOptionPane.INFORMATION_MESSAGE);
    }

}