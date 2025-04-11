package Personajes;

import java.util.Random;
import Videojuego.Estados;

public abstract class Personaje {
    public int vidaMaxima;// Vida máxima del personaje
    public int vida; // Vida actual del personaje
    public int defensa; // Defensa del personaje
    public Estados estado; // Estado del personaje (puede ser normal, envenenado, etc.)
    public int poder; // Poder de ataque del personaje (Se divide entre la defensa del enemigo)
    public String nombre; // Nombre del personaje
    public int turnosCongelado;
    public int turnosEnvenenado; // Turnos que el personaje ha estado envenenado
    public int turnosQuemado; // Turnos que el personaje ha estado quemado
    public int turnosParalizado; // Turnos que el personaje ha estado paralizado

    public Personaje(int vida, int defensa, Estados estado, int poder, String nombre) {
        this.vida = vida;
        this.vidaMaxima = vida;
        this.defensa = defensa;
        this.estado = estado;
        this.poder = poder;
        this.nombre = nombre;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getVida() {
        return vida;
    }

    public int getDefensa() {
        return defensa;
    }

    public Estados getEstado() {
        return estado;
    }

    public int getPoder() {
        return poder;
    }

    public String getNombre() {
        return nombre;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }

    public void setPoder(int poder) {
        this.poder = poder;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Metodo para Atacar a otro personaje
    // Este método calcula el daño basado en el poder del atacante y la defensa del
    // objetivo.
    public void atacar(Personaje objetivo) {
        if (objetivo.defensa > 0) {
            int daño = this.poder / objetivo.defensa; // Calcula el daño basado en el poder y la defensa
            objetivo.vida -= daño; // Reduce la vida del objetivo
            if (objetivo.vida < 0) {
                objetivo.vida = 0; // Asegura que la vida no sea negativa
            }
            System.out.println("poder : " + poder + " defensa : " + defensa);

            System.out.println(this.nombre + " atacó a " + objetivo.nombre + " causando " + daño + " de daño.");
        } else {
            System.out.println("La defensa del objetivo es 0, no se puede calcular el daño.");
        }
    }

    // Método para curar al personaje
    public void curar() {
        Random random = new Random();
        int probabilidad = random.nextInt(100); // Genera un número entre 0 y 99
        if (probabilidad < 30) { // 30% de probabilidad
            this.vida += 10; // Cura 10 puntos de vida
            System.out.println(this.nombre + " se ha curado 10 puntos de vida. Vida actual: " + this.vida);
        } else {
            System.out.println(this.nombre + " intentó curarse, pero falló.");
        }
    }

    public void manejarEstados() {
        if (this.estado == Estados.CONGELADO) {
            if (this.turnosCongelado > 0) {
                this.turnosCongelado--;
                System.out.println(this.nombre + " sigue congelado. Turnos restantes: " + this.turnosCongelado);
            }
            if (this.turnosCongelado == 0) {
                this.estado = Estados.NORMAL;
                System.out.println(this.nombre + " ya no está congelado.");
            }
        }
     
          if (this.estado == Estados.PARALIZADO) {
                if (this.turnosParalizado > 0) {
                    this.turnosParalizado--;
                    System.out.println(this.nombre + " sigue paralizado. Turnos restantes: " + this.turnosParalizado);
                }
                if (this.turnosParalizado == 0) {
                    this.estado = Estados.NORMAL;
                    System.out.println(this.nombre + " ya no está paralizado.");
                }
            }
        
    }
   

    // Habilidad especial del personaje (abstracta)
    // Esta habilidad puede ser diferente para cada personaje.
    public abstract void habilidadEspecial(Personaje objetivo); // Habilidad especial del personaje
}
