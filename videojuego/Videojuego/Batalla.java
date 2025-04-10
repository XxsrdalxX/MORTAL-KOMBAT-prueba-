package Videojuego;



import Personajes.*;
import java.util.Scanner;

public class Batalla {
 public void iniciarBatalla(Personaje jugador1, Personaje cpu) {
        Scanner scanner = new Scanner(System.in);

       

        System.out.println("¡Comienza la batalla entre " + jugador1.getNombre() + " y " + cpu.getNombre() + "!");
        System.out.println("========================================");

        // Ciclo de turnos
        while (jugador1.getVida() > 0 && cpu.getVida() > 0) {
            // Turno del jugador
            System.out.println("\nTurno del jugador:");
            System.out.println("1. Atacar");
            System.out.println("2. Curarse");
            System.out.println("3. Usar habilidad especial");
            System.out.print("Elige una acción: ");
            int accion = scanner.nextInt();

            switch (accion) {
                case 1:
                    jugador1.atacar(cpu);
                    break;
                case 2:
                    jugador1.curar();
                    break;
                case 3:
                    jugador1.habilidadEspecial();
                    break;
                default:
                    System.out.println("Acción no válida. Pierdes tu turno.");
            }

            // Verificar si la IA ha sido derrotada
            if (cpu.getVida() <= 0) {
                System.out.println("\n¡" + cpu.getNombre() + " ha sido derrotado! ¡Ganaste!");
                break;
            }

            // Turno de la IA
            System.out.println("\nTurno de la IA:");
            IAbot iaBot = new IAbot();
            iaBot.decidirAccion(cpu, jugador1);

            // Verificar si el jugador ha sido derrotado
            if (jugador1.getVida() <= 0) {
                System.out.println("\n¡" + jugador1.getNombre() + " ha sido derrotado! ¡Perdiste!");
                break;
            }

            // Mostrar estado actual de ambos personajes
            System.out.println("\nEstado actual:");
            System.out.println(jugador1.getNombre() + " - Vida: " + jugador1.getVida() + ", Defensa: " + jugador1.getDefensa() + ", Poder: " + jugador1.getPoder());
            System.out.println(cpu.getNombre() + " - Vida: " + cpu.getVida() + ", Defensa: " + cpu.getDefensa() + ", Poder: " + cpu.getPoder());
            System.out.println("========================================");
        }

        scanner.close();
    }
    }

