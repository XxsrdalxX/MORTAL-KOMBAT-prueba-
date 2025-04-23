package Personajes;

import javax.swing.JOptionPane;

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
    public void habilidadEspecial(Personaje objetivo) {
        // Mostrar mensaje de uso de habilidad especial
        String mensajeHabilidad = this.nombre + " usa su habilidad especial: ¡Patada Voladora!";
        mostrarMensaje(mensajeHabilidad);

        // Reducir la vida del objetivo
        objetivo.setVida(objetivo.getVida() - this.poder);

        // Mostrar mensaje de daño infligido
        String mensajeDaño = objetivo.getNombre() + " ha recibido " + this.poder + " de daño. Vida restante: " + objetivo.getVida();
        mostrarMensaje(mensajeDaño);

        // Aumentar el poder de Liu Kang
        this.poder += 10;

        // Mostrar mensaje de aumento de poder
        String mensajePoder = this.nombre + " ha aumentado su poder a: " + this.poder;
        mostrarMensaje(mensajePoder);
    }

     private void mostrarMensaje(String mensaje) {
        // Muestra el mensaje en una ventana emergente
        JOptionPane.showMessageDialog(null, mensaje, "Habilidad Especial de Liu Kang", JOptionPane.INFORMATION_MESSAGE);
    }

}