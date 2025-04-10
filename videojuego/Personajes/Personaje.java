package Personajes;
import java.util.Random;
import Videojuego.Estados;
public abstract class Personaje {
    
    public int vida;
    public int defensa;
    public Estados estado;
    public int poder;
    public String nombre;
    

    public Personaje(int vida, int defensa, Estados estado, int poder, String nombre) {
        this.vida = vida;
        this.defensa = defensa;
        this.estado = estado;
        this.poder = poder;
        this.nombre = nombre;
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

    public void atacar(Personaje objetivo) {
        if (objetivo.defensa > 0) {
            int daño = this.poder / objetivo.defensa; // Calcula el daño basado en el poder y la defensa
            objetivo.vida -= daño; // Reduce la vida del objetivo
            if (objetivo.vida < 0) {
                objetivo.vida = 0; // Asegura que la vida no sea negativa
            }
            System.out.println(this.nombre + " atacó a " + objetivo.nombre + " causando " + daño + " de daño.");
        } else {
            System.out.println("La defensa del objetivo es 0, no se puede calcular el daño.");
        }
    }
    
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
    public abstract void habilidadEspecial();
}
