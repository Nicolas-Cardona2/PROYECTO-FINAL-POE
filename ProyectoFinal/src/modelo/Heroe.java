package modelo;

import java.util.*;

public class Heroe extends Personaje {
   
    private int danoHero = 0, defHero = 0;
    private boolean vivoMonstruo = false;
    List<Habilidad> habilidades = new ArrayList<>();
    //se agregan estos atributos para lo de los objetos de los personajes
    private Map<String, Integer> inventario;
    private Map<String, Objeto> catalogoObjetos;

    public Heroe(String nombre, int HP, int MP,int ataque, int defensa, int velocidad,TipoPersonajes person, String estado,int turnos) {
        super(nombre, HP, MP, ataque, defensa, velocidad,person, estado,turnos);
        this.inventario = new HashMap<>(); //esto de aqui es pa que empiece a funcionar el sistema de inventario
        this.catalogoObjetos = new HashMap<>();
        inicializarInventario();
    }

    public void inicializarInventario() {
        // Crear catálogo de objetos disponibles
        catalogoObjetos.put("Hierba medicinal", new Objeto("Hierba medicinal", "Cura 30 puntos de vida", TipoObjeto.CURATIVO, 30));
        catalogoObjetos.put("Antídoto", new Objeto("Antídoto", "Cura estados de envenenamiento y sueño", TipoObjeto.ANTIDOTO, 0));
        catalogoObjetos.put("Piedra de alma", new Objeto("Piedra de alma", "Revive a un compañero con 50% de vida", TipoObjeto.REVIVIR, getHP() / 2));
        catalogoObjetos.put("Semilla de habilidad", new Objeto("Semilla de habilidad", "Restaura 10 puntos de magia", TipoObjeto.RESTAURAR_MP, 10));
        catalogoObjetos.put("Cola de lagarto", new Objeto("Cola de lagarto", "Aumenta la defensa en 5 puntos por 3 turnos", TipoObjeto.MEJORAR_DEFENSA, 5, 3));
        
        // Dar cantidades iniciales de cada objeto (3 de cada uno)
        for (String nombreObjeto : catalogoObjetos.keySet()) {
            inventario.put(nombreObjeto, 3);
        }
    }
    //este para agregar un objeto al inventario Ojito :p
    public void agregarObjeto(String nombreObjeto, int cantidad) {
        inventario.put(nombreObjeto, inventario.getOrDefault(nombreObjeto, 0) + cantidad);
    }
    //para usar algun objeto del inventario
    public boolean usarObjeto(String nombreObjeto, Personaje objetivo) {
        if (!inventario.containsKey(nombreObjeto) || inventario.get(nombreObjeto) <= 0) {
            RegistroBatalla.RegistrarTextos(getNombre() + " no tiene " + nombreObjeto + " en el inventario.");
            return false;
        }
        
        if (!catalogoObjetos.containsKey(nombreObjeto)) {
            RegistroBatalla.RegistrarTextos("El objeto " + nombreObjeto + " no existe en el catálogo.");
            return false;
        }
        
        Objeto objeto = catalogoObjetos.get(nombreObjeto);
        boolean exito = objeto.usar(this, objetivo);
        
        if (exito) {
            // Reducir la cantidad en el inventario
            inventario.put(nombreObjeto, inventario.get(nombreObjeto) - 1);
            
            // Si se acaban, remover del inventario
            if (inventario.get(nombreObjeto) <= 0) {
                inventario.remove(nombreObjeto);
            }
        }
        
        return exito;
    }
    //para la cantidad disponibke de algun objeto
    public int getCantidadObjeto(String nombreObjeto) {
        return inventario.getOrDefault(nombreObjeto, 0);
    }
    //para obtener todo el inventario
    public Map<String, Integer> getInventario() {
        return new HashMap<>(inventario);
    }
    //muestra el inventario en el registro de la batalla
    public void mostrarInventario() {
        RegistroBatalla.RegistrarTextos(" Inventario de " + getNombre() + " ---");
        if (inventario.isEmpty()) {
            RegistroBatalla.RegistrarTextos("El inventario está vacío.");
        } else {
            for (Map.Entry<String, Integer> entry : inventario.entrySet()) {
                RegistroBatalla.RegistrarTextos("- " + entry.getKey() + ": " + entry.getValue());
            }
        }
        RegistroBatalla.RegistrarTextos("--------------------------------");
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
                //Se escoje que habilidad Utilizar
            int eleccion = 1;//Todos tienen una habilidad por ahora por lo tanto no tiene caso poner a escojer momentaneamente
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