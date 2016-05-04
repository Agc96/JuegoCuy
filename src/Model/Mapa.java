package Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Mapa {

    private Celda[][] mapa;
    public static int NUM_FILAS = 12;
    public static int NUM_COLUMNAS = 16;
    List listaTerrenoInactivo;
    
    public Mapa(String ruta) {
        try {
            mapa = new Celda[NUM_FILAS][NUM_COLUMNAS];
            BufferedReader br = new BufferedReader(new FileReader(ruta));
            String linea; int fila = 0;
            while ((linea = br.readLine()) != null) {
                char[] aux = linea.toCharArray();
                for (int col = 0; col < linea.length(); col++)
                    this.mapa[fila][col] = new Celda(aux[col], col, fila);
                fila++;
            }
            listaTerrenoInactivo = new ArrayList();
        } catch (FileNotFoundException ex) {
            System.err.println("ERROR: No se encontro el archivo " + ruta);
            System.exit(1);
        } catch (IOException ex) {
            //Se terminó de leer el mapa
        } 
    }
    
    public void setMapaAt(int fila, int columna, Dibujable obj) {
        if (fila < 0 || fila >= NUM_FILAS) return;
        if (columna < 0 || columna >= NUM_COLUMNAS) return;
        mapa[fila][columna].setObj(obj);
    }

    public Celda getMapaAt(int fila, int columna) {
        if (fila < 0 || fila >= NUM_FILAS) return null;
        if (columna < 0 || columna >= NUM_COLUMNAS) return null;
        return mapa[fila][columna];
    }

    public List getListaTerrenoInactivo() {
        return this.listaTerrenoInactivo;
    }

}