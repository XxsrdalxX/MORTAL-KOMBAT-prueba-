package Videojuego;

import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import Personajes.*;

public class IAbot {

    public Personaje seleccionarPersonajeIA() {
        Random random = new Random();
        int opcion = random.nextInt(5); // Genera un número aleatorio entre 0 y 4

        switch (opcion) {
            case 0:
                return new Raiden(100, 10, Estados.NORMAL, 15, "Raiden");
            case 1:
                return new LiuKang(120, 8, Estados.NORMAL, 20, "Liu Kang");
            case 2:
                return new SubZero("Hielo", 110, 12, Estados.NORMAL, 25, "Sub-Zero");
            case 3:
                return new Kitana(90, 9, Estados.NORMAL, 18, "Kitana");
            case 4:
                return new Scorpion("Fuego", 10, 100, Estados.NORMAL, 22, "Scorpion");
            default:
                return new Raiden(100, 10, Estados.NORMAL, 15, "Raiden"); // Caso por defecto
        }
    }
 
    public String decidirAccion(Personaje enemigo, Personaje jugador, JTextArea areaMensajes) {
        String mensaje;
    
        // Verificar si el enemigo está congelado
        if (enemigo.getEstado() == Estados.CONGELADO) {
            mensaje = "CPU: " + enemigo.getNombre() + " está congelado y no puede realizar ninguna acción.";
            areaMensajes.append(mensaje + "\n");
            enemigo.reducirTurnosEstado(areaMensajes); // Reducir el efecto de congelación
            return mensaje;
        }
    
        // Si la IA está en peligro, puede intentar curarse
        if (enemigo.getVida() <= 20) {
            enemigo.curar(areaMensajes); // Pasar el área de mensajes
            mensaje = "CPU: " + enemigo.getNombre() + " se curó.";
            areaMensajes.append(mensaje + "\n");
            return mensaje;
        }
    
        // Decidir si usar la habilidad especial (40% de probabilidad)
        if (Math.random() < 0.4 && enemigo.getEstado() == Estados.NORMAL) {
            enemigo.habilidadEspecial(jugador, areaMensajes); // Pasar el área de mensajes
            mensaje = "CPU: " + enemigo.getNombre() + " usó su habilidad especial contra " + jugador.getNombre() + ".";
            areaMensajes.append(mensaje + "\n");
            return mensaje;
        }
    
        // Si no se cumplen las condiciones anteriores, atacar
        enemigo.atacar(jugador, areaMensajes); // Pasar el área de mensajes
        mensaje = "CPU: " + enemigo.getNombre() + " atacó a " + jugador.getNombre() + ".";
        areaMensajes.append(mensaje + "\n");
        return mensaje;
    }
    private void mostrarMensaje(String mensaje) {
        // Muestra el mensaje en una ventana emergente
        JOptionPane.showMessageDialog(null, mensaje, "Acción de la IA", JOptionPane.INFORMATION_MESSAGE);
    }

}