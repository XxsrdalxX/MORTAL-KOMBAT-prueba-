package Videojuego;

import Personajes.*;
import java.util.Scanner;

public class SeleccionPJGUI {

    public static Personaje Seleccion() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("¡Bienvenido a la selección de personajes!");
        System.out.println("Por favor, selecciona tu personaje:");
        System.out.println("1. Liu Kang");
        System.out.println("2. Raiden");
        System.out.println("3. Scorpion");
        System.out.println("4. Sub-Zero");
        System.out.println("5. Kitana");

        int opcion = scanner.nextInt();
        Personaje personajeSeleccionado = null;

        switch (opcion) {
            case 1:
                personajeSeleccionado = new LiuKang(100, 50, Estados.NORMAL, 30, "Liu Kang");
                break;
            case 2:
                personajeSeleccionado = new Raiden(120, 40, Estados.NORMAL, 25, "Raiden");
                break;
            case 3:
                personajeSeleccionado = new Scorpion("Fuego", 110, 45, Estados.NORMAL, 35, "Scorpion");
                break;
            case 4:
                personajeSeleccionado = new SubZero("Hielo", 115, 50, Estados.NORMAL, 28, "Sub-Zero");
                break;
            case 5:
                personajeSeleccionado = new Kitana(90, 40, Estados.NORMAL, 40, "Kitana");
                break;
            default:
                System.out.println("Opción no válida. Selecciona un personaje válido.");
                break;
        }

        return personajeSeleccionado;
    }
}