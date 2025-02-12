package videojuego;


import java.util.Random;
import java.util.Scanner;

public class Videojuego {

    public static void main(String[] args) {
        
        
        logica();
    }
    
    
    
public static void logica() {
    Scanner scan = new Scanner(System.in);
    Random random = new Random();
    
    System.out.println("Bienvenido a MORTAL KOMBAT");
    System.out.println("Selecciona tu personaje: \n1. SubZero \n2. Scorpion ");
    int personaje = scan.nextInt();
    
    Personaje jugador;
    Personaje cpu;
    
    if (personaje == 1) {
        jugador = new Personaje(60, 10, Estados.NORMAL, 40, "Sub-Zero");
        cpu = new Personaje(60, 10, Estados.NORMAL, 40, "Scorpion");
    } else {
        jugador = new Personaje(60, 10, Estados.NORMAL, 40, "Scorpion");
        cpu = new Personaje(60, 10, Estados.NORMAL, 40, "Sub-Zero");
    }

    System.out.println("Comienza la batalla entre: " + jugador.getNombre() + " y " + cpu.getNombre());

    // INICIO DEL JUEGO
    while (true) { // Bucle infinito hasta que haya un ganador
        int numeroAleatorio = random.nextInt(2) + 1;
        
        // ATAQUE DE LA CPU
        if (numeroAleatorio == 1) {
            System.out.println("\n" + cpu.getNombre() + " ataca ");
            int ataquesc = cpu.getPoder() / jugador.getDefensa();
            jugador.setVida(jugador.getVida() - ataquesc);

            if (cpu.getNombre().equals("Scorpion")) {
                System.out.println(jugador.getNombre() + " ha sido quemado");
                jugador.setEstado(Estados.QUEMADO);
                jugador.setVida(jugador.getVida() - 5);
            } else if (cpu.getNombre().equals("Sub-Zero")) {
                System.out.println(jugador.getNombre() + " ha sido congelado");
                jugador.setEstado(Estados.CONGELADO);
                jugador.setVida(jugador.getVida() - 8);
            }

            // Verificar si el jugador ha perdido
            if (jugador.getVida() <= 0) {
                System.out.println("\nGAME OVER " + cpu.getNombre() + " WINS FLAWLESS VICTORY");
                jugador.setEstado(Estados.KO);
                break; // Sale del bucle y termina el juego
            }
        }

        // ATAQUE DEL JUGADOR
        if (numeroAleatorio == 2) {
            System.out.println("\n" + jugador.getNombre() + " ataca");
            int ataquesz = jugador.getPoder() / cpu.getDefensa();
            cpu.setVida(cpu.getVida() - ataquesz);
            
            if (jugador.getNombre().equals("Scorpion")) {
                System.out.println(cpu.getNombre() + " ha sido quemado");
                cpu.setEstado(Estados.QUEMADO);
                cpu.setVida(cpu.getVida() - 5);
            } else if (jugador.getNombre().equals("Sub-Zero")) {
                System.out.println(cpu.getNombre() + " ha sido congelado");
                cpu.setEstado(Estados.CONGELADO);
                cpu.setVida(cpu.getVida() - 8);
            }

            // Verificar si la CPU ha perdido
            if (cpu.getVida() <= 0) {
                System.out.println("\nGAME OVER " + jugador.getNombre() + " WINS FLAWLESS VICTORY");
                cpu.setEstado(Estados.KO);
                break; // Sale del bucle y termina el juego
            }
        }
    }
}

}
