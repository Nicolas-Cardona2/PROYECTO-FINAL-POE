package modelo;

public class ExcepcionesPersonalizadas {
    
     public static class PersonajeMuerto extends Exception {
        public PersonajeMuerto() {
            super("Un Personaje Muerto No puede Realizar Acciones");
        }
     }

      public static class MpInsuficiente extends Exception {
        public MpInsuficiente() {
            super(" No hay suficiente MP para Tirar un Ataque");
        }
     }
}
