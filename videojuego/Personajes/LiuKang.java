package Personajes;

import Videojuego.*;

/**
 *
 * @author Dylan
 */
public class LiuKang  extends Personaje{

    public LiuKang(int vida, int defensa, Estados estado, int poder, String nombre) {
        super(vida, defensa, estado, poder, nombre);
    }

    @Override
    public void habilidadEspecial() {
        System.out.println(this.nombre + " usa su habilidad especial: Patada Voladora!");
        // Implementa el efecto de la habilidad especial aquí
    }
}