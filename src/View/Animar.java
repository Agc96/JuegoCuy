/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.GestorMapas;
import Model.Mapa;
import Model.Personaje;
import static View.Renderizador.MAX_SIZE;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author Godievski
 */
public class Animar extends Thread {
    private Ventana ventana;
    private Personaje personaje;
    private int incremento = 4;
    private static int intervalo = 100;
    public static boolean running1 = false;
    public static boolean running2 = false;
    public int p;
    public Animar(Ventana ventana, Personaje personaje, int p){
        this.ventana = ventana;
        this.personaje = personaje;
        this.p = p;
    }
    
    public void run(){
        if (p == 1){
            if (running1) return;
        }
        else if (p == 2){
            if (running2) return;
        }
        
        if (p == 1){
            running1 = true;
        }else if (p == 2){
            running2 = true;
        }
        
        JPanel pnlGrafico = ventana.pnlGrafico;
        Graphics g = pnlGrafico.getGraphics();
        Mapa mapa = ventana.juego.gestorMapa.getMapa(ventana.juego.nivel);
        int posXIni = personaje.getOldX();
        int posYIni = personaje.getOldY();
        int posXFin = personaje.getPosX();
        int posYFin = personaje.getPosY();
        boolean vertical;
        BufferedImage fondoIni = mapa.getMapaAt(posYIni,posXIni).getObj().getImagen();
        BufferedImage fondoFin = mapa.getMapaAt(posYFin,posXFin).getObj().getImagen();
        BufferedImage imgPer = personaje.getImagen();
        vertical = posXIni == posXFin;
        int inc = 0;
        if (vertical)
            incremento *= posYFin - posYIni;
        else
            incremento *= posXFin - posXIni;
        for (int i = 0; i < 16; i++){
            try {
                g.drawImage(fondoIni, posXIni*MAX_SIZE, posYIni*MAX_SIZE,MAX_SIZE,MAX_SIZE, null);
                g.drawImage(fondoFin, posXFin*MAX_SIZE, posYFin*MAX_SIZE,MAX_SIZE,MAX_SIZE, null);
                if (vertical)
                    g.drawImage(imgPer, posXIni*MAX_SIZE, posYIni*MAX_SIZE + inc, MAX_SIZE,MAX_SIZE,null);
                else 
                    g.drawImage(imgPer, posXIni*MAX_SIZE + inc, posYIni*MAX_SIZE, MAX_SIZE,MAX_SIZE,null);
                inc += incremento;
                sleep(intervalo);
            } catch (InterruptedException ex) {
                Logger.getLogger(Animar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (p == 1)
            running1 = false;
        else if (p == 2){
            running2 = false;
        }
        
    }
    public boolean isRunning(int p){
        if (p == 1){
            return running1;
        }
        if (p == 2){
            return running2;
        }
        return false;
    }
}
