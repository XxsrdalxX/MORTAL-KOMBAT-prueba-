package Personajes;

import Videojuego.*;

/**
 *
 * @author Dylan
 */
public class Raiden extends Personaje {

    private int cooldownHabilidad; // Cooldown de la habilidad especial

    public Raiden(int vida, int defensa, Estados estado, int poder, String nombre) {
        super(vida, defensa, estado, poder, nombre);
        this.cooldownHabilidad = 0; // Inicializa el cooldown de la habilidad especial
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
        if(cooldownHabilidad > 0) {
            System.out.println(this.nombre + " no puede usar su habilidad especial. Cooldown: " + cooldownHabilidad);
            return; // Si el cooldown no es cero, no se puede usar la habilidad
        }
        if (objetivo.getEstado() != Estados.PARALIZADO) {
            System.out.println(this.nombre + " usa su habilidad especial: ¡Rayo Destructor!");
            System.out.println(objetivo.getNombre() + " ha sido paralizado por 1 turno.");
            objetivo.setEstado(Estados.PARALIZADO); // Paraliza al enemigo
            objetivo.turnosParalizado = 1; // Paraliza al enemigo por 1 turno
            if(this.poder < 100){
                this.poder += 10; // Aumenta permanentemente el poder de Raiden
            }else {
                System.out.println(this.nombre + " ya tiene el poder máximo.");
            }
           
            System.out.println(this.nombre + " ha aumentado su poder a: " + this.poder);

            cooldownHabilidad = 2; // Establece el cooldown de la habilidad especial a 2 turnos
            reducirCooldown(); // Reduce el cooldown de la habilidad especial
        } else {
            System.out.println(objetivo.getNombre() + " ya está paralizado.");
        }

    }

    public void reducirCooldown() {
        if (cooldownHabilidad > 0) {
            cooldownHabilidad--; // Reduce el cooldown de la habilidad especial
        }
    }
}