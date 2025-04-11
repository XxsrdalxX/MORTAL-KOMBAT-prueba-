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
            // Manejar estados del jugador y la IA
            jugador1.manejarEstados();
            cpu.manejarEstados();
    
            // Turno del jugador
            if (jugador1.getEstado() == Estados.CONGELADO || jugador1.getEstado() == Estados.PARALIZADO) {
                System.out.println(jugador1.getNombre() + " está " + jugador1.getEstado().toString().toLowerCase() + " y no puede actuar.");
            } else {
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
                        jugador1.habilidadEspecial(cpu);
                        break;
                    default:
                        System.out.println("Acción no válida. Pierdes tu turno.");
                }
            }
    
            // Verificar si la IA ha sido derrotada
            if (cpu.getVida() <= 0) {
                System.out.println("\n¡" + cpu.getNombre() + " ha sido derrotado! ¡Ganaste!");
                break;
            }
    
            // Turno de la IA
            if (cpu.getEstado() == Estados.CONGELADO || cpu.getEstado() == Estados.PARALIZADO) {
                System.out.println(cpu.getNombre() + " está " + cpu.getEstado().toString().toLowerCase() + " y no puede actuar.");
            } else {
                System.out.println("\nTurno de la IA:");
                IAbot iaBot = new IAbot();
                iaBot.decidirAccion(cpu, jugador1);
            }
    
            // Verificar si el jugador ha sido derrotado
            if (jugador1.getVida() <= 0) {
                System.out.println("\n¡" + jugador1.getNombre() + " ha sido derrotado! ¡Perdiste!");
                break;
            }
    
            System.out.println("========================================");
        }
    
        scanner.close();
    }

}
