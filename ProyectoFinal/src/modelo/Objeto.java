package modelo; //esta clase y la de tipo objeto se agregan para empezar a manejar los objetos, se va a modificar la clase de heroe para que
//tengan inventario de los objetos y todo el cuento

public class Objeto {
    private String nombre;
    private String descripcion;
    private TipoObjeto Tipo;
    private int valorEfecto;
    private int duracion;

    

    public Objeto(String nombre, String descripcion, TipoObjeto tipo, int valorEfecto) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.Tipo = tipo;
        this.valorEfecto = valorEfecto;
        this.duracion = 0;
    }



    public Objeto(String nombre, String descripcion, TipoObjeto tipo, int valorEfecto, int duracion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.Tipo = tipo;
        this.valorEfecto = valorEfecto;
        this.duracion = duracion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public TipoObjeto getTipo() {
        return Tipo;
    }

    public int getValorEfecto() {
        return valorEfecto;
    }

    public int getDuracion() {
        return duracion;
    }

    public boolean usar(Personaje usuario, Personaje objetivo) {
        try {
            switch (Tipo) {
                case CURATIVO:
                    objetivo.setHP(objetivo.getHP() + valorEfecto);
                    RegistroBatalla.RegistrarTextos(usuario.getNombre() + " usó " + nombre + 
                        " en " + objetivo.getNombre() + " y curó " + valorEfecto + " HP!");
                    break;
                    
                case ANTIDOTO:
                    if (objetivo.getEstado().equals("Envenenado") || objetivo.getEstado().equals("Dormido")) {
                        objetivo.setEstado("Vivo");
                        RegistroBatalla.RegistrarTextos(usuario.getNombre() + " usó " + nombre + 
                            " en " + objetivo.getNombre() + " y curó el estado!");
                    }
                    break;
                    
                case REVIVIR:
                    if (objetivo.getEstado().equals("Muerto")) {
                        objetivo.setEstado("Vivo");
                        objetivo.setHP(valorEfecto);
                        RegistroBatalla.RegistrarTextos(usuario.getNombre() + " usó " + nombre + 
                            " en " + objetivo.getNombre() + " y lo revivió con " + valorEfecto + " HP!");
                    }
                    break;
                    
                case RESTAURAR_MP:
                    objetivo.setMP(objetivo.getMP() + valorEfecto);
                    RegistroBatalla.RegistrarTextos(usuario.getNombre() + " usó " + nombre + 
                        " en " + objetivo.getNombre() + " y restauró " + valorEfecto + " MP!");
                    break;
                    
                case MEJORAR_DEFENSA:
                    objetivo.setDefensa(objetivo.getDefensa() + valorEfecto);
                    RegistroBatalla.RegistrarTextos(usuario.getNombre() + " usó " + nombre + 
                        " en " + objetivo.getNombre() + " y aumentó su defensa en " + valorEfecto + " por " + duracion + " turnos!");
                    break;
                    
                case MEJORAR_ATAQUE:
                    objetivo.setAtaque(objetivo.getAtaque() + valorEfecto);
                    RegistroBatalla.RegistrarTextos(usuario.getNombre() + " usó " + nombre + 
                        " en " + objetivo.getNombre() + " y aumentó su ataque en " + valorEfecto + " por " + duracion + " turnos!");
                    break;
            }
            return true;
        } catch (Exception e) {
            RegistroBatalla.RegistrarTextos("Error al usar el objeto: " + e.getMessage());
            return false;
        }
    }

    @Override
    public String toString() {
        return nombre + " - " + descripcion + " (Efecto: " + valorEfecto + ")";
    }
    
}
