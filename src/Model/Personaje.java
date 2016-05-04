

package Model;

/**
 *
 * @author Godievski
 */
public class Personaje extends Dibujable{
    private String nombre;
    private int posX;
    private int posY;
    private static int vida = 10;
    private String[] accionEspecial;
    static String[] accionDuo = new String[5];
    public static final int TERRENO = 0;
    public static final int ACCION_ESPECIAL = 1;
    public static final int ACCION_DUO = 2;
    public static final int TRIGGER_ENEMIGO = 3;
    public static final int META = 4;
    private int estado;
    
    public Personaje(char elementoGrafico){
        super(elementoGrafico);
        vida = 10;
        this.nombre = null;
        this.accionEspecial = new String[5];
        this.estado = TERRENO;
    }   
    public Personaje (int posX, int posY, char elementoGrafico) {
        super(elementoGrafico);
        vida = 10;
        this.posX = posX;
        this.posY = posY;
        this.nombre = null;
        this.accionEspecial = new String[5];
        this.estado = TERRENO;
    }
    public Personaje (int posX, int posY,char elementoGrafico,int alto, int ancho) {
        super(elementoGrafico,alto,ancho);
        vida = 10;
        this.posX = posX;
        this.posY = posY;
        this.nombre = null;
        this.accionEspecial = new String[5];
        this.estado = TERRENO;
    }
    
    public void setEstado(int tipoEstado){
        this.estado = tipoEstado;
    }
    public int getEstado(){
        return this.estado;
    }
    public void actualizarEstado(Mapa mapa){
        if (mapa != null){
            Celda celda = mapa.getMapaAt(this.posY, this.posX);
            Dibujable dib = celda.getObj();
            if (dib instanceof Terreno){
                Terreno ter = (Terreno) dib;
                int tipoTer = ter.getTipo();
                if (tipoTer == Terreno.TERRENO_ACCION){
                    this.estado = ACCION_ESPECIAL;
                } else if (tipoTer == Terreno.TERRENO_DUO){
                    this.estado = ACCION_DUO;
                } else if (tipoTer == Terreno.TERRENO_TRIGGER_P1 ||
                           tipoTer == Terreno.TERRENO_TRIGGER_P2){
                    this.estado = TRIGGER_ENEMIGO;
                } else if (tipoTer == Terreno.TERRENO_NEUTRAL ||
                           tipoTer == Terreno.TERRENO_P1 ||
                           tipoTer == Terreno.TERRENO_P2 ){
                    this.estado = TERRENO;
                } else if (tipoTer == Terreno.TERRENO_META){
                    this.estado = META;
                }
            }
        }
    }
    
    public void Mover(int x, int y){
        this.posX = x;
        this.posY = y;
    }
    
    public int getVida(){
        return vida;
    }    
    public void setVida(int value){
        vida = value;
    }
    
    public int getPosX() {
        return posX;
    }    
    public void setPosX(int posX) {
        this.posX = posX;
    }
    
    public int getPosY() {
        return posY;
    }    
    public void setPosY(int posY) {
        this.posY = posY;
    }
    
    public void setAccionEspecial(String accion, int nivel){
        this.accionEspecial[nivel] = accion;
    }
    public String getAccionEspecial(int nivel){
        return this.accionEspecial[nivel];
    }
    
    public void setAccionDuo(String accion, int nivel){
        accionDuo[nivel] = accion;
    }
    public String getAccionDuo(int nivel){
        return accionDuo[nivel];
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
