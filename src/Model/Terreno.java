package Model;

public class Terreno extends Dibujable {
    private int tipo;
    private boolean activo;
    
    public static final int TERRENO_NEUTRAL = 0;
    public static final int TERRENO_P1 = 1;
    public static final int TERRENO_P2 = 2;
    public static final int TERRENO_ACCION = 3;
    public static final int TERRENO_DUO = 4;
    public static final int TERRENO_TRIGGER_P1 = 5;
    public static final int TERRENO_META = 6;
    public static final int TERRENO_TRIGGER_P2 = 7;

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
