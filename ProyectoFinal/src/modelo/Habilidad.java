package modelo;

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

    public boolean usar(Personaje jugador, Personaje objetivo){
       
        if(jugador.getMP() <= 0){
             RegistroBatalla.RegistrarTextos(jugador.getNombre() + " no hay suficiente MP para tirar un ataque " + nombre);
            esVivo=true;
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

             default:
                break;
             }
        
        }
        gastoMp(jugador);//Resta Poder
         RegistroBatalla.RegistrarTextos(jugador.getNombre()+" Ha Quedado Con: "+ jugador.getMP()+" MP Disponible");
        return esVivo;//Me devuelve un true o false para saber si el heroe o mostruo esta vivo o muerto
    }
          //Este metodo Muestra el gasto de MP dependiendo de cada personaje 
     public void gastoMp(Personaje jugador){

        switch (jugador.getNombre()) {
            case "El Héroe":  //Gasta 3MP
                  jugador.setMP(jugador.getMP() - 3);
                break;

             case "Yangus":  //Gasta 5MP
                 jugador.setMP(jugador.getMP() - 5);
                break;

             case "Jessica":   //Gasta 4MP
                 jugador.setMP(jugador.getMP() - 4);
                break;
             case "Angelo":   //Gasta 5MP
                 jugador.setMP(jugador.getMP() - 5);
                break;

              case "SlimeBoy":  //Gasta 2MP
                  jugador.setMP(jugador.getMP() - 2);
                break;

             case "Dragon":  //Gasta 6MP
                 jugador.setMP(jugador.getMP() - 6);
                break;

             case "Esqueleto":   //Gasta 3MP
                 jugador.setMP(jugador.getMP() - 3);
                break;
             case "Hechicero":   //Gasta 5MP
                 jugador.setMP(jugador.getMP() - 5);
                break;  
        
            default:
                break;
        }
         if(jugador.getMP()<=0) jugador.setMP(0);
  }
    //Metodo que establece el daño magico de cada enemigo todo varia en su multiplicador de daño
public int dañosMp(Personaje jugador,Personaje objetivo,int random){
  int danoMp=0; 
        switch (jugador.getNombre()) {
            case "El Héroe":  
                  danoMp= (int)((jugador.getAtaque() * 1.2) - (objetivo.getDefensa() / 2) + random);
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
