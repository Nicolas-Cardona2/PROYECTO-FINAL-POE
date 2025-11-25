import controlador.ControladorJuego;
/*aqui nada mas se necesita que se llame al controlador y ya, como les dije por ws esto es como lo basico de lo basico */
public class App {
    public static void main(String[] args) {
        ControladorJuego controlador = new ControladorJuego();
        controlador.iniciarJuego();
    }
}
