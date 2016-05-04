/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author USUARIO
 */
public abstract class Dibujable {
    protected char elementoGrafico;
    protected BufferedImage imagen;
    protected int alto;
    protected int ancho;
    
    public Dibujable (char elementoGrafico){
        this.elementoGrafico = elementoGrafico;
        this.imagen = null;
        this.alto = 1;
        this.ancho = 1;
    }
    
    public Dibujable (char elementoGrafico, int alto, int ancho) {
        this.elementoGrafico = elementoGrafico;
        this.imagen = null;
        this.alto = alto;
        this.ancho = ancho;
    }
    
    public void setImagen(String value) throws IOException {
        this.imagen = ImageIO.read(new File(value));
    }
    public BufferedImage getImagen() {
        return this.imagen;
    }
    
    public void setElementoGrafico(char value){
        this.elementoGrafico = value;
    }
    public char getElementoGrafico(){
        return this.elementoGrafico;
    }
    
    public void setAlto(int value){
        this.alto = value;
    }
    public int getAlto() {
        return this.alto;
    }
    
    public void setAncho(int value){
        this.ancho = value;
    }
    public int getAncho(){
        return this.ancho;
    }
}
