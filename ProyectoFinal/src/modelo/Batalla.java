package modelo;

import java.util.ArrayList;

public class Batalla {

    private  boolean todosMonstruosDead=true,todosHeroesDead=true,todosPersonajesMurieron=true, endPartida=true;

    public boolean EmpezarBatalla(ArrayList <Heroe> listHeroes, ArrayList <Monstruo> listMonstruos,int posicionHero,int posicionMonster,String nombreBoton){//Determinara quienes se enfrentan primero por medio de saber su velocidad
       
        
      //Cuando terminar Partida sea False se acaba la partida 
              //el heroe tiene velocidad mayor arranca turno primero de lo contrario arranca el monstruo
                if(listHeroes.get(posicionHero).getVelocidad() > listMonstruos.get(posicionMonster).getVelocidad()){
                  RegistroBatalla.RegistrarTextos(listHeroes.get(posicionHero).getNombre() +" Es mas Rapido que "+  listMonstruos.get(posicionMonster).getNombre());
                   RegistroBatalla.RegistrarTextos(listHeroes.get(posicionHero).getNombre()+" Acciona Primero");
                  TurnoHeroe(listHeroes, listMonstruos, posicionHero, posicionMonster, nombreBoton);
                   TerminarBatalla(listHeroes, listMonstruos);
                   TurnoMounstro(listHeroes, listMonstruos, posicionHero, posicionMonster, nombreBoton);
                   TerminarBatalla(listHeroes, listMonstruos);
                }else if(listMonstruos.get(posicionMonster).getVelocidad() > listHeroes.get(posicionHero).getVelocidad()){
                    RegistroBatalla.RegistrarTextos(listMonstruos.get(posicionMonster).getNombre() +" Es mas Rapido que "+  listHeroes.get(posicionHero).getNombre());
                   RegistroBatalla.RegistrarTextos(listMonstruos.get(posicionMonster).getNombre()+" Acciona Primero");
                     TurnoMounstro(listHeroes, listMonstruos, posicionHero, posicionMonster, nombreBoton);
                    TerminarBatalla(listHeroes, listMonstruos);
                     TurnoHeroe(listHeroes, listMonstruos, posicionHero, posicionMonster, nombreBoton);
                     TerminarBatalla(listHeroes, listMonstruos);
                }



                    //Si todos los monstruos mueren ha ganado el bando de los heroes
              if(todosMonstruosDead==false){
                  
                   RegistroBatalla.RegistrarTextos(" ........ ");                
                    RegistroBatalla.RegistrarTextos("Ha Perdido el Equipo de los Mounstros que Amenazaban la region :)");                 
                     RegistroBatalla.RegistrarTextos("¡Han Ganado Los Valientes Aventureros! ¡La region Estara a Salvo!");
                     RegistroBatalla.RegistrarTextos(" ........ ");
                  
                  todosPersonajesMurieron=false;

                }else if(todosHeroesDead==false){//Si todos los Heroes mueren ha ganado el bando de los monstruos
            
                    RegistroBatalla.RegistrarTextos(" ........ ");
                    RegistroBatalla.RegistrarTextos("Ha Perdido El Equipo de los Valientes Aventureros :( )");
                    RegistroBatalla.RegistrarTextos("¡Han Ganado Los Monstruos! ¡La region Quedara Afectada!");
                     RegistroBatalla.RegistrarTextos(" ........ ");
                  todosPersonajesMurieron=false;
                }

             return todosPersonajesMurieron;//Devuelve si los monstruos murieron o heroes murieron
          }
           

    public void TerminarBatalla(ArrayList <Heroe> listHeroes, ArrayList <Monstruo> listMonstruos){//Terminara cuando todos esten muertos entonces se devolvera un true o false
          
       
        
          if(listHeroes.get(0).getEstado().equals("Muerto") && listHeroes.get(1).getEstado().equals("Muerto") && listHeroes.get(2).getEstado().equals("Muerto") && listHeroes.get(3).getEstado().equals("Muerto")){
                todosHeroesDead=false;
                
            }else if(listMonstruos.get(0).getEstado().equals("Muerto") && listMonstruos.get(1).getEstado().equals("Muerto") && listMonstruos.get(2).getEstado().equals("Muerto") && listMonstruos.get(3).getEstado().equals("Muerto")){
                todosMonstruosDead=false;
               
            }
            //Si alguno de los dos bandos pierde se acaba la partida
          if(todosHeroesDead == false || todosMonstruosDead== false){
            endPartida = false;
          }
    }

    public void TurnoHeroe(ArrayList <Heroe> listHeroes, ArrayList <Monstruo> listMonstruos,int posicionHero,int posicionMonster,String nombreBoton){//Programacion turno de los heroes
      
        if(listMonstruos.get(0).getEstado().equals("Muerto") && listMonstruos.get(1).getEstado().equals("Muerto") && listMonstruos.get(2).getEstado().equals("Muerto") && listMonstruos.get(3).getEstado().equals("Muerto")){
               TerminarBatalla(listHeroes, listMonstruos);
            }else if(listHeroes.get(posicionHero).getEstado().equals("Dormido")){
               //Cuando el heroe este dormido
               int probabilidad = (int) (Math.random()*10+1);//Genera Valor aleatorio del 1 al 10
               if(probabilidad >= 8){  //Probabilidad de despertarse 30%
                   RegistroBatalla.RegistrarTextos(listHeroes.get(posicionHero).getNombre() + " ¡Se ha Despertado!");
                    listHeroes.get(posicionHero).setEstado("Vivo");//Ya no esta dormido
                    listHeroes.get(posicionHero).acciones(listHeroes,listMonstruos,posicionHero,posicionMonster,nombreBoton);//Procede a atakar al monstruo
                  }else{//En caso de que no se cumpla la condicion
                   RegistroBatalla.RegistrarTextos(listHeroes.get(posicionHero).getNombre() + " Sigue  Durmiendo....");
               }

            }else{
                listHeroes.get(posicionHero).acciones(listHeroes,listMonstruos,posicionHero,posicionMonster,nombreBoton);
            }

    }

    public void TurnoMounstro(ArrayList <Heroe> listHeroes, ArrayList <Monstruo> listMonstruos,int posicionHero,int posicionMonster,String nombreBoton){//Programacion turno del Mounstruo

       if(listHeroes.get(0).getEstado().equals("Muerto") && listHeroes.get(1).getEstado().equals("Muerto") && listHeroes.get(2).getEstado().equals("Muerto") && listHeroes.get(3).getEstado().equals("Muerto")){
                TerminarBatalla(listHeroes, listMonstruos);
            }else if(listMonstruos.get(posicionMonster).getEstado().equals("Muerto")){
               
            }else{
                listMonstruos.get(posicionMonster).acciones(listHeroes,listMonstruos,posicionHero,posicionMonster,nombreBoton);
                 System.out.println(" ");//Salto de linea
            }  
            
    }
       //Metodo que finaliza ronda Asumiendo que los heroes no escojieron nada entonces los monstruos tomaron sus desiciones de combate
    public boolean FinalizarRonda(ArrayList <Heroe> listHeroes, ArrayList <Monstruo> listMonstruos,String nombreBoton){
          for(int i =0;i < listMonstruos.size();i++){
             
            if(listHeroes.get(0).getEstado().equals("Muerto") && listHeroes.get(1).getEstado().equals("Muerto") && listHeroes.get(2).getEstado().equals("Muerto") && listHeroes.get(3).getEstado().equals("Muerto")){
                break;////Se Rompe el ciclo ya que detecta en tiempo real cuando un bando perdio
            }else if(listMonstruos.get(i).getEstado().equals("Muerto")){
                continue;
            }else{
                listMonstruos.get(i).acciones(listHeroes,listMonstruos,0,i,nombreBoton);
                 System.out.println(" ");//Salto de linea
            }  
             
        }
        TerminarBatalla(listHeroes, listMonstruos);
        //Si todos los monstruos mueren ha ganado el bando de los heroes
              if(todosMonstruosDead==false){
                 
                   RegistroBatalla.RegistrarTextos(" ........ ");
                    RegistroBatalla.RegistrarTextos("Ha Perdido el Equipo de los Mounstros que Amenazaban la region :)");
                     RegistroBatalla.RegistrarTextos("¡Han Ganado Los Valientes Aventureros! ¡La region Estara a Salvo!");
                     RegistroBatalla.RegistrarTextos(" ........ ");

                }else if(todosHeroesDead==false){//Si todos los Heroes mueren ha ganado el bando de los monstruos
                    
                    RegistroBatalla.RegistrarTextos(" ........ ");
                    RegistroBatalla.RegistrarTextos("Ha Perdido El Equipo de los Valientes Aventureros :( )");
                    RegistroBatalla.RegistrarTextos("¡Han Ganado Los Monstruos! ¡La region Quedara Afectada!");
                     RegistroBatalla.RegistrarTextos(" ........ ");
                   todosPersonajesMurieron=false;
                }
                return todosPersonajesMurieron;
    }

    public int getPositionHero(ArrayList <Heroe> listHeroes,String characterNameHero){
      int position=0;

        for(int i = 0; i < listHeroes.size();i++){
              if(listHeroes.get(i).getNombre().equals(characterNameHero)){
                  position=i;//Position obtiene el valor de la posicion exacta del personaje seleccionado
                  break;//Se rompe Ciclo 
              }
        }
          return position;//Devuelve Posicion Exacta
    }

    public int getPositionMonster(ArrayList <Monstruo> listMonstruos,String characterNameMonster){
        int position=0;
         for(int i = 0; i < listMonstruos.size();i++){
              if(listMonstruos.get(i).getNombre().equals(characterNameMonster)){
                position=i;//Position obtiene el valor de la posicion exacta del personaje seleccionado
                 break;//Se rompe Ciclo
              }
        }

       return position;//Devuelve Posicion Exacta
    }

      //Metodo Creado con el objetivo de reiniciar los datos para jugar otras batallas
    public void ResetDatosBatalla(ArrayList <Heroe> listHeroes, ArrayList <Monstruo> listMonstruos){
            //Reestableciendo Valores de Heroes
        listHeroes.get(0).setHP(40);
        listHeroes.get(0).setMP(9);
        listHeroes.get(0).setEstado("Vivo");
        listHeroes.get(1).setHP(50);
        listHeroes.get(1).setMP(5);
        listHeroes.get(1).setEstado("Vivo");
        listHeroes.get(2).setHP(30);
        listHeroes.get(2).setMP(20);
        listHeroes.get(2).setEstado("Vivo");
        listHeroes.get(3).setHP(32);
        listHeroes.get(3).setMP(15);
        listHeroes.get(3).setEstado("Vivo");
         //Reestableciendo Valores de los monstruos
        listMonstruos.get(0).setHP(25);
        listMonstruos.get(0).setMP(8);
        listMonstruos.get(0).setEstado("Vivo");
        listMonstruos.get(1).setHP(120);
        listMonstruos.get(1).setMP(24);
        listMonstruos.get(1).setEstado("Vivo");
        listMonstruos.get(2).setHP(35);
        listMonstruos.get(2).setMP(9);
        listMonstruos.get(2).setEstado("Vivo");
        listMonstruos.get(3).setHP(32);
        listMonstruos.get(3).setMP(15);
        listMonstruos.get(3).setEstado("Vivo");

       todosPersonajesMurieron=true;
        todosMonstruosDead=true;
        todosHeroesDead=true;
        endPartida=true;
    }
    
}
