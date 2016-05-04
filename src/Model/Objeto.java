/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Anthony
 */
public class Objeto extends Dibujable {
    //1 = obstáculo, 2 = objeto de ayuda
    private int tipo;
    
    public Objeto(char elementoGrafico,int tipo) {
        super(elementoGrafico);
        this.tipo = tipo;
    }
    public Objeto(char elementoGrafico, int alto, int ancho, int tipo) {
        super(elementoGrafico, alto, ancho);
        this.tipo = tipo;
    }
    public void setTipo(int value){
        this.tipo = value;
    }
    public int getTipo(){
        return this.tipo;
    }
}
