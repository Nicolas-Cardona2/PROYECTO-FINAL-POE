package modelo;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class RegistroBatalla { //Clase Creada como un intermediario que me permite registrar lo que sucede en tiempo real al JTextArea
    private static JTextArea area;

    public static void setTextArea(JTextArea textArea) {//Añadir El JTextArea
        area = textArea;
    }
        //Metodo estatico que no tiene la necesidad de ser instansiado es decir se puede utilizar en cualquier clase normal
    public static void RegistrarTextos(String mensaje) {//Registra en el JTextArea Las Acciones que van sucediendo en la partida
            if (area != null) {
                SwingUtilities.invokeLater(() -> { //Para que actualize correctamente el texto a pesar de manejarse otro hilo aparte para la logica pesada
                 area.append(mensaje + "\n");
                 });
            }
    }

}

