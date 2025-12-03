package controlador;
/*Funciona casi igual que el app en el anterior mini proyecto, y lo hace asi ya que asi conecta las clases normales
 del modelo y la vista
 */
import java.util.ArrayList;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import modelo.*;
import util.MusicaFondo;
import vista.VentanaBatalla;

public class ControladorJuego {

    private ArrayList<Heroe> heroes;
    private ArrayList<Monstruo> monstruos;
    private MusicaFondo musica;
    private VentanaBatalla ventana;
    private Batalla batalla;
    private int heroPosition = -1;
    private int monsterPosition = -1;
    private int contadorSeleccion = 0;
    private boolean resultado;//Aqui se va a guardar el resultado acerca de que bando gano o perdio
    //nuevos atributos para hacer lo de los objetos
    private String objetoSeleccionado;
    private boolean modoUsoObjeto = false;

    public ControladorJuego() {
        heroes = new ArrayList<>();
        monstruos = new ArrayList<>();
        musica = new MusicaFondo();
        batalla = new Batalla();
        objetoSeleccionado = null;
        modoUsoObjeto = false;
    }
    //se hacen nuevos metodos para lo del manejo de los objetos en el juego
    public void activarModoObjetos(String nombreObjeto){
        this.objetoSeleccionado = nombreObjeto;
        this.modoUsoObjeto = true;
        RegistroBatalla.RegistrarTextos("se activa el modo de objetos: " + nombreObjeto);
        RegistroBatalla.RegistrarTextos("Selecciona un heroe para usar este objeto");
        //con esto se habilitan los botones unicamente para los heroes vivos
        for(Heroe h : heroes){
            if(h.getBoton() != null && h.getEstado().equals("Vivo")){
                h.getBoton().setEnabled(true);
            }
        }
        //para desabilitar botones de monstruos
        deactiveMonsterButton();
        //para que no aparezcan de una en la pantalla principal
        ventana.setBotonAcciones(false);
    }
    //este metodo para cancelar el uso de algun objeto
    public void cancelarModoObjeto(){
        this.objetoSeleccionado = null;
        this.modoUsoObjeto = false;
        ventana.setBotonAcciones(false);
        enableHeroButton();
        deactiveMonsterButton();
    }
    //usa un objeto sobre un heroe especifico
    public void usarObjetoEnHeroe(Heroe heroeObjetivo){
        if(!modoUsoObjeto || objetoSeleccionado == null || heroPosition == -1){
            RegistroBatalla.RegistrarTextos("no hay objeto seleccionado o se hizo de modo incorrecto");
            return;
        }

        Heroe heroeUsuario = heroes.get(heroPosition);
        //verifica si el heroe(usuario :p) tiene ese objeto
        if(heroeUsuario.getCantidadObjeto(objetoSeleccionado) <= 0){
            RegistroBatalla.RegistrarTextos(heroeUsuario.getNombre() + " no tiene " + objetoSeleccionado);
            cancelarModoObjeto();
            return;
        }

        boolean exito = heroeUsuario.usarObjeto(objetoSeleccionado, heroeObjetivo);

        if(exito){
            ventana.actualizarPantalla(heroes, monstruos);
        }
        cancelarModoObjeto();
        ReinicioVariables();
    }
    //para mostrar el inventario de un heroe especifico :D
    public void mostrarInventarioHeroe(int posicionHeroe){
        if(posicionHeroe >= 0 && posicionHeroe < heroes.size()){
            Heroe heroe = heroes.get(posicionHeroe);
            heroe.mostrarInventario();
        }
    }
    //muestra el inventario de todos los heroes
    public void mostrarInventariosCompletos(){
        RegistroBatalla.RegistrarTextos(" HE AQUI LOS INVENTARIOS COMPLETOS WAOS :O ");
        for(Heroe heroe : heroes){
            heroe.mostrarInventario();
        }
    }

    public void iniciarJuego() {
        musica.reproducirMusica("ProyectoFinal\\src\\DragonQuest.wav");
        crearPersonajes();
        crearVista();
    }

    public void crearPersonajes() {
        Heroe heroe1 = new Heroe("El Héroe", 40, 9, 22, 18, 12, TipoPersonajes.GUERRERO_BALANCEADO, "Vivo");
        Heroe heroe2 = new Heroe("Yangus", 50, 5, 28, 12, 8, TipoPersonajes.TANQUE, "Vivo");
        Heroe heroe3 = new Heroe("Jessica", 30, 20, 15, 9, 14, TipoPersonajes.MAGO_OFENSIVO, "Vivo");
        Heroe heroe4 = new Heroe("Angelo", 32, 15, 19, 14, 11, TipoPersonajes.SANADOR_APOYO, "Vivo");

        Monstruo monstruo1 = new Monstruo("SlimeBoy", 25, 8, 7, 3, 10, TipoPersonajes.ENEMIGO_DEBIL, "Vivo");
        Monstruo monstruo2 = new Monstruo("Dragon", 120, 24, 22, 12, 9, TipoPersonajes.JEFE_PODEROSO, "Vivo");
        Monstruo monstruo3 = new Monstruo("Esqueleto", 35, 9, 14, 8, 13, TipoPersonajes.LUCHADOR_EVIL, "Vivo");
        Monstruo monstruo4 = new Monstruo("Hechicero", 32, 15, 8, 6, 13, TipoPersonajes.MAGO_EVIL, "Vivo");

        heroe1.agregarHabilidad(new Habilidad("Golpe de espada", 3, 0, TipoHabilidad.DANIO_MAGICO));
        heroe2.agregarHabilidad(new Habilidad("Golpe de Hacha", 5, 0, TipoHabilidad.DANIO_MAGICO));
        heroe3.agregarHabilidad(new Habilidad("Bola de fuego", 4, 0, TipoHabilidad.DANIO_MAGICO));
        heroe4.agregarHabilidad(new Habilidad("Curación", 3, 12, TipoHabilidad.CURACION));

        monstruo1.agregarHabilidad(new Habilidad("Saltar Encima", 2, 0, TipoHabilidad.DANIO_MAGICO));
        monstruo2.agregarHabilidad(new Habilidad("Aliento de Fuego", 6, 0, TipoHabilidad.DANIO_MAGICO));
        monstruo3.agregarHabilidad(new Habilidad("Golpe de Espada", 3, 0, TipoHabilidad.DANIO_MAGICO));
        monstruo4.agregarHabilidad(new Habilidad("Rayo de Fuego", 5, 0, TipoHabilidad.DANIO_MAGICO));

        heroes.add(heroe1);
        heroes.add(heroe2);
        heroes.add(heroe3);
        heroes.add(heroe4);

        monstruos.add(monstruo1);
        monstruos.add(monstruo2);
        monstruos.add(monstruo3);
        monstruos.add(monstruo4);
        //temporar pa ver si funciona esta vuelta
        heroe1.mostrarInventario();
        heroe2.mostrarInventario();
    }


    private void crearVista() {
        ventana = new VentanaBatalla(heroes, monstruos, this);
        ventana.setVisible(true);
    }

    public void accionRegistrarTextArea(JTextArea txtRegistro){
         RegistroBatalla.setTextArea(txtRegistro);//Se añade JTextArea Ala clase para registrar las acciones en el JTextArea
    }
    //se modifica este metodo para que se pueda manejar los objetos y asi :P
    public void personajeSeleccionado(Personaje p) {

        System.out.println("Seleccionado: " + p.getNombre());

        if(modoUsoObjeto && p instanceof Heroe){
            usarObjetoEnHeroe((Heroe) p);

        }

    // Si es un héroe, instanceof permite saber si un objeto es perteneciente a alguna clase correspondiente a la que pertenezca su objeto especifico
    if (p instanceof Heroe) {
        if (heroPosition == -1) {
            heroPosition = batalla.getPositionHero(heroes, p.getNombre());
            contadorSeleccion++;
            deactiveHeroButton();    // desactiva selección de héroes mientras seleccionas monstruo
            enableMonsterButton();   // permite seleccionar monstruo
        } else {
            System.out.println("Hero already selected: " + heroPosition);
        }
    }
    // Si es un monstruo, instanceof permite saber si un objeto es perteneciente a alguna clase correspondiente a la que pertenezca su objeto especifico
    else if (p instanceof Monstruo) {
        if (monsterPosition == -1) {
            monsterPosition = batalla.getPositionMonster(monstruos, p.getNombre());
            System.out.println("monsterPosition set to " + monsterPosition);
            contadorSeleccion++;
        } else {
            System.out.println("Monster already selected: " + monsterPosition);
        }
    }

    System.out.println(contadorSeleccion + " Contando");

        if (contadorSeleccion >= 2) {
         habilitarBotonesPrincipales();
         }
         
   }
    
        //Metodo que Habilita Botones
    public void habilitarBotonesPrincipales(){
        //deactiveMonsterButton();//Deshabilita Opciones de Monstruo
        ventana.setBotonAcciones(true);
    }

    public void enableHeroButton(){
        for (Heroe h : heroes) {
            if (h.getBoton() != null) {
                h.getBoton().setEnabled(true);
            }
        
         }
    }

    public void enableMonsterButton(){
        for (Monstruo m : monstruos) {
            if (m.getBoton() != null) {
                m.getBoton().setEnabled(true);
            }
        }
    }

    public void deactiveHeroButton() {
    for (Heroe h : heroes) {
        if (h.getBoton() != null) {
            h.getBoton().setEnabled(false);
        }
    }
  }

  public void deactiveMonsterButton() {
    for (Monstruo m : monstruos) {
        if (m.getBoton() != null) {
            m.getBoton().setEnabled(false);
        }
    }
  }

    // aqui van todos los metodos que puede usar la vista y pues al rato se va acomodando bien a como lo teniamos antes
    //esto fue solo una prueba para ver si funcionaba o no
    public void atacar() {

        // Deshabilitar botones de interfaz mientras se ejecuta la batalla
         ventana.setBotonAcciones(false);
         
            //SwingWorker es una clase de java Swing que me permite ejecutar tareas pesadas con el objetivo de evitar que la interfaz se congele
            //Basicamente funciona como otro hilo pero en este caso para ejecutar la parte full de la logica del juego
         SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
         @Override
        protected Boolean doInBackground() throws Exception {
            // Se ejecuta la lógica del juego fuera del Event Dispatch Thread de swing para Ejecutar logica pesada en este otro hilo
            return batalla.EmpezarBatalla(heroes, monstruos, heroPosition, monsterPosition, "atacar");
        }

        @Override
        protected void done() {
            try { //Try Catch para detectar si el hilo principal de swing muere o sucede algo raro como congelamiento
                 resultado = get(); // devuelve lo que EmpezarBatalla retorne
                ventana.actualizarPantalla(heroes, monstruos);
             } catch (Exception ex) {
                ex.printStackTrace();
             } finally {
                ReinicioVariables();
                FinBatalla();
            }
          }
       };
           worker.execute();
     }

    public void defender() {

    // Deshabilitar botones de interfaz mientras se ejecuta la batalla
         ventana.setBotonAcciones(false);
         SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
             @Override
             protected Boolean doInBackground() throws Exception {
                  // Se ejecuta la lógica del juego fuera del Event Dispatch Thread de swing para Ejecutar logica pesada en este otro hilo 
                 return batalla.EmpezarBatalla(heroes, monstruos, heroPosition, monsterPosition, "defender");
          }

             @Override
            protected void done() {
                try {
                      resultado = get(); // devuelve lo que EmpezarBatalla retorne
                     ventana.actualizarPantalla(heroes, monstruos);
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    ReinicioVariables();
                    FinBatalla();
                }
            }
         };
              worker.execute();
    }

    public void habilidad() {  
    // Deshabilitar botones de interfaz mientras se ejecuta la batalla
        ventana.setBotonAcciones(false);
        

         SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
             @Override
              protected Boolean doInBackground() throws Exception {
                  // Se ejecuta la lógica del juego fuera del Event Dispatch Thread de swing para Ejecutar logica pesada en este otro hilo
                  return batalla.EmpezarBatalla(heroes, monstruos, heroPosition, monsterPosition, "habilidad");
              }

                @Override
             protected void done() {
                  try {
                      resultado = get(); // devuelve lo que EmpezarBatalla retorne
                     ventana.actualizarPantalla(heroes, monstruos);
                 } catch (Exception ex) {
                        ex.printStackTrace();
                } finally {
                     ReinicioVariables();
                     FinBatalla();
                }
              }
           };
                 worker.execute();
    }

    public void volverJugar() {
        batalla.ResetDatosBatalla(heroes, monstruos);
        ventana.actualizarPantalla(heroes,monstruos);
        ReinicioVariables();
    }

    private void FinBatalla(){

        if(resultado==false){
            deactiveHeroButton();//Desactiva heroes tambien
            ventana.enableVolverJugar(true);//Se activa boton de volver a jugar cuando algun bando haya perdido
        }
    }

    private void ReinicioVariables(){
        heroPosition=-1;//Reinicio de posiciones
        monsterPosition=-1;
        contadorSeleccion=0;
         ventana.setBotonAcciones(false); 
         enableHeroButton();//Activa botones de heroes
         deactiveMonsterButton();//Desactiva botones de Monstruos
    }

    public void detenerMusica() {
        musica.detenerMusica();
    }
}


/* System.out.println(heroPosition + " - "+ monsterPosition);//Para ver Comportamiento de las posiciones
        FinalizarPartida = batalla.EmpezarBatalla(heroes, monstruos, heroPosition, monsterPosition, "atacar");
        ventana.actualizarPantalla(heroes,monstruos);
        ReinicioVariables();
        FinBatalla();//Determina la ejecucion a hacer si algun bando pierde */


        /* System.out.println("Seleccionado: " + p.getNombre());
          contadorSeleccion++;//Se incrementa de 1 en 1
          deactiveHeroButton();//Desactiva Botones heroes
          enableMonsterButton();//Activa Botones de Monstruos
        
          if(heroPosition !=0){
            
         }else{
            heroPosition = batalla.getPositionHero(heroes,p.getNombre());
            
        }
               //Estos Condicionales son para que se conserve correctamente el valor de la posicion a escojer
         if(monsterPosition !=0){
           
         }else{
            monsterPosition = batalla.getPositionMonster(monstruos,p.getNombre());
             
        }
       System.out.println(contadorSeleccion+" Contando");
       if(contadorSeleccion==2){ //Ya que la variable arranca contando en 2 
           habilitarBotonesPrincipales();
       }

    } }*/