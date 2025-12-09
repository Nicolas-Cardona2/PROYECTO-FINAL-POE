package modelo;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class ManejoTxt{
    

    public void saveStateHero(ArrayList <Heroe> listHeroes){


        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("DatosHeroes.txt", true));
           
         //  for(int i =0; i < listHeroes.size();i++){
                bw.write(listHeroes.get(0).getNombre()+"\n");
                bw.flush();//Vacia Buffer escribe realmente en el archivo
           // }
           
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /*
    "-"+listHeroes.get(i).getHP()+"-"+listHeroes.get(i).getMP()+"-"+listHeroes.get(i).getAtaque()
                +"-"+listHeroes.get(i).getDefensa()+"-"+listHeroes.get(i).getVelocidad()+"-"+listHeroes.get(i).getTipoPersonaje()
                +"-"+listHeroes.get(i).getEstado()+"-"+listHeroes.get(i).getTurno()+"-"+"0"  +
    */
         

       /* try {
            String cadena="";
            FileReader br = new FileReader("bulironb.txt");
            int linea = br.read();//Me genera el numero del codigo ASCII del primer caracter en este caso
                //Devuelve un -1 indicando que cuando no hay mas caracteres se devuelve el -1 indicando que ya no hay mas caracteres por leer
            while (linea != -1) {
                cadena+=(char)linea;//Se añade cada linea del char a una cadena
               // System.out.print((char)linea);//Convierte cada valor a char
                linea = br.read();//Me sigue generando los codigos ASCII de los caracteres del archivo txt a medida que se va recorriendo
            }
               
                System.out.println(cadena);

            br.close();
            
        } catch (IOException e) {
            System.out.println("it hasn't read correctly that's text");
        }*/ 


}
