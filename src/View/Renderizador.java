package View;
import Controller.GestorMapas;
import Model.*;
import static View.Renderizador.MAX_SIZE;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

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
    
    /* Muestra los datos (nombres, vida, nivel) */
    public void pnlTexto_mostrarDatos(Graphics graphics, Personaje p1, Personaje p2) throws IOException {
        String vida = "Vida : " + p1.getVida();
        String nombre1 = "Nombre Jugador 1: " + p1.getNombre();
        String nombre2 = "Nombre Jugador 2: " + p2.getNombre();

        String accion = p1.getAccionEspecial(0);

        graphics.drawString(vida, 15, 50);

//        graphics.drawString(accion, 15, 60);
    }

    public void pnlTexto_mostrarLista(Graphics graphics, List<String> lstString, int inicial, int incremento) {

        int x = 15, y = inicial;
        int linea_length = 0;
        String linea_acumulador = "";
        for (int i = 0; i < lstString.size(); ++i) {
            String[] lstPalabras = lstString.get(i).split(" ");
            linea_length = 0;
            linea_acumulador = "";
            for (int j = 0; j < lstPalabras.length; ++j) {
                linea_acumulador += (" " + lstPalabras[j]);
                linea_length += (1 + lstPalabras[j].length());

                boolean cond1 = linea_length >= 25;
                boolean cond2 = (j == lstPalabras.length - 1);
                if (cond1 || cond2) {
                    graphics.drawString(linea_acumulador, x, y);
                    linea_length = 0;
                    linea_acumulador = "";
                    y += incremento / 2;
                }
            }
            y += incremento;
        }
    }    
    
}



class JPanelGraficos extends JPanel {
    private Mapa mapa;
    private Ventana ventana;
    private BufferedImage imgP1;
    private BufferedImage imgP2;
    private int p1_posX;
    private int p1_posY;
    private int p2_posX;
    private int p2_posY;
    Object flagDibujo;

    public void paint(Graphics g) {
        super.paint(g);

        /*ELIMINAR LA LINEA DE ABAGO Y DESCOMENTAR LA OTRA*/
        BufferedImage empty = null;
        try {
            empty = ImageIO.read(new File("./Resources/empty.png"));
        } catch (IOException ex){
            System.out.println("Error al leer el archivo empty.png");
        }        
        
        for (int f = 0; f < Mapa.NUM_FILAS; ++f){
            for (int c = 0; c < Mapa.NUM_COLUMNAS; ++c){
                Celda celda = getMapa().getMapaAt(f, c);
                if (celda == null) continue;
                Dibujable dib = celda.getObj();
                if (dib == null) {
                    g.drawImage(empty, c*MAX_SIZE, f*MAX_SIZE, MAX_SIZE, MAX_SIZE, null);
                    continue;
                }
                //Obtenemos la imagen y la dibujamos
                BufferedImage img = dib.getImagen();
                g.drawImage(img, c*MAX_SIZE, f*MAX_SIZE, MAX_SIZE, MAX_SIZE, null);
            }
        }
        
        g.drawImage(getImgP1(), p1_posX*MAX_SIZE, p1_posY*MAX_SIZE,MAX_SIZE,MAX_SIZE,null);
        g.drawImage(getImgP2(), p2_posX*MAX_SIZE, p2_posY*MAX_SIZE,MAX_SIZE,MAX_SIZE,null);
        
    }

    public Mapa getMapa() {
        return mapa;
    }

    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
    }

    public Ventana getVentana() {
        return ventana;
    }

    public void setVentana(Ventana ventana) {
        this.ventana = ventana;
    }

    public BufferedImage getImgP1() {
        return imgP1;
    }

    public void setImgP1(BufferedImage imgP1) {
        this.imgP1 = imgP1;
    }

    public BufferedImage getImgP2() {
        return imgP2;
    }

    public void setImgP2(BufferedImage imgP2) {
        this.imgP2 = imgP2;
    }

    public int getP1_posX() {
        return p1_posX;
    }

    public void setP1_posX(int p1_posX) {
        this.p1_posX = p1_posX;
    }

    public int getP1_posY() {
        return p1_posY;
    }

    public void setP1_posY(int p1_posY) {
        this.p1_posY = p1_posY;
    }

    public int getP2_posX() {
        return p2_posX;
    }

    public void setP2_posX(int p2_posX) {
        this.p2_posX = p2_posX;
    }

    public int getP2_posY() {
        return p2_posY;
    }

    public void setP2_posY(int p2_posY) {
        this.p2_posY = p2_posY;
    }

}
