package Videojuego;

import Personajes.*;
import java.util.Scanner;

public class Videojuego {

    public static void main(String[] args) {
        System.out.println("¡Bienvenido a Mortal Kombat!");
        SeleccionPJGUI seleccion = new SeleccionPJGUI();
        Personaje jugador1 = seleccion.Seleccion();
        

        System.out.println("\nSelecciona un modo de juego:");
        System.out.println("1. Modo Historia (Torres)");
        System.out.println("2. Batalla Rápida");
        System.out.print("Elige una opción: ");
        Scanner scanner = new Scanner(System.in);
        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                new ModoHistoria(jugador1); // Inicia el modo historia con GUI
                break;
            case 2:
                Batalla batalla = new Batalla();
                IAbot ia = new IAbot();
                Personaje cpu = ia.seleccionarPersonajeIA();
                batalla.iniciarBatalla(jugador1, cpu);
                break;
            default:
                System.out.println("Opción no válida. Saliendo del juego.");
        }

        scanner.close();
    }
}