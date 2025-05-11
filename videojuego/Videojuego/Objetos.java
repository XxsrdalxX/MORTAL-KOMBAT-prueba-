package Videojuego;

import Personajes.*;
import java.util.HashMap;

public class Objetos {
    private HashMap<String, Runnable> objetos; // Almacena los objetos y sus efectos
    private Personaje jugador; // Referencia al jugador actual

    // Constructor
    public Objetos( Personaje jugador) {
        this.jugador = jugador;
        objetos = new HashMap<>();
        inicializarObjetos(); // Inicializa los objetos disponibles
    }

    // Inicializa los objetos y sus efectos
    private void inicializarObjetos() {
        objetos.put("Poción de Salud", () -> {
            jugador.setVida(jugador.getVida() + 50); // Aumenta la salud del jugador
            System.out.println("Has usado una Poción de Salud. +50 de vida.");
        });

        objetos.put("Elixir de Poder", () -> {
            jugador.setPoder(jugador.getPoder() + 10); // Aumenta el poder del jugador
            System.out.println("Has usado un Elixir de Poder. +10 de poder.");
        });

        objetos.put("Escudo de Resistencia", () -> {
            jugador.setResistencia(jugador.getResistencia() + 10); // Aumenta la resistencia del jugador
            System.out.println("Has usado un Escudo de Resistencia. +15 de resistencia.");
        });

        objetos.put("Antídoto", () -> {
            jugador.setEstado(Estados.NORMAL, 0); // Cura cualquier estado negativo
            System.out.println("Has usado un Antídoto. Estado restaurado a NORMAL.");
        });

        objetos.put("Aumento de Vida Máxima", () -> {
            jugador.setVidaMaxima(jugador.getVidaMaxima() + 20); // Incrementa la vida máxima
            System.out.println("Has usado un Aumento de Vida Máxima. +20 a la vida máxima.");
        });
        objetos.put("Resistencia a los efectos", () -> {
            jugador.setEstado(Estados.NORMAL, -2); // Incrementa la defensa
            System.out.println("Has usado una resistencia a los efectos durante dos turnos.");
        });
    }

    // Aplica el efecto de un objeto al jugador
    public void usarObjeto(String nombreObjeto) {
        if (objetos.containsKey(nombreObjeto)) {
            objetos.get(nombreObjeto).run(); // Ejecuta el efecto del objeto
        } else {
            System.out.println("El objeto " + nombreObjeto + " no existe.");
        }
    }

    // Muestra los objetos disponibles
    public void mostrarObjetos() {
        System.out.println("Objetos disponibles:");
        for (String objeto : objetos.keySet()) {
            System.out.println("- " + objeto);
        }
    }
}