package Controller;
import Model.*;
import java.util.List;

/**
 *
 * @author Godievski
 */
public class InterpreteComandos {
        
    public int interpretaAccionEspecial(char key, int index, Mapa mapa, Personaje p1, Personaje p2, int nivel){
        key = Character.toUpperCase(key);
        /*0-> SE FALLÓ*/
        /*1-> EXITO TOTAL, ACCION P1*/
        /*2-> EXITO TOTAL, ACCION P2*/
        /*3-> EXITO TOTAL, ACCION DUO*/
        /*4-> EXITO PARCIAL*/

        if (p1.getEstado() == Personaje.ACCION_ESPECIAL &&
            p1.getAccionEspecial(nivel).length() > 0 &&
            index < p1.getAccionEspecial(nivel).length() &&
            p1.getAccionEspecial(nivel).charAt(index) == key){
            /*PARA P1*/
            if (p1.getAccionEspecial(nivel).length() == (index + 1) ){
                Celda celda = mapa.getMapaAt( p1.getPosY(),p1.getPosX());
                //Desactiva terreno
                ((Terreno) celda.getObj()).setActivo(false);
                return 1;
            }
            return 4;
        } 
        else if (p2.getEstado() == Personaje.ACCION_ESPECIAL &&
                 p2.getAccionEspecial(nivel).length() > 0 &&
                 index < p2.getAccionEspecial(nivel).length() &&
                 p2.getAccionEspecial(nivel).charAt(index) == key){
            /*PARA P2*/
            if (p2.getAccionEspecial(nivel).length() == (index + 1) ){
                Celda celda = mapa.getMapaAt( p2.getPosY(),p2.getPosX());
                //Desactiva terreno
                ((Terreno) celda.getObj()).setActivo(false);
                return 2;
            }
            return 4;
        }
        else if (p1.getEstado() == Personaje.ACCION_DUO &&
                 p2.getEstado() == Personaje.ACCION_DUO && 
                 p2.getAccionDuo(nivel).length() > 0 &&
                 index < p2.getAccionDuo(nivel).length() &&
                 p2.getAccionDuo(nivel).charAt(index) == key) {
            /*Para p1 y  p2*/
            //DESACTIVA TERRENO DUO
            if (p2.getAccionDuo(nivel).length() == (index + 1) ){
                Celda celda1 = mapa.getMapaAt( p1.getPosY(),p1.getPosX());
                Celda celda2 = mapa.getMapaAt( p2.getPosY(),p2.getPosX());
                ((Terreno) celda1.getObj()).setActivo(false);
                ((Terreno) celda2.getObj()).setActivo(false);
                return 3;
            }            
            return 4;
        }else{
            return 0;
        }
        
    }
    
    public void interpretaMovimiento(char key, Personaje p1, Personaje p2,
            Mapa mapa, int nivel){
        int difX = 0;
        int difY = 0;
        int personaje = 0;
        
        char c = Character.toUpperCase(key);
        if (c == 'W'){
            difY = -1; personaje = 1;
        } else if (c == 'A'){
            difX = -1;  personaje = 1;
        } else if (c == 'S'){
            difY = 1;   personaje = 1;
        } else if (c == 'D'){
            difX = 1;   personaje = 1;
        }
        if (personaje == 1){
            //PARA QUE NO SE MUEVA CUANDO ESTA EN UN TRIGGER ACTIVO
            if (p1.getEstado() == Personaje.TRIGGER_ENEMIGO) return;
            //MOVER AL PERSONAJE
            int xFinal = p1.getPosX() + difX;
            int yFinal = p1.getPosY() + difY;
            if (xFinal >= 0 && xFinal < 16 && yFinal >= 0 && yFinal < 12){
                Celda celda = mapa.getMapaAt(yFinal, xFinal);
                if (celda != null && celda.getObj() instanceof Terreno){
                    Terreno terreno = (Terreno)celda.getObj();
                    int t = terreno.getTipo();
                    if (t != 2 && t >= 0 && t < 8){
                        //NEW
                        p1.setOldX(p1.getPosX());
                        p1.setOldY(p1.getPosY());
                        p1.Mover(xFinal, yFinal);
                        p1.actualizarEstado(mapa);
                    }
                    return;
                }
            }
            return;
        }
        
        if (c == 'I'){
            difY = -1; personaje = 2;
        } else if (c == 'J'){
            difX = -1;  personaje = 2;
        } else if (c == 'K'){
            difY = 1;   personaje = 2;
        } else if (c == 'L'){
            difX = 1;   personaje = 2;
        }
        if (personaje == 2){
            //PARA QUE NO SE MUEVA CUANDO ESTA EN UN TRIGGER ACTIVO
            if (p2.getEstado() == Personaje.TRIGGER_ENEMIGO) return;
            //MOVER AL PERSONAJE
            int xFinal = p2.getPosX() + difX;
            int yFinal = p2.getPosY() + difY;
            if (xFinal >= 0 && xFinal < 16 && yFinal >= 0 && yFinal < 12){
                Celda celda = mapa.getMapaAt(yFinal, xFinal);
                if (celda != null && celda.getObj() instanceof Terreno){
                    Terreno terreno = (Terreno)celda.getObj();
                    int t = terreno.getTipo();
                    if (t != 1 && t >= 0 && t < 8){
                        //NEW
                        p2.setOldX(p2.getPosX());
                        p2.setOldY(p2.getPosY());
                        p2.Mover(xFinal, yFinal);
                        p2.actualizarEstado(mapa);
                    }
                    return;
                } 
            }
        }
    }
}
