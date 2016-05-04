package Controller;

import Model.*;

public class GestorMapas {
    private Mapa[] mapas;
    static int numNiveles = 1;
    
    public GestorMapas() {
        mapas = new Mapa[numNiveles];
        for (int n = 0; n < numNiveles; n++)
            mapas[n] = new Mapa("./Files/Mapa" + n + ".txt");
    }
    
    public Mapa getMapa(int nivel){
        if (nivel >= 0 && nivel < numNiveles)
            return mapas[nivel];
        else
            return null;
    }
    public int getNumNiveles(){
        return this.numNiveles;
    }
    
    public Enemigo getEnemigo(int nivel) {
        for (int i = 0; i < 12; i++)
            for (int j = 0; j < 16; j++) {
                Dibujable obj = mapas[nivel].getMapaAt(i,j).getObj();
                if (obj instanceof Enemigo)
                    return (Enemigo)obj;
            }                
        return null;
    }
}
