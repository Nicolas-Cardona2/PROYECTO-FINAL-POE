package modelo;
import java.util.ArrayList;


public class EstadoJuego {
    private ArrayList<Heroe> heroes;
    private ArrayList<Monstruo> monstruos;
    private String descripcion;
    
    public EstadoJuego(ArrayList<Heroe> heroes, ArrayList<Monstruo> monstruos, String descripcion) {
        // Crear copias exactas de los personajes
        this.heroes = new ArrayList<>();
        for (Heroe heroe : heroes) {
            this.heroes.add(clonarHeroe(heroe));
        }
        
        this.monstruos = new ArrayList<>();
        for (Monstruo monstruo : monstruos) {
            this.monstruos.add(clonarMonstruo(monstruo));
        }
        
        this.descripcion = descripcion;
    }
    
    private Heroe clonarHeroe(Heroe original) {
        Heroe copia = new Heroe(
            original.getNombre(),
            original.getHP(),
            original.getMP(),
            original.getAtaque(),
            original.getDefensa(),
            original.getVelocidad(),
            original.getTipoPersonaje(),
            original.getEstado(),
            original.getTurno()
        );
        
        // CRÍTICO: Clonar habilidades
        if (original.getHabilidades() != null) {
            for (Habilidad hab : original.getHabilidades()) {
                copia.agregarHabilidad(new Habilidad(
                    hab.getNombre(),
                    hab.getCostoMp(),
                    hab.getPoder(),
                    hab.getTipo()
                ));
            }
        }
        
        // CRÍTICO: Clonar inventario
        if (original.getInventario() != null) {
            copia.setInventario(original.getInventario()); // Ya crea una copia en el getter
        }
        
        // CRÍTICO: Clonar catálogo de objetos
        if (original.getCatalogoObjetos() != null) {
            copia.setCatalogoObjetos(original.getCatalogoObjetos());
        }
        
        // CRÍTICO: Preservar referencias de la UI
        copia.setBoton(original.getBoton());
        copia.setBarraHP(original.getBarraHP());
        copia.setBarraMP(original.getBarraMP());
        copia.setBarraEstado(original.getBarraEstado());
        
        return copia;
    }
    
    private Monstruo clonarMonstruo(Monstruo original) {
        Monstruo copia = new Monstruo(
            original.getNombre(),
            original.getHP(),
            original.getMP(),
            original.getAtaque(),
            original.getDefensa(),
            original.getVelocidad(),
            original.getTipoPersonaje(),
            original.getEstado(),
            original.getTurno()
        );
        
        // CRÍTICO: Clonar habilidades
        if (original.getHabilidades() != null) {
            for (Habilidad hab : original.getHabilidades()) {
                copia.agregarHabilidad(new Habilidad(
                    hab.getNombre(),
                    hab.getCostoMp(),
                    hab.getPoder(),
                    hab.getTipo()
                ));
            }
        }
        
        // CRÍTICO: Preservar referencias de la UI
        copia.setBoton(original.getBoton());
        copia.setBarraHP(original.getBarraHP());
        copia.setBarraMP(original.getBarraMP());
        copia.setBarraEstado(original.getBarraEstado());
        
        return copia;
    }
    
    public ArrayList<Heroe> getHeroes() {
        return heroes;
    }
    
    public ArrayList<Monstruo> getMonstruos() {
        return monstruos;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}