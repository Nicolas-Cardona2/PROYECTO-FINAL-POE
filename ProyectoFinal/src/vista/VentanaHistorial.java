package vista;

import controlador.ControladorJuego;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentanaHistorial extends JFrame {
    
    private ControladorJuego controlador;
    private JTable tablaHistorial;
    private DefaultTableModel modeloTabla;
    private JButton btnCerrar;
    private JButton btnEliminar;
    private JButton btnCargar;
    
    public VentanaHistorial(ControladorJuego controlador) {
        this.controlador = controlador;
        configurarVentana();
        crearComponentes();
    }
    
    private void configurarVentana() {
        setTitle("Dragon Quest VIII - Historial de Partidas");
        setSize(1000, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(20, 20, 40));
    }
    
    private void crearComponentes() {
        // Panel superior con título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(20, 20, 70));
        panelTitulo.setPreferredSize(new Dimension(1000, 80));
        
        JLabel lblTitulo = new JLabel("📜 HISTORIAL DE PARTIDAS");
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 32));
        lblTitulo.setForeground(new Color(255, 215, 0));
        panelTitulo.add(lblTitulo);
        
        add(panelTitulo, BorderLayout.NORTH);
        
        // Panel central con la tabla
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBackground(new Color(30, 30, 50));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Crear modelo de tabla
        String[] columnas = {"ID", "Fecha", "Turno", "Héroes Vivos", "Monstruos Vivos", "Resultado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Crear tabla
        tablaHistorial = new JTable(modeloTabla);
        tablaHistorial.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tablaHistorial.setRowHeight(30);
        tablaHistorial.setBackground(new Color(40, 40, 60));
        tablaHistorial.setForeground(Color.WHITE);
        tablaHistorial.setGridColor(new Color(100, 100, 120));
        tablaHistorial.setSelectionBackground(new Color(70, 90, 120));
        tablaHistorial.setSelectionForeground(Color.WHITE);
        
        // Estilo del encabezado
        tablaHistorial.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tablaHistorial.getTableHeader().setBackground(new Color(50, 50, 80));
        tablaHistorial.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(tablaHistorial);
        scrollPane.getViewport().setBackground(new Color(30, 30, 50));
        panelCentral.add(scrollPane, BorderLayout.CENTER);
        
        add(panelCentral, BorderLayout.CENTER);
        
        // Panel inferior con botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(new Color(25, 25, 45));
        panelBotones.setPreferredSize(new Dimension(1000, 70));
        
        btnCargar = new JButton("📂 Cargar Seleccionada");
        btnCargar.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnCargar.setPreferredSize(new Dimension(230, 45));
        btnCargar.setBackground(new Color(50, 90, 50));
        btnCargar.setForeground(Color.WHITE);
        btnCargar.setFocusPainted(false);
        btnCargar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 180, 100), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        btnEliminar = new JButton("🗑 Eliminar Seleccionada");
        btnEliminar.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnEliminar.setPreferredSize(new Dimension(250, 45));
        btnEliminar.setBackground(new Color(120, 50, 50));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 100, 100), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        btnCerrar = new JButton("✖ Cerrar");
        btnCerrar.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnCerrar.setPreferredSize(new Dimension(150, 45));
        btnCerrar.setBackground(new Color(70, 70, 90));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(150, 150, 170), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        panelBotones.add(btnCargar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCerrar);
        
        add(panelBotones, BorderLayout.SOUTH);
        
        // Configurar eventos
        configurarEventos();
    }
    
    private void configurarEventos() {
        btnCargar.addActionListener(e -> {
            int filaSeleccionada = tablaHistorial.getSelectedRow();
            
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(
                    this,
                    "Por favor, selecciona una partida para cargar.",
                    "No hay selección",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            
            String id = modeloTabla.getValueAt(filaSeleccionada, 0).toString();
            controlador.cargarPartidaPorId(id);
            this.dispose();
        });
        
        btnEliminar.addActionListener(e -> {
            int filaSeleccionada = tablaHistorial.getSelectedRow();
            
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(
                    this,
                    "Por favor, selecciona una partida para eliminar.",
                    "No hay selección",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            
            String id = modeloTabla.getValueAt(filaSeleccionada, 0).toString();
            
            int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de que deseas eliminar la partida " + id + "?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean eliminado = controlador.eliminarPartida(id);
                if (eliminado) {
                    modeloTabla.removeRow(filaSeleccionada);
                    JOptionPane.showMessageDialog(
                        this,
                        "Partida eliminada exitosamente.",
                        "Eliminación Exitosa",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });
        
        btnCerrar.addActionListener(e -> {
            dispose();
        });
    }
    
    // Método público para actualizar la tabla desde el controlador
    public void actualizarTabla(Object[][] datos) {
        modeloTabla.setRowCount(0);
        for (Object[] fila : datos) {
            modeloTabla.addRow(fila);
        }
    }
}