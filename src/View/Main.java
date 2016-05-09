package View;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        /*CONFIGURACIÓN GRAFICOS*/
        GraphicsEnvironment graphEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphDevice = graphEnv.getDefaultScreenDevice();
        GraphicsConfiguration graphicConf = graphDevice.getDefaultConfiguration();
        
        Ventana my_game = new Ventana(graphicConf);
    }

}
