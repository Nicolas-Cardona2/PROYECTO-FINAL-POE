package vista;

import controlador.ControladorJuego;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.SistemaGremio;
import modelo.SolicitudGremio;

/**
 * Ventana para el sistema de turnos del gremio
 * Muestra la cola de espera, atención actual y completados
 */
public class VentanaGremio extends JFrame {
    
    private ControladorJuego controlador;
    private SistemaGremio sistemaGremio;
    
    // Componentes de interfaz
    private JTable tablaEspera;
    private JTable tablaCompletados;
    private DefaultTableModel modeloEspera;
    private DefaultTableModel modeloCompletados;
    
    private JLabel lblTurnoActual;
    private JLabel lblHeroeActual;
    private JLabel lblServicioActual;
    
    private JButton btnNuevaSolicitud;
    private JButton btnAtenderSiguiente;
    private JButton btnCompletarAtencion;
    private JButton btnCancelarAtencion;
    private JButton btnActualizar;
    private JButton btnCerrar;
    
    private JLabel lblEstadisticas;
    
    /**
     * Constructor: crea y configura la ventana
     */
    public VentanaGremio(ControladorJuego controlador, SistemaGremio sistemaGremio) {
        this.controlador = controlador;
        this.sistemaGremio = sistemaGremio;
        
        configurarVentana();
        crearComponentes();
        actualizarVista();
    }
    
    /**
     * Configura propiedades básicas de la ventana
     */
    private void configurarVentana() {
        setTitle("🏰 Sistema de Turnos - Gremio de Aventureros");
        setSize(1000, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Solo cierra esta ventana
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(30, 30, 50));
    }
    
    /**
     * Crea todos los componentes de la interfaz
     */
    private void crearComponentes() {
        
        // ===== PANEL SUPERIOR: Título =====
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(20, 20, 40));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        
        JLabel lblTitulo = new JLabel("🏰 SISTEMA DE TURNOS DEL GREMIO 🏰");
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(255, 215, 0));
        panelTitulo.add(lblTitulo);
        
        add(panelTitulo, BorderLayout.NORTH);
        
        // ===== PANEL CENTRAL: Contenido principal =====
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        panelCentral.setBackground(new Color(30, 30, 50));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // --- Panel de atención actual ---
        JPanel panelAtencion = crearPanelAtencionActual();
        panelCentral.add(panelAtencion, BorderLayout.NORTH);
        
        // --- Panel con las tablas ---
        JPanel panelTablas = crearPanelTablas();
        panelCentral.add(panelTablas, BorderLayout.CENTER);
        
        add(panelCentral, BorderLayout.CENTER);
        
        // ===== PANEL DERECHO: Botones de control =====
        JPanel panelBotones = crearPanelBotones();
        add(panelBotones, BorderLayout.EAST);
        
        // ===== PANEL INFERIOR: Estadísticas =====
        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(new Color(20, 20, 40));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        lblEstadisticas = new JLabel("En espera: 0 | Completados: 0");
        lblEstadisticas.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblEstadisticas.setForeground(Color.WHITE);
        panelInferior.add(lblEstadisticas);
        
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    /**
     * Crea el panel que muestra la atención actual
     */
    private JPanel crearPanelAtencionActual() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 0));
        panel.setBackground(new Color(40, 60, 100));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0), 2),
            "🔔 ATENCIÓN ACTUAL",
            0, 0,
            new Font("SansSerif", Font.BOLD, 14),
            Color.WHITE
        ));
        
        // Turno actual
        lblTurnoActual = crearEtiquetaInfo("Turno: --");
        panel.add(lblTurnoActual);
        
        // Héroe actual
        lblHeroeActual = crearEtiquetaInfo("Héroe: --");
        panel.add(lblHeroeActual);
        
        // Servicio actual
        lblServicioActual = crearEtiquetaInfo("Servicio: --");
        panel.add(lblServicioActual);
        
        return panel;
    }
    
    /**
     * Crea una etiqueta con formato para información
     */
    private JLabel crearEtiquetaInfo(String texto) {
        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 16));
        lbl.setForeground(Color.WHITE);
        return lbl;
    }
    
    /**
     * Crea el panel con las tablas de espera y completados
     */
    private JPanel crearPanelTablas() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 0, 10));
        panel.setBackground(new Color(30, 30, 50));
        
        // Tabla de espera
        String[] columnasEspera = {"Turno", "Héroe", "Servicio", "Hora Llegada"};
        modeloEspera = new DefaultTableModel(columnasEspera, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No editable
            }
        };
        
        tablaEspera = new JTable(modeloEspera);
        tablaEspera.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tablaEspera.setRowHeight(25);
        tablaEspera.setBackground(new Color(250, 250, 250));
        
            JScrollPane scrollEspera = new JScrollPane(tablaEspera);
        scrollEspera.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(100, 150, 200), 2),
            "⏳ EN ESPERA",
            0, 0,
            new Font("SansSerif", Font.BOLD, 14),
            Color.WHITE
        ));
        
        panel.add(scrollEspera);
        
        // Tabla de completados
        String[] columnasCompletados = {"Turno", "Héroe", "Servicio", "Hora Llegada"};
        modeloCompletados = new DefaultTableModel(columnasCompletados, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaCompletados = new JTable(modeloCompletados);
        tablaCompletados.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tablaCompletados.setRowHeight(25);
        tablaCompletados.setBackground(new Color(240, 255, 240));
        
        JScrollPane scrollCompletados = new JScrollPane(tablaCompletados);
        scrollCompletados.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(100, 200, 100), 2),
            "✅ COMPLETADOS",
            0, 0,
            new Font("SansSerif", Font.BOLD, 14),
            Color.WHITE
        ));
        
        panel.add(scrollCompletados);
        
        return panel;
    }
    
    /**
     * Crea el panel con los botones de control
     */
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(25, 25, 45));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        panel.setPreferredSize(new Dimension(220, 0));
        
        // Crear botones
        btnNuevaSolicitud = crearBoton("➕ Nueva Solicitud", new Color(70, 130, 70));
        btnAtenderSiguiente = crearBoton("▶️ Atender Siguiente", new Color(70, 90, 150));
        btnCompletarAtencion = crearBoton("✅ Completar", new Color(100, 150, 100));
        btnCancelarAtencion = crearBoton("❌ Cancelar", new Color(150, 70, 70));
        btnActualizar = crearBoton("🔄 Actualizar", new Color(90, 90, 120));
        btnCerrar = crearBoton("🚪 Cerrar", new Color(80, 80, 80));
        
        // Agregar botones con espaciado
        panel.add(btnNuevaSolicitud);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnAtenderSiguiente);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnCompletarAtencion);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnCancelarAtencion);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnActualizar);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnCerrar);
        
        // Configurar eventos
        configurarEventos();
        
        return panel;
    }
    
    /**
     * Crea un botón con estilo personalizado
     */
    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("SansSerif", Font.BOLD, 14));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setMaximumSize(new Dimension(200, 40));
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return boton;
    }
    
    /**
     * Configura los eventos de los botones
     */
    private void configurarEventos() {
        
        // Nueva solicitud
        btnNuevaSolicitud.addActionListener(e -> {
            mostrarDialogoNuevaSolicitud();
        });
        
        // Atender siguiente
        btnAtenderSiguiente.addActionListener(e -> {
            sistemaGremio.atenderSiguiente();
            actualizarVista();
        });
        
        // Completar atención
        btnCompletarAtencion.addActionListener(e -> {
            if (sistemaGremio.completarAtencion()) {
                JOptionPane.showMessageDialog(
                    this,
                    "✅ ¡Atención completada exitosamente!",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
            actualizarVista();
        });
        
        // Cancelar atención
        btnCancelarAtencion.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas cancelar esta atención?",
                "Confirmar Cancelación",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                sistemaGremio.cancelarAtencion();
                actualizarVista();
            }
        });
        
        // Actualizar vista
        btnActualizar.addActionListener(e -> {
            actualizarVista();
            JOptionPane.showMessageDialog(
                this,
                "Vista actualizada",
                "Actualizar",
                JOptionPane.INFORMATION_MESSAGE
            );
        });
        
        // Cerrar ventana
        btnCerrar.addActionListener(e -> {
            this.dispose();
        });
    }
    
    /**
     * Muestra diálogo para crear nueva solicitud
     */
    private void mostrarDialogoNuevaSolicitud() {
        // Panel personalizado para el diálogo
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panel.add(new JLabel("Nombre del Héroe:"));
        JTextField txtNombre = new JTextField(15);
        panel.add(txtNombre);
        
        panel.add(new JLabel("Servicio:"));
        JComboBox<String> comboServicio = new JComboBox<>(sistemaGremio.getServiciosDisponibles());
        panel.add(comboServicio);
        
        int resultado = JOptionPane.showConfirmDialog(
            this,
            panel,
            "Nueva Solicitud de Servicio",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (resultado == JOptionPane.OK_OPTION) {
            String nombre = txtNombre.getText().trim();
            String servicio = (String) comboServicio.getSelectedItem();
            
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "El nombre del héroe no puede estar vacío",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            SolicitudGremio solicitud = sistemaGremio.agregarSolicitud(nombre, servicio);
            
            JOptionPane.showMessageDialog(
                this,
                "✅ Solicitud agregada\nTurno #" + solicitud.getNumeroTurno(),
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
            );
            
            actualizarVista();
        }
    }
    
    /**
     * Actualiza toda la vista con los datos actuales
     */
    public void actualizarVista() {
        
        // Actualizar atención actual
        SolicitudGremio actual = sistemaGremio.getSolicitudEnAtencion();
        
        if (actual != null) {
            lblTurnoActual.setText("Turno: #" + actual.getNumeroTurno());
            lblHeroeActual.setText("Héroe: " + actual.getNombreHeroe());
            lblServicioActual.setText("Servicio: " + actual.getTipoServicio());
        } else {
            lblTurnoActual.setText("Turno: --");
            lblHeroeActual.setText("Héroe: --");
            lblServicioActual.setText("Servicio: --");
        }
        
        // Actualizar tabla de espera
        modeloEspera.setRowCount(0); // Limpiar
        Object[][] datosEspera = sistemaGremio.obtenerDatosEspera();
        for (Object[] fila : datosEspera) {
            modeloEspera.addRow(fila);
        }
        
        // Actualizar tabla de completados
        modeloCompletados.setRowCount(0); // Limpiar
        Object[][] datosCompletados = sistemaGremio.obtenerDatosCompletados();
        for (Object[] fila : datosCompletados) {
            modeloCompletados.addRow(fila);
        }
        
        // Actualizar estadísticas
        lblEstadisticas.setText(String.format(
            "En espera: %d | Completados: %d | Total atendidos: %d",
            sistemaGremio.getCantidadEnEspera(),
            sistemaGremio.getTotalCompletados(),
            sistemaGremio.getTotalCompletados() + (actual != null ? 1 : 0)
        ));
    }
}