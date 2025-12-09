package modelo;

import java.util.ArrayList;

import modelo.ExcepcionesPersonalizadas.MpInsuficiente;
import modelo.ExcepcionesPersonalizadas.PersonajeMuerto;

public class Batalla {
    //Estos variables guardan los turnos de estos estado alterados
    private int turno=0,unaVez=1;
    private  boolean todosMonstruosDead=true,todosHeroesDead=true,todosPersonajesMurieron=true, endPartida=true;
    private ArrayList <Heroe> auxiliarHeroes;
    private ArrayList <Monstruo> auxiliarMonstruos;

    public boolean EmpezarBatalla(ArrayList <Heroe> listHeroes, ArrayList <Monstruo> listMonstruos,int posicionHero,int posicionMonster,String nombreBoton,int optionSkill)throws PersonajeMuerto,MpInsuficiente{//Determinara quienes se enfrentan primero por medio de saber su velocidad
       
        if(unaVez==1){//Guardar Valores Predeterminados de los Monstruos y Heroes
          
          auxiliarHeroes = new ArrayList<>();
          for (Heroe value : listHeroes) {//Se hace con el objetivo de guardar un clone u copia que apuntan a otro puntero no al mismo
              //Se inicializa con los valores de listHeroes 
             Heroe clone = new Heroe(
              value.getNombre(),value.getHP(),value.getMP(),value.getAtaque(),value.getDefensa(),
              value.getVelocidad(),value.getTipoPersonaje(),value.getEstado(),value.getTurno()
            );
            auxiliarHeroes.add(clone);//Se van añadiendo los valores predeterminados de cada heroe
          }

          auxiliarMonstruos = new ArrayList<>();
          for (Monstruo value : listMonstruos) {//Se hace con el objetivo de guardar un clone u copia que apuntan a otro puntero no al mismo
              //Se inicializa con los valores de listMonstruos 
             Monstruo clone = new Monstruo(
              value.getNombre(),value.getHP(),value.getMP(),value.getAtaque(),value.getDefensa(),
              value.getVelocidad(),value.getTipoPersonaje(),value.getEstado(),value.getTurno()
            );
            auxiliarMonstruos.add(clone);//Se van añadiendo los valores predeterminados de cada Monstruo
          }

          unaVez=0;
        }
       
        try {//Excepcion Personalizada Acerca si un personaje esta muerto
          if(listHeroes.get(posicionHero).getEstado().equals("Muerto")|| listMonstruos.get(posicionMonster).equals("Muerto")){
             throw new PersonajeMuerto();//Lanza Excepcion Personalizada Respecto a que esta muerto el personaje
          }else{
            //Cuando terminar Partida sea False se acaba la partida 
              //el heroe tiene velocidad mayor arranca turno primero de lo contrario arranca el monstruo
                if(listHeroes.get(posicionHero).getVelocidad() > listMonstruos.get(posicionMonster).getVelocidad()){
                  RegistroBatalla.RegistrarTextos(listHeroes.get(posicionHero).getNombre() +" Es mas Rapido que "+  listMonstruos.get(posicionMonster).getNombre());
                   RegistroBatalla.RegistrarTextos(listHeroes.get(posicionHero).getNombre()+" Acciona Primero");
                  TurnoHeroe(listHeroes, listMonstruos, posicionHero, posicionMonster, nombreBoton,optionSkill);
                   TerminarBatalla(listHeroes, listMonstruos);
                   TurnoMounstro(listHeroes, listMonstruos, posicionHero, posicionMonster, nombreBoton, optionSkill);
                   TerminarBatalla(listHeroes, listMonstruos);
                }else if(listMonstruos.get(posicionMonster).getVelocidad() > listHeroes.get(posicionHero).getVelocidad()){
                    RegistroBatalla.RegistrarTextos(listMonstruos.get(posicionMonster).getNombre() +" Es mas Rapido que "+  listHeroes.get(posicionHero).getNombre());
                   RegistroBatalla.RegistrarTextos(listMonstruos.get(posicionMonster).getNombre()+" Acciona Primero");
                     TurnoMounstro(listHeroes, listMonstruos, posicionHero, posicionMonster, nombreBoton,optionSkill);
                    TerminarBatalla(listHeroes, listMonstruos);
                     TurnoHeroe(listHeroes, listMonstruos, posicionHero, posicionMonster, nombreBoton,optionSkill);
                     TerminarBatalla(listHeroes, listMonstruos);
                }
          }

        } catch (PersonajeMuerto e) {
             
              RegistroBatalla.RegistrarTextos("Error "+e.getMessage());
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

    public void TurnoHeroe(ArrayList <Heroe> listHeroes, ArrayList <Monstruo> listMonstruos,int posicionHero,int posicionMonster,String nombreBoton,int optionSkill)throws PersonajeMuerto,MpInsuficiente{//Programacion turno de los heroes
      
        if(listMonstruos.get(0).getEstado().equals("Muerto") && listMonstruos.get(1).getEstado().equals("Muerto") && listMonstruos.get(2).getEstado().equals("Muerto") && listMonstruos.get(3).getEstado().equals("Muerto")){
               TerminarBatalla(listHeroes, listMonstruos);
            }else if(listHeroes.get(posicionHero).getEstado().equals("Dormido")){
               //Cuando el heroe este dormido
               int probabilidad = (int) (Math.random()*10+1);//Genera Valor aleatorio del 1 al 10
               if(probabilidad >= 8){  //Probabilidad de despertarse 30%
                   RegistroBatalla.RegistrarTextos(listHeroes.get(posicionHero).getNombre() + " ¡Se ha Despertado!");
                    listHeroes.get(posicionHero).setEstado("Vivo");//Ya no esta dormido
                    listHeroes.get(posicionHero).acciones(listHeroes,listMonstruos,posicionHero,posicionMonster,nombreBoton,optionSkill);//Procede a atakar al monstruo
                  }else{//En caso de que no se cumpla la condicion
                   RegistroBatalla.RegistrarTextos(listHeroes.get(posicionHero).getNombre() + " Sigue  Durmiendo....");
               }

            }else if(listHeroes.get(posicionHero).getEstado().equals("Paralizado")){
                  //Pone valor de atake por defecto
                  listHeroes.get(posicionHero).setAtaque(auxiliarHeroes.get(posicionHero).getAtaque());
                  //Esta linea lo que hace es volverle a poner el valor de defensa que tenia por defecto
                  listHeroes.get(posicionHero).setDefensa(auxiliarHeroes.get(posicionHero).getDefensa());
                  turno=listHeroes.get(posicionHero).getTurno();//Obtiene valor de turno asignado

                   //Se gasta Turno del buff por cada Accion Realizada
                    //Le resta de 1 en 1
                    turno--;
                    listHeroes.get(posicionHero).setTurno(turno);
                if(listHeroes.get(posicionHero).getTurno()==0){
                  //Esta linea lo que hace es volverle a poner el valor de velocidad que tenia por defecto
                  listHeroes.get(posicionHero).setVelocidad(auxiliarHeroes.get(posicionHero).getVelocidad());
                  listHeroes.get(posicionHero).setEstado("Vivo");//Vuelve a su estado base
                }
               listHeroes.get(posicionHero).acciones(listHeroes,listMonstruos,posicionHero,posicionMonster,nombreBoton,optionSkill);

            }else if(listHeroes.get(posicionHero).getEstado().equals("Defensa")){
                    
                    if(listHeroes.get(posicionHero).getTurno()!=0){
                          turno = listHeroes.get(posicionHero).getTurno();
                          //Le resta de 1 en 1
                         turno--;
                         listHeroes.get(posicionHero).setTurno(turno);  
                          //Para determinar Cuando ya se terminaron los turnos de esa Habilidad
                          if(listHeroes.get(posicionHero).getTurno()==0){
                            //Estas dos lineas lo que hacen es volverle a poner el valor de atake y defensa que tenian por defecto
                           listHeroes.get(posicionHero).setAtaque(auxiliarHeroes.get(posicionHero).getAtaque());
                           listHeroes.get(posicionHero).setDefensa(auxiliarHeroes.get(posicionHero).getDefensa());
                            listHeroes.get(posicionHero).setVelocidad(auxiliarHeroes.get(posicionHero).getVelocidad());
                           listHeroes.get(posicionHero).setEstado("Vivo");//Vuelve a su estado base
                          }
                          listHeroes.get(posicionHero).acciones(listHeroes,listMonstruos,posicionHero,posicionMonster,nombreBoton,optionSkill);
                    }else{
                      listHeroes.get(posicionHero).acciones(listHeroes,listMonstruos,posicionHero,posicionMonster,nombreBoton,optionSkill);
                    }

            }else if(listHeroes.get(posicionHero).getEstado().equals("BuffAtake")){
                 //Esta linea lo que hace es volverle a poner el valor de Defensa que tenia por defecto
                  listHeroes.get(posicionHero).setDefensa(auxiliarHeroes.get(posicionHero).getDefensa());
                  turno=listHeroes.get(posicionHero).getTurno();//Obtiene valor de turno asignado
                  //Se gasta Turno del buff por cada Accion Realizada
                  //Le resta de 1 en 1
                  turno--;
                  listHeroes.get(posicionHero).setTurno(turno);  
                  //Para determinar Cuando ya se terminaron los turnos de esa Habilidad
                if(listHeroes.get(posicionHero).getTurno()==0){
                     //Esta linea lo que hace es volverle a poner el valor de atake que tenia por defecto
                  listHeroes.get(posicionHero).setAtaque(auxiliarHeroes.get(posicionHero).getAtaque());
                  listHeroes.get(posicionHero).setEstado("Vivo");//Vuelve a su estado base
                }

                listHeroes.get(posicionHero).acciones(listHeroes,listMonstruos,posicionHero,posicionMonster,nombreBoton,optionSkill);
           
           }else if(listHeroes.get(posicionHero).getEstado().equals("BuffDefensa")){
                listHeroes.get(posicionHero).setAtaque(auxiliarHeroes.get(posicionHero).getAtaque());//Vuelve a su atk predeterminado
                turno=listHeroes.get(posicionHero).getTurno();//Obtiene valor de turno asignado
                
                    //Le resta de 1 en 1
                    turno--;
                    listHeroes.get(posicionHero).setTurno(turno);
                  //Para determinar Cuando ya se terminaron los turnos de esa Habilidad
                if(listHeroes.get(posicionHero).getTurno()==0){
                     //Esta linea lo que hace es volverle a poner el valor de Defensa que tenia por defecto
                  listHeroes.get(posicionHero).setDefensa(auxiliarHeroes.get(posicionHero).getDefensa());
                  listHeroes.get(posicionHero).setEstado("Vivo");//Vuelve a su estado base
                }

                listHeroes.get(posicionHero).acciones(listHeroes,listMonstruos,posicionHero,posicionMonster,nombreBoton,optionSkill);
           

           } else{
                listHeroes.get(posicionHero).acciones(listHeroes,listMonstruos,posicionHero,posicionMonster,nombreBoton,optionSkill);
            }

    }

    public void TurnoMounstro(ArrayList <Heroe> listHeroes, ArrayList <Monstruo> listMonstruos,int posicionHero,int posicionMonster,String nombreBoton,int optionSkill)throws PersonajeMuerto,MpInsuficiente{//Programacion turno del Mounstruo

       if(listHeroes.get(0).getEstado().equals("Muerto") && listHeroes.get(1).getEstado().equals("Muerto") && listHeroes.get(2).getEstado().equals("Muerto") && listHeroes.get(3).getEstado().equals("Muerto")){
                TerminarBatalla(listHeroes, listMonstruos);
            }else if(listMonstruos.get(posicionMonster).getEstado().equals("Muerto")){
             
              
            }else if(listMonstruos.get(posicionMonster).getEstado().equals("Paralizado")){
                 
                  listMonstruos.get(posicionMonster).setAtaque(auxiliarMonstruos.get(posicionMonster).getAtaque());
                  //Esta linea lo que hace es volverle a poner el valor de defensa que tenia por defecto
                  listMonstruos.get(posicionMonster).setDefensa(auxiliarMonstruos.get(posicionMonster).getDefensa());
                  turno=listMonstruos.get(posicionMonster).getTurno();//Obtiene valor de turno asignado

                   //Se gasta Turno del buff por cada Accion Realizada
                    //Le resta de 1 en 1
                    turno--;
                    listMonstruos.get(posicionMonster).setTurno(turno);
                if(listMonstruos.get(posicionMonster).getTurno()==0){
                  //Esta linea lo que hace es volverle a poner el valor de velocidad que tenia por defecto
                  listMonstruos.get(posicionMonster).setVelocidad(auxiliarMonstruos.get(posicionMonster).getVelocidad());
                  listMonstruos.get(posicionMonster).setEstado("Vivo");//Vuelve a su estado base
                }
               listMonstruos.get(posicionMonster).acciones(listHeroes,listMonstruos,posicionHero,posicionMonster,nombreBoton,optionSkill);

            }else if(listMonstruos.get(posicionMonster).getEstado().equals("Defensa")){
                    
                    if(listMonstruos.get(posicionMonster).getTurno()!=0){
                          turno = listMonstruos.get(posicionMonster).getTurno();
                          //Le resta de 1 en 1
                         turno--;
                         listMonstruos.get(posicionMonster).setTurno(turno);  
                          //Para determinar Cuando ya se terminaron los turnos de esa Habilidad
                          if(listMonstruos.get(posicionMonster).getTurno()==0){
                            //Estas dos lineas lo que hacen es volverle a poner el valor de atake , defensa y velocidad que tenian por defecto
                           listMonstruos.get(posicionMonster).setAtaque(auxiliarMonstruos.get(posicionMonster).getAtaque());
                           listMonstruos.get(posicionMonster).setDefensa(auxiliarMonstruos.get(posicionMonster).getDefensa());
                             listMonstruos.get(posicionMonster).setVelocidad(auxiliarMonstruos.get(posicionMonster).getVelocidad());
                           listMonstruos.get(posicionMonster).setEstado("Vivo");//Vuelve a su estado base
                          }
                          listMonstruos.get(posicionMonster).acciones(listHeroes,listMonstruos,posicionHero,posicionMonster,nombreBoton,optionSkill);
                    }else{
                      listMonstruos.get(posicionMonster).acciones(listHeroes,listMonstruos,posicionHero,posicionMonster,nombreBoton,optionSkill);
                    }

            }else if(listMonstruos.get(posicionMonster).getEstado().equals("BuffAtake")){
                  //Esta linea lo que hace es volverle a poner el valor de defensa que tenia por defecto
                  listMonstruos.get(posicionMonster).setDefensa(auxiliarMonstruos.get(posicionMonster).getDefensa());
                  turno=listMonstruos.get(posicionMonster).getTurno();//Obtiene valor de turno asignado

                   //Se gasta Turno del buff por cada Accion Realizada
                    //Le resta de 1 en 1
                    turno--;
                    listMonstruos.get(posicionMonster).setTurno(turno);
                if(listMonstruos.get(posicionMonster).getTurno()==0){
                  //Esta linea lo que hace es volverle a poner el valor de atake que tenia por defecto
                  listMonstruos.get(posicionMonster).setAtaque(auxiliarMonstruos.get(posicionMonster).getAtaque());
                  listMonstruos.get(posicionMonster).setEstado("Vivo");//Vuelve a su estado base
                }
               listMonstruos.get(posicionMonster).acciones(listHeroes,listMonstruos,posicionHero,posicionMonster,nombreBoton,optionSkill);
            
            }else if(listMonstruos.get(posicionMonster).getEstado().equals("BuffDefensa")){
                    //Vuelve a valor por defecto del atake
                listMonstruos.get(posicionMonster).setAtaque(auxiliarMonstruos.get(posicionMonster).getAtaque());
                turno=listMonstruos.get(posicionMonster).getTurno();//Obtiene valor de turno asignado

                   //Se gasta Turno del buff por cada Accion Realizada
                    //Le resta de 1 en 1
                    turno--;
                    listMonstruos.get(posicionMonster).setTurno(turno);
                if(listMonstruos.get(posicionMonster).getTurno()==0){
                  //Esta linea lo que hace es volverle a poner el valor de defensa que tenia por defecto
                  listMonstruos.get(posicionMonster).setDefensa(auxiliarMonstruos.get(posicionMonster).getDefensa());
                  listMonstruos.get(posicionMonster).setEstado("Vivo");//Vuelve a su estado base
                }
               listMonstruos.get(posicionMonster).acciones(listHeroes,listMonstruos,posicionHero,posicionMonster,nombreBoton,optionSkill);

            }else{
                listMonstruos.get(posicionMonster).acciones(listHeroes,listMonstruos,posicionHero,posicionMonster,nombreBoton,optionSkill);
                 System.out.println(" ");//Salto de linea
            }  
            
    }
       //Metodo que finaliza ronda Asumiendo que los heroes no escojieron nada entonces los monstruos tomaron sus desiciones de combate
    public boolean FinalizarRonda(ArrayList <Heroe> listHeroes, ArrayList <Monstruo> listMonstruos,String nombreBoton,int optionSkill)throws PersonajeMuerto,MpInsuficiente{
          for(int i =0;i < listMonstruos.size();i++){
             
            if(listHeroes.get(0).getEstado().equals("Muerto") && listHeroes.get(1).getEstado().equals("Muerto") && listHeroes.get(2).getEstado().equals("Muerto") && listHeroes.get(3).getEstado().equals("Muerto")){
                break;////Se Rompe el ciclo ya que detecta en tiempo real cuando un bando perdio
            }else if(listMonstruos.get(i).getEstado().equals("Muerto")){
                continue;
            }else{
                listMonstruos.get(i).acciones(listHeroes,listMonstruos,0,i,nombreBoton,optionSkill);
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
