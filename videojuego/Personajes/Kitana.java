package Personajes;

import Videojuego.Estados;

public class Kitana extends Personaje {

    public Kitana(int vida, int defensa, Estados estado, int poder, String nombre) {
        super(vida, defensa, estado, poder, nombre);
    }

    @Override
    public void habilidadEspecial(Personaje objetivo) {
        System.out.println(this.nombre + " usa su habilidad especial: ¡Tormenta de abanicos!");
        this.poder += 20; // Aumenta el poder como parte de la habilidad especial
    }
}