package vista;
/*aqui lo que se hace de todo lo que viene es una ventana normal, pero el que va a actuar full es el
 controlador ya que basicamente hace que las clases del modelo se conecten con esto, por el momento
 hare un commit de todo esto, no va a funcionar, o al menos aun no ya que necesito que reconozca a controlador
 como parte del proyecto pero por alguna razon no lo hace, la función de la vista no tiene ningun misterio
 lo unico que cambia es basicamente que usa a cotrolador para facilitar la comunicacion entre el resto
 de clases.
 */
import controlador.ControladorJuego;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import modelo.*;

public class VentanaBatalla extends JFrame {

    private ArrayList<Heroe> heroes;
    private ArrayList<Monstruo> monstruos;
    private ControladorJuego controlador;
    private JPanel panelHeroes, panelMonstruos;

    private JTextArea txtRegistro;
    private JButton botonAtk,botonDef,botonSkill,botonVolverJugar;
    //nuevo panel de objetos segun los objetos xd
    private JPanel panelObjetos;
    private JButton[] botonesObjetos;
    private JButton botonCancelarObjeto;
    private JButton botonMostrarInventarios;

    public VentanaBatalla(ArrayList<Heroe> heroes, ArrayList<Monstruo> monstruos, ControladorJuego controlador) {
        this.heroes = heroes;
        this.monstruos = monstruos;
        this.controlador = controlador;

        setTitle("Dragon Quest VIII - Batalla");
        setSize(1200, 700); //se aumenta el ancho para los objetos y todo ese cuento
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        //  Panel Superior: Título 
        JLabel titulo = new JLabel("...Ha Comenzado La Legendaria Batalla en Dragon Quest...", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);
        titulo.setOpaque(true);
        titulo.setBackground(new Color(20, 20, 70));
        add(titulo, BorderLayout.NORTH);

         //  Panel Central: Héroes vs Monstruos
        JPanel panelCentral = new JPanel(new GridLayout(2, 4, 15, 15));
        panelCentral.setBackground(new Color(30, 30, 50));

         panelHeroes = crearPanelPersonajes(heroes, "Héroes");
         panelMonstruos = crearPanelPersonajes(monstruos, "Monstruos");

          panelCentral.add(panelHeroes);//Se añaden tanto los heroes como los monstruos al panel central
          panelCentral.add(panelMonstruos);
        add(panelCentral, BorderLayout.CENTER);

        //nuevo panel a la izq para los objetos ojito :P
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setBackground(new Color(25,25,25));
        panelIzquierdo.setPreferredSize(new Dimension(200, 0));
        //Titulo del panel de los objetos
        JLabel tituloObjetos = new JLabel("Objetos ", SwingConstants.CENTER);
        tituloObjetos.setFont(new Font ("SansSerif", Font.BOLD, 16));
        tituloObjetos.setForeground(Color.YELLOW);
        tituloObjetos.setOpaque(true);
        tituloObjetos.setBackground(new Color(40,40,40));
        panelIzquierdo.add(tituloObjetos, BorderLayout.NORTH);
        //panel con los botones de los objetos
        panelObjetos = new JPanel(new GridLayout(6,1,5,5));
        panelObjetos.setBackground(new Color(30,30,30));
        panelObjetos.setBorder(BorderFactory.createEmptyBorder(10,5,10,5));
        //se crean botones para los 5 objetos
        String[] nombresObjetos = {
            "Hierba medicinal",
            "Antidoto",
            "Piedra del alma",
            "Semilla de habilidad",
            "Cola de lagarto"
        };

        botonesObjetos = new JButton[nombresObjetos.length];

        for(int i = 0; i < nombresObjetos.length; i++){
            botonesObjetos[i] = new JButton(nombresObjetos[i]);
            botonesObjetos[i].setFont(new Font("SansSerif", Font.PLAIN, 12));
            botonesObjetos[i].setBackground(new Color(70, 90, 70));
            botonesObjetos[i].setForeground(Color.WHITE);
            botonesObjetos[i].setFocusPainted(false);
            botonesObjetos[i].setEnabled(true);//inicialmente eran habilidades y asi

            final String objeto = nombresObjetos[i];
            botonesObjetos[i].addActionListener(e -> {
                controlador.activarModoObjetos(objeto);
                deshabilitarBotonesObjetos();
            });
            panelObjetos.add(botonesObjetos[i]);
        }
        //boton para cancelar el uso de objetos
         botonCancelarObjeto = new JButton("Cancelar");
        botonCancelarObjeto.setFont(new Font("SansSerif", Font.PLAIN, 12));
        botonCancelarObjeto.setBackground(new Color(90, 70, 70));
        botonCancelarObjeto.setForeground(Color.WHITE);
        botonCancelarObjeto.setFocusPainted(false);
        botonCancelarObjeto.setEnabled(false);
        botonCancelarObjeto.addActionListener(e -> {
            controlador.cancelarModoObjeto();
            habilitarBotonesObjetos();
        });

        panelObjetos.add(botonCancelarObjeto);
        
        panelIzquierdo.add(panelObjetos, BorderLayout.CENTER);
        
        // Botón para mostrar inventarios
        botonMostrarInventarios = new JButton("Ver Inventarios");
        botonMostrarInventarios.setFont(new Font("SansSerif", Font.PLAIN, 12));
        botonMostrarInventarios.setBackground(new Color(70, 70, 90));
        botonMostrarInventarios.setForeground(Color.WHITE);
        botonMostrarInventarios.setFocusPainted(false);
        botonMostrarInventarios.addActionListener(e -> {
            controlador.mostrarInventariosCompletos();
        });

        JPanel panelBotonInventario = new JPanel();
        panelBotonInventario.setBackground(new Color(25, 25, 25));
        panelBotonInventario.add(botonMostrarInventarios);
        panelIzquierdo.add(panelBotonInventario, BorderLayout.SOUTH);
        
        add(panelIzquierdo, BorderLayout.WEST);

         //  Panel Derecho para Mensajes y Botones 
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBackground(new Color(25, 25, 25));
        panelDerecho.setPreferredSize(new Dimension(300, 0));

        // Cuadro de texto de mensajes
         txtRegistro = new JTextArea(15, 25);
        txtRegistro.setEditable(false);
        txtRegistro.setLineWrap(true);
        txtRegistro.setWrapStyleWord(true);
        txtRegistro.setFont(new Font("Serif", Font.PLAIN, 16));
        txtRegistro.setBackground(new Color(40, 40, 40));
        txtRegistro.setForeground(Color.WHITE);
        JScrollPane scrollMensajes = new JScrollPane(txtRegistro);
        scrollMensajes.setBorder(BorderFactory.createTitledBorder("Mensajes de batalla"));
        panelDerecho.add(scrollMensajes, BorderLayout.CENTER);
        //Registra Correctamante todo lo que sucede en batalla
        controlador.accionRegistrarTextArea(txtRegistro);
        // Botones de acción debajo
        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 10, 10));
        panelBotones.setBackground(new Color(25, 25, 25));
      
        botonAtk = new JButton("Atacar");
        botonAtk.setFont(new Font("SansSerif", Font.BOLD, 16));
        botonAtk.setBackground(new Color(70, 70, 90));
        botonAtk.setForeground(Color.WHITE);
        botonAtk.setFocusPainted(false);
        panelBotones.add(botonAtk);

        botonDef = new JButton("Defender");
        botonDef.setFont(new Font("SansSerif", Font.BOLD, 16));
        botonDef.setBackground(new Color(70, 70, 90));
        botonDef.setForeground(Color.WHITE);
        botonDef.setFocusPainted(false);
        panelBotones.add(botonDef);

        botonSkill = new JButton("Habilidad");
        botonSkill.setFont(new Font("SansSerif", Font.BOLD, 16));
        botonSkill.setBackground(new Color(70, 70, 90));
        botonSkill.setForeground(Color.WHITE);
        botonSkill.setFocusPainted(false);
        panelBotones.add(botonSkill);

         botonVolverJugar = new JButton("Volver a Jugar");
        botonVolverJugar.setFont(new Font("SansSerif", Font.BOLD, 16));
        botonVolverJugar.setBackground(new Color(70, 70, 90));
        botonVolverJugar.setForeground(Color.WHITE);
        botonVolverJugar.setFocusPainted(false);
        panelBotones.add(botonVolverJugar);

        setBotonAcciones(false);//Para Habilitar o Deshabilitar Botones

        panelDerecho.add(panelBotones, BorderLayout.SOUTH);
        add(panelDerecho, BorderLayout.EAST);
        setVisible(true);

         configurarEventos();
         controlador.deactiveMonsterButton();//Los deshabilita inicialmente a los montruos
       // actualizarPantalla();
    }


    private JPanel crearPanelPersonajes(ArrayList<? extends Personaje> lista, String titulo) {
    JPanel panel = new JPanel(new GridLayout(1, 4, 15, 15));
    panel.setBorder(BorderFactory.createTitledBorder(titulo));
    panel.setBackground(new Color(30, 30, 50));

    for (Personaje p : lista) {
        
        JPanel panelPersonaje = new JPanel(new BorderLayout());
        // fondo segun personaje o monstruo
        if (p.getNombre().equals("SlimeBoy") || p.getNombre().equals("Dragon") ||
            p.getNombre().equals("Esqueleto") || p.getNombre().equals("Hechicero")) {
            panelPersonaje.setBackground(new Color(100, 40, 40));
        } else {
            panelPersonaje.setBackground(new Color(40, 60, 100));
        }

        // panel central donde va la imagen en el boton
        JPanel panelBoton = new JPanel(new GridBagLayout());
        panelBoton.setPreferredSize(new Dimension(100, 100));
        panelBoton.setOpaque(false);

        // crear el boton que contendra la imagen o el texto
        JButton boton = new JButton();
        boton.setPreferredSize(new Dimension(90, 90));
        
        // intentar cargar la imagen del personaje
        try {
            // normalizar el nombre del archivo, quitar espacios y acentos
            String nombreArchivo = p.getNombre()
                .replace(" ", "")
                .replace("é", "e");
            
            ImageIcon icono = new ImageIcon(getClass().getResource("/imagenes/" + nombreArchivo + ".png"));
            Image img = icono.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(img));
            boton.setText("");
            boton.setBackground(Color.WHITE);
            boton.setOpaque(true);
        } catch (Exception ex) {
            // si no encuentra la imagen, muestra el nombre como antes
            System.out.println("No se encontró imagen para: " + p.getNombre());
            boton.setText(p.getNombre());
            boton.setForeground(Color.WHITE);
            boton.setFont(new Font("SansSerif", Font.BOLD, 12));
            boton.setOpaque(false);
            boton.setContentAreaFilled(false);
        }
        
        boton.setBorderPainted(true);
        boton.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setHorizontalAlignment(SwingConstants.CENTER);
        boton.setVerticalAlignment(SwingConstants.CENTER);
        boton.putClientProperty("personaje", p);
        asignarEventoBotonPersonaje(boton, p);
        p.setBoton(boton);
        
        panelBoton.add(boton);

        // nombre de cada personaje
        JLabel nombreHeroe = new JLabel(p.getNombre(), SwingConstants.CENTER);
        nombreHeroe.setForeground(Color.WHITE);
        nombreHeroe.setFont(new Font("SansSerif", Font.BOLD, 12));
        
        // panel para las barras
        JPanel panelBarras = new JPanel();
        panelBarras.setLayout(new BoxLayout(panelBarras, BoxLayout.Y_AXIS));
        panelBarras.setOpaque(false);
        
        // barra hp
        JProgressBar barraHP = new JProgressBar(0, 100);
        barraHP.setValue(Math.min(p.getHP(), 100));
        barraHP.setString("HP: " + p.getHP());
        barraHP.setStringPainted(true);
        p.setBarraHP(barraHP);
        
        // barra mp
        JProgressBar barraMP = new JProgressBar(0, 100);
        barraMP.setValue(Math.min(p.getMP(), 100));
        barraMP.setString("MP: " + p.getMP());
        barraMP.setStringPainted(true);
        p.setBarraMP(barraMP);
        
        // barra estado
        JProgressBar barraEstado = new JProgressBar(0, 100);
        barraEstado.setString("Estado: " + p.getEstado());
        barraEstado.setStringPainted(true);
        p.setBarraEstado(barraEstado);
        
        // separacion pequeña entre barras
        panelBarras.add(barraHP);
        panelBarras.add(Box.createVerticalStrut(3)); 
        panelBarras.add(barraMP);
        panelBarras.add(Box.createVerticalStrut(3));
        panelBarras.add(barraEstado);
        
        // se agregan en el panel principal
        panelPersonaje.add(nombreHeroe, BorderLayout.NORTH);
        panelPersonaje.add(panelBoton, BorderLayout.CENTER);
        panelPersonaje.add(panelBarras, BorderLayout.SOUTH);
        
        panel.add(panelPersonaje);
    }
    return panel;
}
//metodos nuevos para los botones de los objetos y sisas eso
    private void deshabilitarBotonesObjetos() {
        for (JButton boton : botonesObjetos) {
            boton.setEnabled(false);
        }
        botonCancelarObjeto.setEnabled(true);
    }
    
    private void habilitarBotonesObjetos() {
        for (JButton boton : botonesObjetos) {
            boton.setEnabled(true);
        }
        botonCancelarObjeto.setEnabled(false);
    }

    //Getters y setters para habilitar y deshabilitar botones(se modifica para incluir objetos )
    public void setBotonAcciones(boolean b){
        enableAtacar(b);
        enableDefensa(b);
        enableHabilidad(b);
        enableVolverJugar(b);

     }
     public void enableAtacar(boolean b){ botonAtk.setEnabled(b); }
    public void enableDefensa(boolean b){ botonDef.setEnabled(b); }
    public void enableHabilidad(boolean b){ botonSkill.setEnabled(b); }
    public void enableVolverJugar(boolean b){ botonVolverJugar.setEnabled(b); }

    public JButton getEnableAtacar(boolean b){ return botonAtk; }
    public JButton getEnableDefensa(boolean b){ return botonDef; }
    public JButton getEnableHabilidad(boolean b){ return botonSkill; }
    public JButton getEnableVolverJugar(boolean b){ return botonVolverJugar; }

  private void asignarEventoBotonPersonaje(JButton boton, Personaje p){

     for (ActionListener remove : boton.getActionListeners()) {
        boton.removeActionListener(remove);
    }

        //Se Guarda Personaje dentro del evento
    boton.putClientProperty("personaje", p);

    //Creacion del evento
    boton.addActionListener(e -> {
        Personaje seleccionado = (Personaje) ((JButton) e.getSource())
                .getClientProperty("personaje");

        controlador.personajeSeleccionado(seleccionado);
        });

     }
        //Metodo donde se configuran los eventos de los botones principales de las acciones
    
     private void configurarEventos() {
        botonAtk.addActionListener(e -> ejecutarAccion("atacar"));
        botonDef.addActionListener(e -> ejecutarAccion("defender"));
        botonSkill.addActionListener(e -> ejecutarAccion("habilidad"));
        botonVolverJugar.addActionListener(e -> ejecutarAccion("Volver a Jugar"));
    }
   //Metodo donde se ejecutan los eventos de los botones principales 
    public void ejecutarAccion(String accion){// Ellos se comunican con metodos de controloador

        switch (accion) {
            case "atacar":
                    controlador.atacar();
                break;
            
             case "defender":
                    controlador.defender();
                break;

             case "habilidad":
                    controlador.habilidad();
                break;

             case "Volver a Jugar":
                    controlador.volverJugar();
                    txtRegistro.setText("");//Para Limpiar por completo el JTextArea
               break;

            default:
                break;
        }

    }


    public void actualizarPantalla(ArrayList<Heroe> listHeroes, ArrayList<Monstruo> listMonstruos) {

         // Aqui se Actualiza la barra de los heroes
    for (Heroe h : listHeroes) {

        if (h.getBarraHP() != null) {
            JProgressBar barraHP = h.getBarraHP();
            barraHP.setValue(Math.min(h.getHP(), 100));
            barraHP.setString("HP: " + h.getHP());
        }

        if (h.getBarraMP() != null) {
            JProgressBar barraMP = h.getBarraMP();
            barraMP.setValue(Math.min(h.getMP(), 100));
            barraMP.setString("MP: " + h.getMP());
        }

        if (h.getBarraEstado() != null) {
            JProgressBar barraEstado = h.getBarraEstado();
            barraEstado.setString("Estado: " + h.getEstado());
        }
    }

    // aqui se Actualiza la barra de los monstruos
    for (Monstruo m : listMonstruos) {

        if (m.getBarraHP() != null) {
            JProgressBar barraHP = m.getBarraHP();
            barraHP.setValue(Math.min(m.getHP(), 100));
            barraHP.setString("HP: " + m.getHP());
        }

        if (m.getBarraMP() != null) {
            JProgressBar barraMP = m.getBarraMP();
            barraMP.setValue(Math.min(m.getMP(), 100));
            barraMP.setString("MP: " + m.getMP());
        }

        if (m.getBarraEstado() != null) {
            JProgressBar barraEstado = m.getBarraEstado();
            barraEstado.setString("Estado: " + m.getEstado());
        }   

    }

    //  refrescar la interfaz para actualizarse correctamente
    revalidate();
    repaint();
       
 }

}

