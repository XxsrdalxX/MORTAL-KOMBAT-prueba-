package Personajes;

import Videojuego.*;

public class SubZero extends Personaje {

    public String tipo;
    public int turnosCongelado = 0; // Contador de turnos que el objetivo está congelado
    public int cooldown = 0; // Contador de cooldown para la habilidad especial

    public SubZero(String tipo, int defensa, int vida, Estados estado, int poder, String nombre) {
        super(vida, defensa, estado, poder, nombre);
        this.tipo = "Hielo";
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public void Congelar(Personaje objetivo) {
        if (objetivo.getEstado() != Estados.CONGELADO) {
            System.out.println(this.nombre + " congela a " + objetivo.getNombre() + "!");
            objetivo.setEstado(Estados.CONGELADO);
            objetivo.turnosCongelado = 2; // Congela al objetivo por 2 turnos
        } else {
            System.out.println(objetivo.getNombre() + " ya está congelado.");
        }
    }

    @Override
    public void habilidadEspecial(Personaje objetivo) {
        if (cooldown == 0) {
            System.out.println(this.nombre + " usa su habilidad especial: ¡Congelación!");
            Congelar(objetivo); // Congela al objetivo como parte de la habilidad especial
            cooldown = 3; // Establece el cooldown en 3 turnos
        } else {
            System.out.println(this.nombre + " no puede usar su habilidad especial. Cooldown restante: " + cooldown + " turnos.");
        }
        reducirCooldown(); // Reduce el cooldown al final del turno 
    }

    public void reducirCooldown() {
        if (cooldown > 0) {
            cooldown--; // Reduce el cooldown en 1 al final del turno
        }
    }
}
