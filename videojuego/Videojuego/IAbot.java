
package Videojuego;

import java.util.Random;

import Personajes.*;

public class IAbot {

    public void decidirAccion(Personaje enemigo, Personaje jugador) {
        // Si el jugador está congelado, la IA no usa su habilidad
        if (jugador.estado == Estados.CONGELADO) {
            System.out.println(" La IA ve que " + jugador.nombre + " está congelado. Ataca normalmente.");
            enemigo.atacar(jugador);
            return;
        }

        // Si la IA está en peligro, puede intentar curarse
        if (enemigo.vida <= 20) {
            System.out.println(enemigo.nombre + " está en peligro. Intenta curarse.");
            enemigo.curar();
            return;
        }

        // Probabilidad de usar habilidad especial (60% de las veces)
        if (Math.random() < 0.6) {
            System.out.println(enemigo.nombre + " decide usar su habilidad especial.");
            enemigo.habilidadEspecial(jugador);
        } else {
            System.out.println(enemigo.nombre + " ataca normalmente.");
            enemigo.atacar(jugador);
        }
    }

    public static Personaje seleccionarPersonajeIA() {
        Random random = new Random();
        int opcion = random.nextInt(5) + 1; // Genera un número aleatorio entre 1 y 5

        switch (opcion) {
            case 1:
                System.out.println("🤖 La IA ha seleccionado a Liu Kang.");
                return new LiuKang(100, 5, Estados.NORMAL, 30, "Liu Kang");
            case 2:
                System.out.println("🤖 La IA ha seleccionado a Raiden.");
                return new Raiden(120, 4, Estados.NORMAL, 25, "Raiden");
            case 3:
                System.out.println("🤖 La IA ha seleccionado a Scorpion.");
                return new Scorpion("Fuego", 4, 110, Estados.NORMAL, 35, "Scorpion");
            case 4:
                System.out.println("🤖 La IA ha seleccionado a Sub-Zero.");
                return new SubZero("Hielo", 5, 115, Estados.NORMAL, 28, "Sub-Zero");
            case 5:
                System.out.println("🤖 La IA ha seleccionado a Kitana.");
                return new Kitana(90, 4, Estados.NORMAL, 40, "Kitana");
            default:
                // Esto nunca debería ocurrir, pero se incluye por seguridad
                System.out.println("🤖 Error al seleccionar personaje de la IA.");
                return null;
        }
    }
}