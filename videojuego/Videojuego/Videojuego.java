package Videojuego;
import Personajes.*;
public class Videojuego {

    public static void main(String[] args) {


        System.out.println("¡Bienvenido a Mortal Kombat!");
        Batalla batalla = new Batalla();
        SeleccionPJGUI seleccion = new SeleccionPJGUI();
     IAbot ia = new IAbot();
        Personaje jugador1 = seleccion.Seleccion();
        Personaje cpu = ia.seleccionarPersonajeIA();
       batalla.iniciarBatalla(jugador1, cpu);

       

       

       
    }

}
