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
    public void habilidadEspecial() {
        System.out.println(this.nombre + " usa su habilidad especial: Rayo Destructor!");
        this.poder += 20; // Aumenta el poder como parte de la habilidad especial
    }
    
}