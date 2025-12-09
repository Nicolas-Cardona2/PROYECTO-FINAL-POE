package modelo;

import modelo.ExcepcionesPersonalizadas.MpInsuficiente;

public class Habilidad {
    private String nombre; // no es nombre de personaje sino de la habilidad jsjs
    private int costoMp;
    private int poder;
    private TipoHabilidad tipo;
    private boolean esVivo;

    public Habilidad(String nombre, int costoMp, int poder, TipoHabilidad tipo) {
        this.nombre = nombre;
        this.costoMp = costoMp;
        this.poder = poder;
        this.tipo = tipo;
    }
///todos get por razones de que se piden los datos que sea que cuesten tales poderes o habilidades y eso ;D
    public String getNombre() {
        return nombre;
    }

    public int getCostoMp() {
        return costoMp;
    }

    public int getPoder() {
        return poder;
    }

    public TipoHabilidad getTipo() {
        return tipo;
    }
                //Lanzo excepcion para cuando no hay suficiente MP
    public boolean usar(Personaje jugador, Personaje objetivo)throws MpInsuficiente{
        esVivo=true;
        try {  
        if(jugador.getMP() < costoMp){
           //  RegistroBatalla.RegistrarTextos(jugador.getNombre() + " no hay suficiente MP para tirar un ataque " + nombre);
             throw new MpInsuficiente();
        }else{

                int random = (int)(Math.random() * 5 + 1);

            switch (tipo) {
             case DANIO_MAGICO:

                int danio = dañosMp(jugador, objetivo, random);//El daño es proporcional al tipo de aventurero o enemigo
                  
                        if (danio <= 0) danio = 0;
                        RegistroBatalla.RegistrarTextos(jugador.getNombre() + " usa " + nombre + " de " + danio + " de daño!" + " contra " + objetivo.getNombre() );
                        if (objetivo.getEstado().equals("Defensa") ){
                         esVivo = objetivo.recibirDanio(danio,jugador,objetivo,true);//Se le devuelve valor de true o false
                        
                        }else if(objetivo.getEstado().equals("Dormido")){
                            objetivo.setEstado("Vivo");//Despierta al Heroe ya que el atake lo desperto
                            RegistroBatalla.RegistrarTextos(objetivo.getNombre()+ " Lo ha Despertado el atake de "+ jugador.getNombre() );
                             esVivo = objetivo.recibirDanio(danio,jugador,objetivo,false);//Se le devuelve valor de true o false
                        }else{

                            if(jugador.getNombre().equals("Hechicero") || jugador.getNombre().equals("Dragon"))  objetivo.setEstado("Dormido") ;//Durmio al heroe escojido
                            esVivo = objetivo.recibirDanio(danio,jugador,objetivo,false);//Se le devuelve valor de true o false
                        }
                
                
                break;

             case CURACION:

               if(objetivo.getEstado().equals("Muerto")){

                  System.out.println("Lo Siento No puedes Curar a un Heroe Muerto :( )");
               }else{
                    int cura = (int)((poder * 1.2) + random);//Cura y da punticos extra de vida como plus cuando sobre pasa el HP del personaje
                    objetivo.setHP(objetivo.getHP() + cura);//Se añade la curacion
                     RegistroBatalla.RegistrarTextos(jugador.getNombre() + " usa " + nombre + " para curar a " + objetivo.getNombre() + " por " + cura + " puntos de vida!");
                     RegistroBatalla.RegistrarTextos(objetivo.getNombre() + " Tiene Ahora " + objetivo.getHP() + " de vida!");
                }
                
                break;

            case BUFF_DE_ATAQUE:
                    int buffAtake=0;
                        jugador.setEstado("BuffAtake");
                        jugador.setTurno(2);//El estado dura dos Turnos A menos que halla un cambio de estado
                        buffAtake= jugador.getAtaque() + ((jugador.getAtaque()*50)/100);//Mejora el 50% del Atake
                        jugador.setAtaque(buffAtake);
                        RegistroBatalla.RegistrarTextos(jugador.getNombre() + " usa " + nombre + " Y mejora su Atake a: " + buffAtake);
                        esVivo=true;
                        break;

            case BUFF_DE_DEFENSA:
                        int buffDefensa=0;
                        jugador.setEstado("BuffDefensa");
                        jugador.setTurno(2);//El estado dura dos Turnos A menos que halla un cambio de estado
                        buffDefensa= jugador.getDefensa() + ((jugador.getDefensa()*50)/100);//Mejora el 50% De Defensa
                        jugador.setDefensa(buffDefensa);
                        RegistroBatalla.RegistrarTextos(jugador.getNombre() + " usa " + nombre + " Y mejora su Defensa a: " + buffDefensa);
                        esVivo=true;

                        break;

            case PARALYSIS:
                        int paralysis=0;
                        objetivo.setEstado("Paralizado");
                        objetivo.setTurno(2);//El estado dura dos Turnos A menos que halla un cambio de estado
                        paralysis= objetivo.getVelocidad()/2;//Se le reduce ala mitad la velocidad
                        objetivo.setVelocidad(paralysis);
                        RegistroBatalla.RegistrarTextos(jugador.getNombre() + " usa " + nombre + " Y a Dejado Paralizado a: " + objetivo.getNombre());
                        esVivo=true;
                        break;
             default:
                break;
             }
           jugador.setMP(jugador.getMP() - costoMp);//Resta Poder
        }
       
         RegistroBatalla.RegistrarTextos(jugador.getNombre()+" Ha Quedado Con: "+ jugador.getMP()+" MP Disponible");
       
    } catch (MpInsuficiente e) {
            RegistroBatalla.RegistrarTextos("Error: "+e.getMessage());
        }
         return esVivo;//Me devuelve un true o false para saber si el heroe o mostruo esta vivo o muerto
    
    }
          
    //Metodo que establece el daño magico de cada enemigo todo varia en su multiplicador de daño
public int dañosMp(Personaje jugador,Personaje objetivo,int random){
  int danoMp=0; 
        switch (jugador.getNombre()) {
            case "El Héroe":  //Condicional que permite determinar la habilidad especifica escojida para ejecutarla
                              if(nombre.equals("Golpe de espada")){
                                  danoMp= (int)((jugador.getAtaque() * 1.2) - (objetivo.getDefensa() / 2) + random);
                              }
                break;

             case "Yangus":  
                 danoMp= (int)((jugador.getAtaque() * 1.5) - (objetivo.getDefensa() / 2) + random);
                break;

             case "Jessica":   
                 danoMp= (int)((jugador.getAtaque() * 1.3) - (objetivo.getDefensa() / 2) + random);
                break;

              case "SlimeBoy":  
                  danoMp= (int)((jugador.getAtaque() * 1.0) - (objetivo.getDefensa() / 2) + random);
                break;

             case "Dragon":  
                 danoMp= (int)((jugador.getAtaque() * 2.0) - (objetivo.getDefensa() / 2) + random);
                break;

             case "Esqueleto":   
                 danoMp= (int)((jugador.getAtaque() * 1.2) - (objetivo.getDefensa() / 2) + random);
                break;
             case "Hechicero":   
                     //Aparte de lanzar el rayo de fuego puede dormir al heroe
                     danoMp= (int)((jugador.getAtaque() * 1.5) - (objetivo.getDefensa() / 2) + random);
                     RegistroBatalla.RegistrarTextos(jugador.getNombre() + " Duerme a " + objetivo.getNombre());
                 
                break;  
        
            default:
                break;
        }

         return danoMp;
  }




}
