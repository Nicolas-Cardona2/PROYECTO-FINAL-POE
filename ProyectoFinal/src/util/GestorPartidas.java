package util;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import modelo.*;

/**
 * Clase que gestiona el guardado y carga de partidas en archivos TXT
 * Guarda las partidas en la carpeta HistorialPartidas/
 */
public class GestorPartidas {
    
    private static final String CARPETA_PARTIDAS = "HistorialPartidas";
    private static final String EXTENSION = ".txt";
    private static int contadorPartidas = 1;
    
    // Constructor que crea la carpeta si no existe
    public GestorPartidas() {
        crearCarpetaSiNoExiste();
        contadorPartidas = obtenerUltimoId() + 1;
    }
    
    /**
     * Crea la carpeta HistorialPartidas si no existe
     */
    private void crearCarpetaSiNoExiste() {
        File carpeta = new File(CARPETA_PARTIDAS);
        if (!carpeta.exists()) {
            carpeta.mkdir();
            System.out.println("Carpeta '" + CARPETA_PARTIDAS + "' creada exitosamente");
        }
    }
    
    /**
     * Obtiene el último ID de partida guardada
     */
    private int obtenerUltimoId() {
        File carpeta = new File(CARPETA_PARTIDAS);
        File[] archivos = carpeta.listFiles((dir, name) -> name.endsWith(EXTENSION));
        
        if (archivos == null || archivos.length == 0) {
            return 0;
        }
        
        int maxId = 0;
        for (File archivo : archivos) {
            String nombre = archivo.getName().replace("partida_", "").replace(EXTENSION, "");
            try {
                int id = Integer.parseInt(nombre);
                if (id > maxId) maxId = id;
            } catch (NumberFormatException e) {
                // Ignorar archivos con nombres no numéricos
            }
        }
        return maxId;
    }
    
    /**
     * Guarda una partida en un archivo TXT
     */
    public String guardarPartida(ArrayList<Heroe> heroes, ArrayList<Monstruo> monstruos, int turnoActual) {
        String id = String.format("%03d", contadorPartidas);
        String nombreArchivo = CARPETA_PARTIDAS + "/partida_" + id + EXTENSION;
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            // Fecha y hora
            String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.println("FECHA=" + fecha);
            writer.println("TURNO=" + turnoActual);
            
            // Contar vivos
            int heroesVivos = contarVivos(heroes);
            int monstruosVivos = contarVivos(monstruos);
            writer.println("HEROES_VIVOS=" + heroesVivos);
            writer.println("MONSTRUOS_VIVOS=" + monstruosVivos);
            
            // Resultado
            String resultado = calcularResultado(heroesVivos, monstruosVivos);
            writer.println("RESULTADO=" + resultado);
            
            // Separador
            writer.println("===HEROES===");
            
            // Guardar heroes
            for (Heroe h : heroes) {
                writer.println("HEROE_INICIO");
                writer.println("NOMBRE=" + h.getNombre());
                writer.println("HP=" + h.getHP());
                writer.println("MP=" + h.getMP());
                writer.println("ATK=" + h.getAtaque());
                writer.println("DEF=" + h.getDefensa());
                writer.println("SPEED=" + h.getVelocidad());
                writer.println("TIPO=" + h.getTipoPersonaje());
                writer.println("ESTADO=" + h.getEstado());
                writer.println("TURNOS=" + h.getTurno());
                
                // Guardar habilidades
                writer.println("HABILIDADES_INICIO");
                for (Habilidad hab : h.getHabilidades()) {
                    writer.println("HAB=" + hab.getNombre() + "," + hab.getCostoMp() + "," + hab.getPoder() + "," + hab.getTipo());
                }
                writer.println("HABILIDADES_FIN");
                
                // Guardar inventario
                writer.println("INVENTARIO_INICIO");
                writer.println("HIERBA=" + h.getCantidadObjeto("Hierba medicinal"));
                writer.println("ANTIDOTO=" + h.getCantidadObjeto("Antidoto"));
                writer.println("PIEDRA=" + h.getCantidadObjeto("Piedra del alma"));
                writer.println("SEMILLA=" + h.getCantidadObjeto("Semilla de habilidad"));
                writer.println("COLA=" + h.getCantidadObjeto("Cola de lagarto"));
                writer.println("INVENTARIO_FIN");
                
                writer.println("HEROE_FIN");
            }
            
            // Separador
            writer.println("===MONSTRUOS===");
            
            // Guardar monstruos
            for (Monstruo m : monstruos) {
                writer.println("MONSTRUO_INICIO");
                writer.println("NOMBRE=" + m.getNombre());
                writer.println("HP=" + m.getHP());
                writer.println("MP=" + m.getMP());
                writer.println("ATK=" + m.getAtaque());
                writer.println("DEF=" + m.getDefensa());
                writer.println("SPEED=" + m.getVelocidad());
                writer.println("TIPO=" + m.getTipoPersonaje());
                writer.println("ESTADO=" + m.getEstado());
                writer.println("TURNOS=" + m.getTurno());
                
                // Guardar habilidades
                writer.println("HABILIDADES_INICIO");
                for (Habilidad hab : m.getHabilidades()) {
                    writer.println("HAB=" + hab.getNombre() + "," + hab.getCostoMp() + "," + hab.getPoder() + "," + hab.getTipo());
                }
                writer.println("HABILIDADES_FIN");
                
                writer.println("MONSTRUO_FIN");
            }
            
            contadorPartidas++;
            System.out.println("Partida guardada: " + nombreArchivo);
            return id;
            
        } catch (IOException e) {
            System.err.println("Error al guardar partida: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Carga una partida desde un archivo TXT
     */
    public DatosPartida cargarPartida(String id) {
        String nombreArchivo = CARPETA_PARTIDAS + "/partida_" + id + EXTENSION;
        File archivo = new File(nombreArchivo);
        
        if (!archivo.exists()) {
            System.err.println("Partida no encontrada: " + nombreArchivo);
            return null;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            DatosPartida datos = new DatosPartida();
            String linea;
            
            // Leer encabezado
            while ((linea = reader.readLine()) != null) {
                if (linea.startsWith("FECHA=")) {
                    datos.fecha = linea.substring(6);
                } else if (linea.startsWith("TURNO=")) {
                    datos.turno = Integer.parseInt(linea.substring(6));
                } else if (linea.equals("===HEROES===")) {
                    datos.heroes = cargarHeroes(reader);
                } else if (linea.equals("===MONSTRUOS===")) {
                    datos.monstruos = cargarMonstruos(reader);
                    break;
                }
            }
            
            System.out.println("Partida cargada: " + nombreArchivo);
            return datos;
            
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al cargar partida: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Carga los héroes desde el archivo
     */
    private ArrayList<Heroe> cargarHeroes(BufferedReader reader) throws IOException {
        ArrayList<Heroe> heroes = new ArrayList<>();
        String linea;
        
        while ((linea = reader.readLine()) != null) {
            if (linea.equals("===MONSTRUOS===")) {
                break;
            }
            
            if (linea.equals("HEROE_INICIO")) {
                Heroe heroe = cargarHeroe(reader);
                if (heroe != null) {
                    heroes.add(heroe);
                }
            }
        }
        
        return heroes;
    }
    
    /**
     * Carga un héroe individual
     */
    private Heroe cargarHeroe(BufferedReader reader) throws IOException {
        String nombre = "", estado = "";
        int hp = 0, mp = 0, atk = 0, def = 0, speed = 0, turnos = 0;
        TipoPersonajes tipo = null;
        ArrayList<Habilidad> habilidades = new ArrayList<>();
        
        String linea;
        while ((linea = reader.readLine()) != null) {
            if (linea.equals("HEROE_FIN")) {
                break;
            }
            
            if (linea.startsWith("NOMBRE=")) nombre = linea.substring(7);
            else if (linea.startsWith("HP=")) hp = Integer.parseInt(linea.substring(3));
            else if (linea.startsWith("MP=")) mp = Integer.parseInt(linea.substring(3));
            else if (linea.startsWith("ATK=")) atk = Integer.parseInt(linea.substring(4));
            else if (linea.startsWith("DEF=")) def = Integer.parseInt(linea.substring(4));
            else if (linea.startsWith("SPEED=")) speed = Integer.parseInt(linea.substring(6));
            else if (linea.startsWith("TIPO=")) tipo = TipoPersonajes.valueOf(linea.substring(5));
            else if (linea.startsWith("ESTADO=")) estado = linea.substring(7);
            else if (linea.startsWith("TURNOS=")) turnos  = Integer.parseInt(linea.substring(15));
            else if (linea.equals("HABILIDADES_INICIO")) {
                habilidades = cargarHabilidades(reader);
            } else if (linea.equals("INVENTARIO_INICIO")) {
                // Saltar inventario por ahora (se puede cargar después si necesitas)
                while ((linea = reader.readLine()) != null && !linea.equals("INVENTARIO_FIN"));
            }
        }
        
        Heroe heroe = new Heroe(nombre, hp, mp, atk, def, speed, tipo, estado, turnos );
        for (Habilidad hab : habilidades) {
            heroe.agregarHabilidad(hab);
        }
        
        return heroe;
    }
    
    /**
     * Carga los monstruos desde el archivo
     */
    private ArrayList<Monstruo> cargarMonstruos(BufferedReader reader) throws IOException {
        ArrayList<Monstruo> monstruos = new ArrayList<>();
        String linea;
        
        while ((linea = reader.readLine()) != null) {
            if (linea.equals("MONSTRUO_INICIO")) {
                Monstruo monstruo = cargarMonstruo(reader);
                if (monstruo != null) {
                    monstruos.add(monstruo);
                }
            }
        }
        
        return monstruos;
    }
    
    /**
     * Carga un monstruo individual
     */
    private Monstruo cargarMonstruo(BufferedReader reader) throws IOException {
        String nombre = "", estado = "";
        int hp = 0, mp = 0, atk = 0, def = 0, speed = 0, turnos = 0;
        TipoPersonajes tipo = null;
        ArrayList<Habilidad> habilidades = new ArrayList<>();
        
        String linea;
        while ((linea = reader.readLine()) != null) {
            if (linea.equals("MONSTRUO_FIN")) {
                break;
            }
            
            if (linea.startsWith("NOMBRE=")) nombre = linea.substring(7);
            else if (linea.startsWith("HP=")) hp = Integer.parseInt(linea.substring(3));
            else if (linea.startsWith("MP=")) mp = Integer.parseInt(linea.substring(3));
            else if (linea.startsWith("ATK=")) atk = Integer.parseInt(linea.substring(4));
            else if (linea.startsWith("DEF=")) def = Integer.parseInt(linea.substring(4));
            else if (linea.startsWith("SPEED=")) speed = Integer.parseInt(linea.substring(6));
            else if (linea.startsWith("TIPO=")) tipo = TipoPersonajes.valueOf(linea.substring(5));
            else if (linea.startsWith("ESTADO=")) estado = linea.substring(7);
            else if (linea.startsWith("TURNOS=")) turnos = Integer.parseInt(linea.substring(15));
            else if (linea.equals("HABILIDADES_INICIO")) {
                habilidades = cargarHabilidades(reader);
            }
        }
        
        Monstruo monstruo = new Monstruo(nombre, hp, mp, atk, def, speed, tipo, estado, turnos);
        for (Habilidad hab : habilidades) {
            monstruo.agregarHabilidad(hab);
        }
        
        return monstruo;
    }
    
    /**
     * Carga las habilidades desde el archivo
     */
    private ArrayList<Habilidad> cargarHabilidades(BufferedReader reader) throws IOException {
        ArrayList<Habilidad> habilidades = new ArrayList<>();
        String linea;
        
        while ((linea = reader.readLine()) != null) {
            if (linea.equals("HABILIDADES_FIN")) {
                break;
            }
            
            if (linea.startsWith("HAB=")) {
                String[] partes = linea.substring(4).split(",");
                String nombre = partes[0];
                int costoMP = Integer.parseInt(partes[1]);
                int poder = Integer.parseInt(partes[2]);
                TipoHabilidad tipo = TipoHabilidad.valueOf(partes[3]);
                
                habilidades.add(new Habilidad(nombre, costoMP, poder, tipo));
            }
        }
        
        return habilidades;
    }
    
    /**
     * Obtiene todas las partidas guardadas para el historial
     */
    public ArrayList<Object[]> obtenerTodasLasPartidas() {
        ArrayList<Object[]> partidas = new ArrayList<>();
        File carpeta = new File(CARPETA_PARTIDAS);
        File[] archivos = carpeta.listFiles((dir, name) -> name.endsWith(EXTENSION));
        
        if (archivos == null) {
            return partidas;
        }
        
        for (File archivo : archivos) {
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                String id = archivo.getName().replace("partida_", "").replace(EXTENSION, "");
                String fecha = "";
                int turno = 0;
                int heroesVivos = 0, monstruosVivos = 0;
                String resultado = "";
                
                String linea;
                while ((linea = reader.readLine()) != null) {
                    if (linea.startsWith("FECHA=")) fecha = linea.substring(6);
                    else if (linea.startsWith("TURNO=")) turno = Integer.parseInt(linea.substring(6));
                    else if (linea.startsWith("HEROES_VIVOS=")) heroesVivos = Integer.parseInt(linea.substring(13));
                    else if (linea.startsWith("MONSTRUOS_VIVOS=")) monstruosVivos = Integer.parseInt(linea.substring(16));
                    else if (linea.startsWith("RESULTADO=")) {
                        resultado = linea.substring(10);
                        break;
                    }
                }
                
                partidas.add(new Object[]{
                    id,
                    fecha,
                    String.valueOf(turno),
                    heroesVivos + "/4",
                    monstruosVivos + "/4",
                    resultado
                });
                
            } catch (IOException | NumberFormatException e) {
                System.err.println("Error al leer archivo: " + archivo.getName());
            }
        }
        
        return partidas;
    }
    
    /**
     * Elimina una partida del historial
     */
    public boolean eliminarPartida(String id) {
        String nombreArchivo = CARPETA_PARTIDAS + "/partida_" + id + EXTENSION;
        File archivo = new File(nombreArchivo);
        
        if (archivo.exists()) {
            boolean eliminado = archivo.delete();
            if (eliminado) {
                System.out.println("Partida eliminada: " + nombreArchivo);
            }
            return eliminado;
        }
        
        return false;
    }
    
    /**
     * Métodos auxiliares
     */
    private int contarVivos(ArrayList<? extends Personaje> lista) {
        int count = 0;
        for (Personaje p : lista) {
            if (p.getEstado().equals("Vivo")) {
                count++;
            }
        }
        return count;
    }
    
    private String calcularResultado(int heroesVivos, int monstruosVivos) {
        if (heroesVivos == 0) return "Derrota";
        if (monstruosVivos == 0) return "Victoria";
        return "En Progreso";
    }
    
    /**
     * Clase interna para retornar datos de partida cargada
     */
    public static class DatosPartida {
        public String fecha;
        public int turno;
        public ArrayList<Heroe> heroes;
        public ArrayList<Monstruo> monstruos;
    }
}