/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Godievski
 */
public class Animacion extends Thread{
    private Ventana ventana;
    private Animar anim1;
    private Animar anim2;
    
    public Animacion(Ventana ventana){
        this.ventana = ventana;
        anim1 = null;
        anim2 = null;
    }
    
    public void run(){
        while(true){
            try {
                if (anim1 != null){
                    if (!anim1.isRunning(1)){
                        anim1.join();
                        anim1 = null;
                    }
                }
                if (anim2 != null){
                    if (!anim2.isRunning(2)){
                        anim2.join();
                        anim2 = null;
                    }
                }
                sleep(5);
            } catch (InterruptedException ex) {
                Logger.getLogger(Animacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void iniciar(int p){
        if (p == 1){
            if (anim1 == null){
                anim1 = new Animar(ventana,ventana.juego.p1, 1);
                anim1.start();
            }
        }
        else if (p == 2){
            if (anim2 == null){
                anim2 = new Animar(ventana,ventana.juego.p2, 2);
                anim2.start();
            }
        }
    }
    public boolean isRunning(int p){
        if (p == 1)
            return anim1 != null;
        else if (p == 2)
            return anim2 != null;
        return false;
    }
}
