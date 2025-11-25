package modelo;

import java.util.ArrayList;
import java.util.List;
//import java.util.Scanner; // no creo que sea necesario pero lo dejo por futuras modificaciones posibles
 //trate de simplificar todo por eso se ve tan diferente de como estaba antes gracias
public class Monstruo extends Personaje {
    
    private int danoEvil = 0,defEvil=0;
    private boolean vivoHeroe=false;
    private List<Habilidad> habilidades = new ArrayList<>();

    public Monstruo(String nombre, int HP, int MP, int ataque, int defensa, int velocidad, TipoPersonajes person, String estado) {
        super(nombre, HP, MP, ataque, defensa, velocidad, person, estado);
    }

    public void agregarHabilidad(Habilidad habilidad) {
        habilidades.add(habilidad);
    }

    public void recibirAtaqueEvil(int danoEvil,ArrayList <Heroe> listHeroes,int posicion){
   
    if(listHeroes.get(posicion).getEstado().equals("Defensa")){
         // no se imprime las salidas del else por la cuestion que se imprimen las salidas del metodo defensa
    }else{
       RegistroBatalla.RegistrarTextos(listHeroes.get(posicion).getNombre() + "Tiene: "+ listHeroes.get(posicion).getHP()+ " de HP");
       listHeroes.get(posicion).setHP(listHeroes.get(posicion).getHP() - danoEvil);
       
       // evitar hp negativo
       if(listHeroes.get(posicion).getHP() < 0){
           listHeroes.get(posicion).setHP(0);
       }
       
       RegistroBatalla.RegistrarTextos(listHeroes.get(posicion).getNombre() + " Ahora Tiene: "+ listHeroes.get(posicion).getHP()+ " de HP"); 

       // si el hero tiene 0 o menos de vida estara muerto
      if(listHeroes.get(posicion).getHP() <= 0){
         listHeroes.get(posicion).setEstado("Muerto");
         RegistroBatalla.RegistrarTextos(listHeroes.get(posicion).getNombre()+" Ha Quedado de Baja");
        }  
     }    
}

    public void defensaHero(int defEvil,ArrayList <Heroe> listHeroes,int posicion){

     RegistroBatalla.RegistrarTextos(listHeroes.get(posicion).getNombre() + "Tiene: "+ listHeroes.get(posicion).getHP()+ " de HP");
     listHeroes.get(posicion).setHP(listHeroes.get(posicion).getHP() - defEvil);
     
     // evitar hp negativo
     if(listHeroes.get(posicion).getHP() < 0){
         listHeroes.get(posicion).setHP(0);
     }
     
     RegistroBatalla.RegistrarTextos(listHeroes.get(posicion).getNombre() + " Ahora Tiene: "+ listHeroes.get(posicion).getHP()+ " de HP");
     RegistroBatalla.RegistrarTextos(listHeroes.get(posicion).getNombre()+ " Perdio: "+ defEvil + " de HP");
     RegistroBatalla.RegistrarTextos("Se defendio "+ defEvil+ " De haber sido atakado por: "+ defEvil*2+" de Daño");
     listHeroes.get(posicion).setEstado("Vivo");
     
     // si el hero tiene 0 o menos de vida estara muerto
      if(listHeroes.get(posicion).getHP() <= 0){
         listHeroes.get(posicion).setEstado("Muerto");
         RegistroBatalla.RegistrarTextos(listHeroes.get(posicion).getNombre()+" Ha Quedado de Baja");
        }
}


    @Override
    public void acciones(ArrayList <Heroe> listHeroes, ArrayList <Monstruo> listMonstruos,int posicionHero,int posicionMonster,String nameBoton) {
         RegistroBatalla.RegistrarTextos("\nTurno de " + listMonstruos.get(posicionMonster).getNombre() + ". Elige una acción:"+" Tiene "+ listMonstruos.get(posicionMonster).getMP()+" de MP");
        int accion = (int)(Math.random() * 3); //son 3 porque se cuenta desde la posicion 0
         int heroRandom=0;
         int fin=1;
       while(fin !=0){//Ciclo que obliga ala CPU escojer Personaje Vivo
         heroRandom = (int)( Math.random() * listHeroes.size());
           if(listHeroes.get(heroRandom).getEstado().equals("Vivo")) fin=0;//Para Finalizar Ciclo
       }
        Heroe objetivo = listHeroes.get(heroRandom);

        switch (accion) {
            case 0:
                if (objetivo.getEstado().equals("Muerto")){

                         System.out.println("Lo siento pero este Heroe ya esta muerto ");
                         System.out.println("Vuelve a intentarlo...");
                         //acciones(listHeroes, listMonstruos, posicion);//Se vuelve a llamar al metodo
                }else{
                    RegistroBatalla.RegistrarTextos(listMonstruos.get(posicionMonster).getNombre() + " ataca a " + objetivo.getNombre());
                    int aleatorio = (int)(Math.random() * 3 + 1);//Los monstruos tiene un poquito de mas daño
                        danoEvil = (int)((getAtaque() - (objetivo.getDefensa() / 2)) + aleatorio);
                        if (danoEvil < 0) danoEvil = 0;

                    if(objetivo.getEstado().equals("Defensa")){
                        defEvil =(danoEvil/2); 
                        if (defEvil < 0) defEvil = 0;//Condicional para indicar que si el numero da negativo para evitar problemas va a valer 0
                        defensaHero(defEvil, listHeroes, heroRandom);
                        
                    }else if(objetivo.getEstado().equals("Dormido")){
                        objetivo.setEstado("Vivo");//Despierta al Heroe ya que el atake lo desperto
                        RegistroBatalla.RegistrarTextos(objetivo.getNombre()+ " Lo ha Despertado el atake de "+ listMonstruos.get(posicionMonster).getNombre());
                         recibirAtaqueEvil(danoEvil,listHeroes,heroRandom);//Recibe atake el heroe solo que despierta de su estado
                    }else{
                        // objetivo.recibirDanio(danoEvil);
                        recibirAtaqueEvil(danoEvil,listHeroes,heroRandom);
                    }

                    
                }
                break;

            case 1:
                listMonstruos.get(posicionMonster).setEstado("Defensa");
                RegistroBatalla.RegistrarTextos(listMonstruos.get(posicionMonster).getNombre() + " se pone en defensa.");
                break;

            case 2:
                if (habilidades.isEmpty()) {
                   // acciones(listHeroes, listMonstruos, posicion); 
                    return;
                }

                 if (objetivo.getEstado().equals("Muerto")){
                         System.out.println("Lo siento pero este Heroe ya esta muerto ");
                         System.out.println("Vuelve a intentarlo...");
                        // acciones(listHeroes, listMonstruos, posicion);//Se vuelve a llamar al metodo
                }else{
                        Habilidad habilidad = habilidades.get((int)(Math.random() * habilidades.size()));
                        RegistroBatalla.RegistrarTextos(listMonstruos.get(posicionMonster).getNombre() + " usa su habilidad: " + habilidad.getNombre());
                        vivoHeroe= habilidad.usar(this, objetivo);
                         //Si Devulve false el heroe murio de lo contrario no
                        if(vivoHeroe == false){
                            listHeroes.get(heroRandom).setEstado("Muerto");
                        }

                }

                break;
        }
    }
}

