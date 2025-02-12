package videojuego;


public class Personaje {
    
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

    
    
}
