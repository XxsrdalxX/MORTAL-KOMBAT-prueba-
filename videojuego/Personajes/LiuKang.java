package Personajes;

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
        System.out.println(this.nombre + " usa su habilidad especial: Patada Voladora!");
        objetivo.setVida(objetivo.getVida() - this.poder); // Reduce la vida del objetivo
        this.poder += 10; // Aumenta el poder como parte de la habilidad especial
        System.out.println(this.nombre + " ha aumentado su poder a: " + this.poder);
    }
}