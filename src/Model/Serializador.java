/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 *
 * @author Anthony
 */

public class Serializador {
    public Serializador() {
        //No hace nada
    }
    
    private Element AnadirValor(Document doc, Element raiz, String nombre,
            String valor) {
        Element nodo = doc.createElement(nombre); 
        Text texto = doc.createTextNode(valor);
        nodo.appendChild(texto); //Agregamos el valor al nodo
        raiz.appendChild(nodo); //Agregamos el nodo a la raiz
        return nodo;
    }
    
    private Element CrearNodo(Document doc, Element raiz, String nombre) {
        Element nodo = doc.createElement(nombre); 
        raiz.appendChild(nodo); //Agregamos el nodo a la raiz
        return nodo;
    }
    
    private Document crearDocumento(String name) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation implementation = builder.getDOMImplementation();
        Document document = implementation.createDocument(null, name, null);
        document.setXmlVersion("1.0");
        return document;
    }
    
    public void GuardarPartida(String nomArch, int vida, int nivel, Personaje p1,
            Personaje p2) {
        try {
            Document doc = crearDocumento("Juego"); //Crea árbol XML
            //Obtenemos la referencia al nodo raíz del documento XML
            Element raiz = doc.getDocumentElement();
            //Añadimos los elementos principales a guardar del juego
            AnadirValor(doc, raiz, "vida", Integer.toString(vida));
            AnadirValor(doc, raiz, "nivel", Integer.toString(nivel));
            //Añadimos los datos del jugador 1
            Element nodoP1 = CrearNodo(doc, raiz, "Personaje");
            AnadirValor(doc, nodoP1, "nombre", p1.getNombre());
            AnadirValor(doc, nodoP1, "posX", Integer.toString(p1.getPosX()));
            AnadirValor(doc, nodoP1, "posY", Integer.toString(p1.getPosY()));
            //Añadimos los datos del jugador 2
            Element nodoP2 = CrearNodo(doc, raiz, "Personaje");
            AnadirValor(doc, nodoP2, "nombre", p2.getNombre());
            AnadirValor(doc, nodoP2, "posX", Integer.toString(p2.getPosX()));
            AnadirValor(doc, nodoP2, "posY", Integer.toString(p2.getPosY()));
            //Añadimos los datos del mapa
            Element nodoMapa = CrearNodo(doc, raiz, "Mapa");
            AnadirValor(doc, nodoMapa, "accion1", Boolean.toString(true));
            AnadirValor(doc, nodoMapa, "accion2", Boolean.toString(false));
            AnadirValor(doc, nodoMapa, "accionDuo", Boolean.toString(true));
            //Indicamos donde lo queremos almacenar
            File arch = new File(nomArch); //nombre del archivo
            Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.transform(new DOMSource(doc), new StreamResult(arch));
        } catch (ParserConfigurationException | TransformerException ex) {
            Logger.getLogger(Serializador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
