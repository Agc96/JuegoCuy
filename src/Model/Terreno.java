package Model;

public class Terreno extends Dibujable {
    private int tipo;
    private boolean activo;
    /*0-> terreno neutral(AyB)
     *1-> terreno p1 (A)
     *2-> terreno p2 (B)
     *3-> terreno accion simple
     *4-> terreno accion duo
     *5-> terreno trigger de A
     *6-> terreno final usado como meta 
     *7-> terreno trigger de B
    */
    public Terreno(char elementoGrafico, int alto, int ancho, int tipo){
        super(elementoGrafico, alto, ancho);
        this.activo = true;
        this.tipo = tipo;
    }
    public Terreno(char elementoGrafico, int tipo){
        super(elementoGrafico);
        this.activo = true;
        this.tipo = tipo;
    }
    public void setTipo(int value){
        this.tipo = value;
    }
    public int getTipo(){
        return this.tipo;
    }
    public void setActivo(boolean value){
        this.activo = value;
    }
    public boolean getActivo(){
        return this.activo;
    }
}
