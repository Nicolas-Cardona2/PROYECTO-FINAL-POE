package modelo;

/**
 * Representa una solicitud de servicio en el gremio
 * Cada héroe que llega al gremio genera una solicitud
 */
public class SolicitudGremio {
    
    private int numeroTurno;           // Número de turno asignado
    private String nombreHeroe;        // Nombre del héroe que solicita
    private String tipoServicio;       // Qué servicio necesita
    private String estado;             // "En espera", "En atención", "Completado"
    private String horaLlegada;        // Hora en que llegó
    
    public SolicitudGremio(int numeroTurno, String nombreHeroe, String tipoServicio) {
        this.numeroTurno = numeroTurno;
        this.nombreHeroe = nombreHeroe;
        this.tipoServicio = tipoServicio;
        this.estado = "En espera"; // Por defecto está esperando
        this.horaLlegada = obtenerHoraActual();
    }
    
    /**
     * Obtiene la hora actual del sistema
     * retorna String con formato HH:mm:ss
     */
    private String obtenerHoraActual() {
        java.time.LocalTime ahora = java.time.LocalTime.now();
        return String.format("%02d:%02d:%02d", 
                           ahora.getHour(), 
                           ahora.getMinute(), 
                           ahora.getSecond());
    }
    
    public int getNumeroTurno() {
        return numeroTurno;
    }
    
    public String getNombreHeroe() {
        return nombreHeroe;
    }
    
    public String getTipoServicio() {
        return tipoServicio;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getHoraLlegada() {
        return horaLlegada;
    }
    
    /**
     * Convierte la solicitud a texto legible
     * Útil para mostrar en pantalla o registros
     */
    @Override
    public String toString() {
        return String.format("Turno #%d - %s - Servicio: %s - Estado: %s - Hora: %s",
                           numeroTurno, nombreHeroe, tipoServicio, estado, horaLlegada);
    }
}