package Model;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import Controller.GestorXML;

/**
 *
 * @author Godievski
 */
public class Celda {
    private Dibujable obj;
    public Celda() {
        obj = null;
    }
    public Celda(char c, int posX, int posY) {
        obj = null;
        if (c != ' ') try {
            obj = GestorXML.ObtenerDibujable(c, posX, posY);
        } catch (IOException io) {
            System.err.println("ERROR: no se encuentra el archivo 'objetos.xml'.");
            System.exit(1);
        } catch (SAXException sax) {
            System.err.println("No se pudo cargar el archivo 'objetos.xml'.");
            System.exit(1);
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        }
        catch (NullPointerException ex) {
            System.err.println("Error al intentar cargar el archivo 'objetos.xml'.");
            System.out.println("Fila: " + posY);
            System.out.println("Columna: " + posX);
            System.exit(1);
        }
    }
    
    public Celda(Dibujable dib) {
        obj = dib;
    }
    public Dibujable getObj(){
        return obj;
    }
    public void setObj(Dibujable value){
        this.obj = value;
    }
}