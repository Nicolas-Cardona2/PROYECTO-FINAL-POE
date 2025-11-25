package modelo;

import java.util.*;

public class Heroe extends Personaje {
   
    private int danoHero=0,defHero=0;
    private boolean vivoMonstruo=false;
    List<Habilidad> habilidades = new ArrayList<>();

    public Heroe(String nombre, int HP, int MP,int ataque, int defensa, int velocidad,TipoPersonajes person, String estado) {
        super(nombre, HP, MP, ataque, defensa, velocidad,person, estado);
    }

    public void agregarHabilidad(Habilidad habilidad) {
        habilidades.add(habilidad);
    }

    public int calculoAtaqueHero(int ataque, int defensa,ArrayList <Monstruo> listMonstruos,int posicion){
    int atk= ataque;
    int def = defensa;
    int aleatorio=(int) (Math.random()*2+1) ;
    // calculo con la formula daño = (atk-def/2) + random(0-2)
    danoHero =( (atk - (def/2)) + aleatorio); 

    if(listMonstruos.get(posicion).getEstado().equals("Defensa")){
         // no se imprime las salidas del else por la cuestion que se imprimen las salidas del metodo defensa
    }else{
     
      RegistroBatalla.RegistrarTextos(listMonstruos.get(posicion).getNombre() + "Tiene: "+ listMonstruos.get(posicion).getHP()+ " de HP");
      listMonstruos.get(posicion).setHP(listMonstruos.get(posicion).getHP() - danoHero);
      
      // evitar hp negativo
      if(listMonstruos.get(posicion).getHP() < 0){
          listMonstruos.get(posicion).setHP(0);
      }
      
      RegistroBatalla.RegistrarTextos(listMonstruos.get(posicion).getNombre() + " Ahora Tiene: "+ listMonstruos.get(posicion).getHP()+ " de HP");
      
      // si el monstruo tiene 0 o menos de vida estara muerto
      if(listMonstruos.get(posicion).getHP() <= 0){
         listMonstruos.get(posicion).setEstado("Muerto");
         RegistroBatalla.RegistrarTextos(listMonstruos.get(posicion).getNombre()+" Ha Quedado de Baja");
        }
   
    }
   
     return danoHero;
}

    public int calculoDefensaMonstruo(int danoHero,ArrayList <Monstruo> listMonstruos,int posicion){
      this.danoHero = danoHero;
    // calculo con la formula Defensa = (Daño/2) + random(0-2)
    defHero =(this.danoHero/2); 
    // condicional para indicar que si el numero da negativo para evitar problemas va a valer 0
    if(defHero <0){
       defHero=0;
    }
     RegistroBatalla.RegistrarTextos(listMonstruos.get(posicion).getNombre() + "Tiene: "+ listMonstruos.get(posicion).getHP()+ " de HP");
     listMonstruos.get(posicion).setHP(listMonstruos.get(posicion).getHP() - defHero);
     
     // evitar hp negativo
     if(listMonstruos.get(posicion).getHP() < 0){
         listMonstruos.get(posicion).setHP(0);
     }
     
     RegistroBatalla.RegistrarTextos(listMonstruos.get(posicion).getNombre() + " Ahora Tiene: "+ listMonstruos.get(posicion).getHP()+ " de HP");
     listMonstruos.get(posicion).setEstado("Vivo");
     
     if(listMonstruos.get(posicion).getHP() <= 0){
         listMonstruos.get(posicion).setEstado("Muerto");
         RegistroBatalla.RegistrarTextos(listMonstruos.get(posicion).getNombre()+" Ha Quedado de Baja");
        }

     return defHero;
}



    @Override
    public void acciones(ArrayList <Heroe> listHeroes, ArrayList <Monstruo> listMonstruos,int posicionHero,int posicionMonster,String nameBoton) {
         
        RegistroBatalla.RegistrarTextos("\nTurno de " + listHeroes.get(posicionHero).getNombre() + ". Elige una acción:"+" Tienes "+ listHeroes.get(posicionHero).getMP()+" de MP");
        String opcion = nameBoton;

        switch (opcion) {
            case "atacar":
          
            int enemigo = posicionMonster;
          
                 if(listMonstruos.get(enemigo).getEstado().equals("Defensa")){
                                 danoHero = calculoAtaqueHero(listHeroes.get(posicionHero).getAtaque(),listMonstruos.get(enemigo).getDefensa(),listMonstruos,enemigo); 
                                 defHero = calculoDefensaMonstruo(danoHero,listMonstruos, enemigo);
                                 RegistroBatalla.RegistrarTextos(listMonstruos.get(enemigo).getNombre()+ " Perdio: "+ defHero + " de HP");
                                 RegistroBatalla.RegistrarTextos("Se defendio "+ defHero+ " De haber sido atakado por: "+ defHero*2+" de Daño");

                 }else{
                                 danoHero = calculoAtaqueHero(listHeroes.get(posicionHero).getAtaque(),listMonstruos.get(enemigo).getDefensa(),listMonstruos,enemigo);
                                 RegistroBatalla.RegistrarTextos(listMonstruos.get(enemigo).getNombre()+ " Perdio: "+ danoHero + " de HP");  


            }
            break;

        case "defender":
            listHeroes.get(posicionHero).setEstado("Defensa");
            RegistroBatalla.RegistrarTextos(listHeroes.get(posicionHero).getNombre() + " se prepara para defenderse.");
            break;

        case "habilidad":
            if (habilidades.isEmpty()) {
                System.out.println(getNombre() + " no tiene habilidades disponibles.");
                break;
            }

            int eleccion = 0;//Todos tienen una habilidad por ahora por lo tanto no tiene caso poner a escojer momentaneamente
            if (eleccion >= 0 && eleccion < habilidades.size()) {
                Habilidad habilidad = habilidades.get(eleccion);

                if (habilidad.getTipo() == TipoHabilidad.CURACION) {
                           //CURA Personaje con menos vida  
                    int objetivo = DeterminarPersonajeConMenosVida(listHeroes);
                     
                    if (objetivo >= 0 && objetivo < listHeroes.size()) {
                     habilidad.usar(this, listHeroes.get(objetivo));
                    } else {
                        System.out.println("Opción no válida.");
                     }
                } else {

                    int objetivo = posicionMonster;

                             if (objetivo >= 0 && objetivo < listMonstruos.size()) {
                                    vivoMonstruo= habilidad.usar(this, listMonstruos.get(objetivo));
                                   
                                    //Si Devuelve false el monstruo murio de lo contrario no
                                if(vivoMonstruo == false){
                                    listMonstruos.get(objetivo).setEstado("Muerto");
                                 }
                       
                            }else{
                                System.out.println("Opción no válida.");
                            }
                   
                }

            } else {
                System.out.println("Opción inválida. El turno se pierde.");
            }
            break;

        default:
            System.out.println("Opción inválida. El turno se pierde.");
            break;
                }
                    
    }

 private int DeterminarPersonajeConMenosVida(ArrayList <Heroe> listHeroes){
    //Esta lista a punta a una nueva referencia en memoria de la lista heroes esto con el objetivo de crear una lista copia independiente que no altere la lista original
    ArrayList<Heroe> heroesAuxiliar  = new ArrayList<>(listHeroes);//Lista auxiliar utilizada para el tema de la habilidad de sanacion
   
    for(int i = 0; i < heroesAuxiliar.size(); i++){
            if(heroesAuxiliar.get(i).getEstado().equals("Muerto")){
               heroesAuxiliar.remove(i);//Borra heroes muertos para no tenerlos en cuenta
            }
    }
   
    int menorHp=0;
        // Variables para hacer BubleShort
    int punt1 = 0, punt2 = 1;
    Heroe auxiliar; // objeto para guardar un elemento temporalmente

       //Doble Ciclo para organizar por metodo de la Burbuja
         for (int i = 0; i < heroesAuxiliar.size() - 1; i++) {
              punt1 = 0;//Apuntadores volviendo a su posicion inicial
                punt2 = 1;
            while (punt2 < heroesAuxiliar.size()) {
                if (heroesAuxiliar.get(punt1).getHP() < heroesAuxiliar.get(punt2).getHP()) {
                  // intercambiar posiciones del arrayList Auxiliar
                    auxiliar = heroesAuxiliar.get(punt1);
                    heroesAuxiliar.set(punt1, heroesAuxiliar.get(punt2));
                    heroesAuxiliar.set(punt2, auxiliar);
                }

            // Avanzar los apuntadores un paso
                punt1++;
                punt2++;
            }
        }

       for(int i =0 ; i < listHeroes.size();i++){  //Compara hasta encontrar el que nombre del heroe con menos HP y que este en estado Vivo
            if(listHeroes.get(i).getNombre().equals(heroesAuxiliar.get(heroesAuxiliar.size()-1).getNombre()) && listHeroes.get(i).getEstado().equals("Vivo")){
               menorHp=i;
               break;//Se termina el ciclo de golpe
            }
       }

       return menorHp;//Devuelve valor de la posicion especifica del heroe con menos vida
    }

}