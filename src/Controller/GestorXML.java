/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Dibujable;
import Model.Enemigo;
import Model.Objeto;
import Model.Personaje;
import Model.Terreno;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class GestorXML {
    
    private static String ObtenerValor(Element elem, String tag) {
        NodeList lista = elem.getElementsByTagName(tag);
        Node nodo = lista.item(0);
        return nodo.getTextContent();
    }
    
    private static Element ObtenerNodo(Element elem, String tag, int index) {
        NodeList lista = elem.getElementsByTagName(tag);
        return (Element) lista.item(index);
    }
    
    private static Element ObtenerNodoPrincipal(String nomArch, String tag)
            throws IOException, ParserConfigurationException, SAXException {
        File arch = new File(nomArch);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(arch);
        doc.getDocumentElement().normalize();
        return (Element) doc.getElementsByTagName(tag).item(0);
    }
    
    private static Element ObtenerNodo(NodeList lista, String tagClase, String idClase) {
        Element objeto = null;        
        for (int i = 0; i < lista.getLength(); i++) {
            //Obtenemos el nodo de un objeto
            Element nodo = (Element) lista.item(i);
            //Obtenemos el elemento grafico del objeto
            String clase = ObtenerValor(nodo, tagClase);
            //Si es igual al que queremos, tenemos el objeto buscado
            if (idClase.equals(clase)) objeto = nodo;
        }
        return objeto;
    }
    
    public static void CargarPartida(Personaje p1, Personaje p2) throws IOException,
            SAXException, ParserConfigurationException {
        /* FALTA TERMINAR */
        Element nodoNivel = ObtenerNodoPrincipal("partida.xml", "Juego");
        int vida = Integer.parseInt(ObtenerValor(nodoNivel, "vida"));
        System.out.println(vida);
        int nivel = Integer.parseInt(ObtenerValor(nodoNivel, "nivel"));
        System.out.println(nivel);
        //Colocar datos del personaje 2
        Element nodoP2 = ObtenerNodo(nodoNivel, "Personaje", 0);
        p2.setPosX(Integer.parseInt(ObtenerValor(nodoP2, "posX")));
        Element mapa = ObtenerNodo(nodoNivel, "Mapa", 0);
    }
    
    public static Dibujable ObtenerDibujable(char c, int posX, int posY)
            throws IOException, SAXException, ParserConfigurationException,
            NullPointerException {
        Dibujable dibujable = null;
        Element nodoNivel = ObtenerNodoPrincipal("./Files/objetos.xml", "Objetos");
        NodeList lista = nodoNivel.getElementsByTagName("Objeto");
        Element objeto = ObtenerNodo(lista, "codigo", Character.toString(c));
        //Obtenemos los valores del nodo
        int alto = Integer.parseInt(ObtenerValor(objeto, "alto"));
        int ancho = Integer.parseInt(ObtenerValor(objeto, "ancho"));
        String clase = ObtenerValor(objeto, "clase");
        int tipo = Integer.parseInt(ObtenerValor(objeto, "tipo"));
        //Asignamos sengun el tipo de dibujable
        switch (clase) {
            case "Terreno":
                dibujable = new Terreno(c, alto, ancho, tipo);
                break;
            case "Objeto":
                dibujable = new Objeto(c, alto, ancho, tipo);
                break;
            case "Enemigo":
                //CORREGIR PARA QUE NO SEA NECESARIO ESTE PARCHE
                if (tipo == 1)
                    dibujable = new Enemigo(posX, posY, 'S', alto, ancho);
                else 
                    dibujable = new Enemigo(posX, posY, 'N', alto, ancho);
        }        
        //Colocar imagen en memoria y colocar referencia en la celda
        String ruta = ObtenerValor(objeto, "imagen");
        if (ruta != null) try {
            dibujable.setImagen(ruta);
        } catch (IOException ex) {
            System.err.println("ERROR: No se encuentra la imagen en " + ruta);
            System.exit(1);            
        }
        return dibujable;
    }
}