
package Personajes;

import Videojuego.*;

public class SubZero extends Personaje {

    public String tipo;
    public int turnosCongelado = 0; // Variable para contar los turnos congelado

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
            turnosCongelado++; // Aumenta el contador de turnos congelado
        } else {
            System.out.println(objetivo.getNombre() + " ya está congelado por " + turnosCongelado + " turnos!");
            objetivo.setEstado(Estados.NORMAL);
        }
    }

    public void habilidadEspecial(Personaje objetivo) {
        System.out.println(this.nombre + " usa su habilidad especial: ¡Congelación!");
        Congelar(objetivo); // Congela al objetivo como parte de la habilidad especial

    }

}
