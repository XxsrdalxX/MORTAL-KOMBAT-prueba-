package Personajes;

//         this.nombre = nombre;    
import Videojuego.*;

public class Scorpion extends Personaje {

    public String tipo;

    public Scorpion(String tipo, int defensa, int vida, Estados estado, int poder, String nombre) {
        super(vida, defensa, estado, poder, nombre);
        this.tipo = "Fuego";
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

    // APLICAR EFECTO PARA QUEMAR AL OBJETIVO
    public void Quemar(Personaje objetivo) {
        if (objetivo.getEstado() != Estados.QUEMADO) {
            System.out.println(this.nombre + " quema a " + objetivo.getNombre() + " con su ataque de fuego!"
                    + " Perdera vida durante 5 turnos mas");
            for (int i = 1; i <= 5; i++) {
                try {
                    Thread.sleep(1000); // Simula un retraso de 1 segundo entre turnos
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                objetivo.setVida(objetivo.getVida() - 1); // Resta 1 de vida al objetivo
                System.out.println("Turno " + i + ": " + objetivo.getNombre() + " pierde 1 de vida. Vida restante: "
                        + objetivo.getVida());

                // Verificar si el objetivo ha sido derrotado
                if (objetivo.getVida() <= 0) {
                    System.out
                            .println(objetivo.getNombre() + " ha sido derrotado por el fuego de " + this.nombre + "!");
                    break;
                }
            }
        }
    } // Quema al objetivo y le resta 1 de vida por 5 turnos

    @Override
    public void habilidadEspecial(Personaje objetivo) {
        System.out.println(this.nombre + " usa LANZA INFERNAL");
        this.Quemar(objetivo);

    }// Habilidad especial de Scorpion que quema al objetivo y le resta 1 de vida por
     // 5 turnos
}// Fin de la clase Scorpion
