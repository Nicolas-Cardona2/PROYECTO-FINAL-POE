package modelo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Sistema de gestión de turnos para el gremio
 * Maneja la cola de espera y atención de héroes
 */
public class SistemaGremio {
    
    // Cola para las solicitudes en espera 
    private Queue<SolicitudGremio> colaEspera;
    
    // Lista de solicitudes completadas 
    private ArrayList<SolicitudGremio> solicitudesCompletadas;
    
    // Solicitud que se está atendiendo actualmente
    private SolicitudGremio solicitudEnAtencion;
    
    // Contador para asignar números de turno
    private int contadorTurnos;
    
    // Servicios disponibles en el gremio
    private String[] serviciosDisponibles = {
        "💊 Curación Completa",
        "⚔️ Mejora de Armas",
        "🛡️ Mejora de Armadura",
        "📜 Aceptar Misión",
        "💰 Cobrar Recompensa",
        "🎒 Comprar Objetos"
    };
    
    /**
     * Constructor: inicializa el sistema
     */
    public SistemaGremio() {
        // LinkedList es perfecta para colas (agregar al final, sacar del inicio)
        this.colaEspera = new LinkedList<>();
        this.solicitudesCompletadas = new ArrayList<>();
        this.solicitudEnAtencion = null;
        this.contadorTurnos = 1; // Comienza en turno #1
    }
    
    public SolicitudGremio agregarSolicitud(String nombreHeroe, String tipoServicio) {
        // Crear nueva solicitud con turno incrementado
        SolicitudGremio nuevaSolicitud = new SolicitudGremio(
            contadorTurnos++, 
            nombreHeroe, 
            tipoServicio
        );
        
        // Agregar al final de la cola
        colaEspera.offer(nuevaSolicitud); // offer() agrega al final
        
        RegistroBatalla.RegistrarTextos("✅ Nueva solicitud agregada: " + nuevaSolicitud);
        
        return nuevaSolicitud;
    }
    
    /**
     * Atiende la siguiente solicitud en la cola
     * @return true si hay solicitud para atender, false si cola vacía
     */
    public boolean atenderSiguiente() {
        // Si ya hay alguien en atención, no se puede atender otro
        if (solicitudEnAtencion != null) {
            RegistroBatalla.RegistrarTextos("⚠️ Ya hay una solicitud en atención");
            return false;
        }
        
        // Si la cola está vacía, no hay nadie que atender
        if (colaEspera.isEmpty()) {
            RegistroBatalla.RegistrarTextos("ℹ️ No hay solicitudes en espera");
            return false;
        }
        
        // Sacar el primero de la cola (poll() remueve y devuelve el primero)
        solicitudEnAtencion = colaEspera.poll();
        solicitudEnAtencion.setEstado("En atención");
        
        RegistroBatalla.RegistrarTextos("🔔 Atendiendo: " + solicitudEnAtencion);
        
        return true;
    }
    
    /**
     * Completa la atención actual
     * si retorna true si se completó exitosamente
     */
    public boolean completarAtencion() {
        if (solicitudEnAtencion == null) {
            RegistroBatalla.RegistrarTextos("⚠️ No hay ninguna solicitud en atención");
            return false;
        }
        
        // Cambiar estado y mover a completadas
        solicitudEnAtencion.setEstado("Completado");
        solicitudesCompletadas.add(solicitudEnAtencion);
        
        RegistroBatalla.RegistrarTextos("✅ Atención completada: " + solicitudEnAtencion);
        
        // Limpiar la atención actual
        solicitudEnAtencion = null;
        
        return true;
    }
    
    /**
     * Cancela la solicitud actualmente en atención
     * La devuelve al inicio de la cola
     */
    public void cancelarAtencion() {
        if (solicitudEnAtencion != null) {
            solicitudEnAtencion.setEstado("En espera");
            
            // Crear nueva cola temporal
            Queue<SolicitudGremio> colaTemp = new LinkedList<>();
            colaTemp.offer(solicitudEnAtencion); // Agregar primero la cancelada
            
            // Agregar el resto de la cola original
            while (!colaEspera.isEmpty()) {
                colaTemp.offer(colaEspera.poll());
            }
            
            // Reemplazar cola original
            colaEspera = colaTemp;
            
            RegistroBatalla.RegistrarTextos("↩️ Atención cancelada, turno devuelto");
            
            solicitudEnAtencion = null;
        }
    }
    
    /**
     * Obtiene información de las solicitudes en espera
     * @return array con los datos para mostrar en tabla
     */
    public Object[][] obtenerDatosEspera() {
        Object[][] datos = new Object[colaEspera.size()][4];
        
        int i = 0;
        // Recorrer cola sin modificarla
        for (SolicitudGremio solicitud : colaEspera) {
            datos[i][0] = solicitud.getNumeroTurno();
            datos[i][1] = solicitud.getNombreHeroe();
            datos[i][2] = solicitud.getTipoServicio();
            datos[i][3] = solicitud.getHoraLlegada();
            i++;
        }
        
        return datos;
    }
    
    /**
     * Obtiene información de las solicitudes completadas
     * @return array con los datos para mostrar en tabla
     */
    public Object[][] obtenerDatosCompletados() {
        Object[][] datos = new Object[solicitudesCompletadas.size()][4];
        
        for (int i = 0; i < solicitudesCompletadas.size(); i++) {
            SolicitudGremio solicitud = solicitudesCompletadas.get(i);
            datos[i][0] = solicitud.getNumeroTurno();
            datos[i][1] = solicitud.getNombreHeroe();
            datos[i][2] = solicitud.getTipoServicio();
            datos[i][3] = solicitud.getHoraLlegada();
        }
        
        return datos;
    }
    
    public SolicitudGremio getSolicitudEnAtencion() {
        return solicitudEnAtencion;
    }
    
    public int getCantidadEnEspera() {
        return colaEspera.size();
    }
    
    public int getTotalCompletados() {
        return solicitudesCompletadas.size();
    }
    
    public String[] getServiciosDisponibles() {
        return serviciosDisponibles;
    }
    
    /**
     * Reinicia el sistema 
     */
    public void reiniciarSistema() {
        colaEspera.clear();
        solicitudesCompletadas.clear();
        solicitudEnAtencion = null;
        contadorTurnos = 1;
        
        RegistroBatalla.RegistrarTextos("🔄 Sistema de gremio reiniciado");
    }
}