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
            if (jugador1.getEstado() == Estados.CONGELADO) {
                System.out.println(jugador1.getNombre() + " está congelado y no puede atacar.");
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
                System.out.println("========================================");
                System.out.println(" FATALITY ");
                System.out.println("========================================");
                System.out.println("DIGITA EL SIGUIENTE CODIGO PARA REALIZAR UNA FATALITY: ");
                System.out.println("A A B B C C A A B B ");
                String Codigo = scanner.nextLine();
                if (Codigo.equals("A A B B C C A A B B")) {
                    System.out.println("¡Felicidades! Has realizado una FATALITY con éxito.");
                } else {
                    System.out.println("Código incorrecto. No has podido realizar la FATALITY.");
                }
                break;
            }

            // Turno de la IA
            if (cpu.getEstado() == Estados.CONGELADO) {
                System.out.println(cpu.getNombre() + " está congelado y no puede atacar.");
            } else {
                System.out.println("\nTurno de la IA:");
                IAbot iaBot = new IAbot();
                iaBot.decidirAccion(cpu, jugador1);
            }

            // Verificar si el jugador ha sido derrotado
            if (jugador1.getVida() <= 0) {
                System.out.println("\n¡" + jugador1.getNombre() + " ha sido derrotado! ¡Perdiste!");
                System.out.println("¡¡FLAWLESS VICTORY!!");
                break;
            }

            // Mostrar estado actual de ambos personajes
            System.out.println("\nEstado actual:");
            System.out.println(jugador1.getNombre() + " - Vida: " + jugador1.getVida() + ", Defensa: "
                    + jugador1.getDefensa() + ", Poder: " + jugador1.getPoder());
            System.out.println(cpu.getNombre() + " - Vida: " + cpu.getVida() + ", Defensa: " + cpu.getDefensa()
                    + ", Poder: " + cpu.getPoder());
            System.out.println("========================================");
        }
    }

}
