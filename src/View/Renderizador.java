package View;
import Controller.GestorMapas;
import Model.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
/**
 *
 * @author Godievski
 */
public class Renderizador {
    public static final int MAX_SIZE = 64;

    public void dibujarMapa(Graphics graphics, Mapa mapa) throws IOException{
        /*ELIMINAR LA LINEA DE ABAGO Y DESCOMENTAR LA OTRA*/
        BufferedImage empty = ImageIO.read(new File("./Resources/empty.png"));
        for (int f = 0; f < Mapa.NUM_FILAS; f++){
            for (int c = 0; c < Mapa.NUM_COLUMNAS; c++){
                Celda celda = mapa.getMapaAt(f, c);
                if (celda == null) continue;
                Dibujable dib = celda.getObj();
                if (dib == null) {
                    graphics.drawImage(empty, c*MAX_SIZE, f*MAX_SIZE, MAX_SIZE, MAX_SIZE, null);
                    continue;
                }
                //Obtenemos la imagen y la dibujamos
                BufferedImage img = dib.getImagen();
                graphics.drawImage(img, c*MAX_SIZE, f*MAX_SIZE, MAX_SIZE, MAX_SIZE, null);
            }
        }
    }
    
    public void dibujarJugadores(Graphics graphics,Personaje p1, Personaje p2) throws IOException{
        BufferedImage imgP1 = p1.getImagen();
        BufferedImage imgP2 = p2.getImagen();
        graphics.drawImage(imgP1, p1.getPosX()*MAX_SIZE, p1.getPosY()*MAX_SIZE,MAX_SIZE,MAX_SIZE,null);
        graphics.drawImage(imgP2, p2.getPosX()*MAX_SIZE, p2.getPosY()*MAX_SIZE,MAX_SIZE,MAX_SIZE,null);
    }
    
}
