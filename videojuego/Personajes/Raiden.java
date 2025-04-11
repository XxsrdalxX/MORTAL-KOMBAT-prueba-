package Personajes;

import Videojuego.*;

/**
 *
 * @author Dylan
 */
public class Raiden extends Personaje {

    public Raiden(int vida, int defensa, Estados estado, int poder, String nombre) {
        super(vida, defensa, estado, poder, nombre);
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }

    public int getPoder() {
        return poder;
    }

    public void setPoder(int poder) {
        this.poder = poder;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void habilidadEspecial(Personaje objetivo) {
        if (objetivo.getEstado() != Estados.PARALIZADO) {
            System.out.println(this.nombre + " usa su habilidad especial: ¡Rayo Destructor!");
            System.out.println(objetivo.getNombre() + " ha sido paralizado por 1 turno.");
            objetivo.setEstado(Estados.PARALIZADO); // Paraliza al enemigo
            objetivo.turnosParalizado = 1; // Paraliza al enemigo por 1 turno
            this.poder += 10; // Aumenta permanentemente el poder de Raiden
            System.out.println(this.nombre + " ha aumentado su poder a: " + this.poder);
        } else {
            System.out.println(objetivo.getNombre() + " ya está paralizado.");
        }
    }
}