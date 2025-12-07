package vista;

import controlador.ControladorJuego;
import java.awt.*;
import javax.swing.*;

public class MenuPrincipal extends JFrame {
    
    private ControladorJuego controlador;
    private JButton btnNuevaBatalla;
    private JButton btnSistemaGremio;
    private JButton btnCargarPartida;
    private JButton btnHistorial;
    private JButton btnSalir;
    
    public MenuPrincipal(ControladorJuego controlador) {
        this.controlador = controlador;
        configurarVentana();
        crearComponentes();
    }
    
    private void configurarVentana() {
        setTitle("Dragon Quest VIII - Menú Principal");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(15, 15, 35));
    }
    
    private void crearComponentes() {
        // Panel superior con el título del juego
        JPanel panelTituloCompleto = new JPanel();
        panelTituloCompleto.setLayout(new BoxLayout(panelTituloCompleto, BoxLayout.Y_AXIS));
        panelTituloCompleto.setBackground(new Color(15, 15, 35));
        
        JLabel lblTitulo = new JLabel("DRAGON QUEST VIII");
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 48));
        lblTitulo.setForeground(new Color(255, 215, 0));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblSubtitulo = new JLabel("El Periplo del Rey Maldito");
        lblSubtitulo.setFont(new Font("Serif", Font.ITALIC, 20));
        lblSubtitulo.setForeground(new Color(200, 200, 220));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelTituloCompleto.add(Box.createVerticalStrut(30));
        panelTituloCompleto.add(lblTitulo);
        panelTituloCompleto.add(Box.createVerticalStrut(10));
        panelTituloCompleto.add(lblSubtitulo);
        
        add(panelTituloCompleto, BorderLayout.NORTH);
        
        // Panel central con los botones del menú
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setBackground(new Color(15, 15, 35));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(50, 200, 50, 200));
        
        // Crear botones con estilo
        btnNuevaBatalla = crearBoton("⚔ Nueva Batalla");
        btnSistemaGremio = crearBoton("🏰 Sistema de Turnos al Gremio");
        btnCargarPartida = crearBoton("📂 Cargar Partida");
        btnHistorial = crearBoton("📜 Ver Historial de Partidas");
        btnSalir = crearBoton("🚪 Salir");
        
        // Agregar botones al panel con espaciado
        panelBotones.add(btnNuevaBatalla);
        panelBotones.add(Box.createVerticalStrut(15));
        panelBotones.add(btnSistemaGremio);
        panelBotones.add(Box.createVerticalStrut(15));
        panelBotones.add(btnCargarPartida);
        panelBotones.add(Box.createVerticalStrut(15));
        panelBotones.add(btnHistorial);
        panelBotones.add(Box.createVerticalStrut(15));
        panelBotones.add(btnSalir);
        
        add(panelBotones, BorderLayout.CENTER);
        
        // Panel inferior con información
        JPanel panelFooter = new JPanel();
        panelFooter.setBackground(new Color(15, 15, 35));
        panelFooter.setPreferredSize(new Dimension(800, 50));
        
        JLabel lblVersion = new JLabel("Versión 1.0 - Proyecto Final");
        lblVersion.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblVersion.setForeground(new Color(150, 150, 170));
        panelFooter.add(lblVersion);
        
        add(panelFooter, BorderLayout.SOUTH);
        
        // Configurar eventos de los botones
        configurarEventos();
    }
    
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("SansSerif", Font.BOLD, 18));
        boton.setPreferredSize(new Dimension(400, 50));
        boton.setMaximumSize(new Dimension(400, 50));
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Estilo del botón
        boton.setBackground(new Color(40, 60, 100));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0), 2),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        
        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(60, 80, 120));
                boton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(255, 215, 0), 3),
                    BorderFactory.createEmptyBorder(10, 20, 10, 20)
                ));
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(40, 60, 100));
                boton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(255, 215, 0), 2),
                    BorderFactory.createEmptyBorder(10, 20, 10, 20)
                ));
            }
        });
        
        return boton;
    }
    
    private void configurarEventos() {
        btnNuevaBatalla.addActionListener(e -> {
            controlador.iniciarNuevaBatalla();
            this.dispose(); // Cerrar menú principal
        });
        
        btnSistemaGremio.addActionListener(e -> {
            controlador.abrirSistemaGremio();
        });
        
        btnCargarPartida.addActionListener(e -> {
            controlador.cargarPartida();
            this.dispose(); // Cerrar menú si carga exitosa
        });
        
        btnHistorial.addActionListener(e -> {
            controlador.mostrarHistorial();
        });
        
        btnSalir.addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de que deseas salir?",
                "Confirmar Salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                controlador.detenerMusica();
                System.exit(0);
            }
        });
    }
}