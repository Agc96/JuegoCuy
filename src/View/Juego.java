package View;

import java.util.Scanner;
import java.io.IOException;
import Controller.*;
import Model.*;
import java.awt.Graphics;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Juego {

    public Renderizador rend;
    public InterpreteComandos lector;
    public GestorMapas gestorMapa;
    public Personaje p1;
    public Personaje p2;
    private final Scanner scan;
    public int nivel;
    public boolean inicio_Nivel;
    
    private Ventana ventana;
    
    public static final int MENU_JUEGO = 0;
    public static final int NOMBRE_PLAYER1 = 1;
    public static final int NOMBRE_PLAYER2 = 2;
    public static final int CAPTURAR_MOVIMIENTO = 3;
    public static final int CAPTURAR_ACCION_ESPECIAL = 4;
    public static final int CAPTURAR_ACCION_DUO = 5;
    public static final int NO_CAPTURAR = 9;

    public static int eventFlag = CAPTURAR_MOVIMIENTO; // INDICA EN QUE SECCION ESTOY
    
    public Juego(Ventana ventana) {
        rend = new Renderizador();
        lector = new InterpreteComandos();
        gestorMapa = new GestorMapas();
        scan = new Scanner(System.in);
        p1 = p2 = null;
        nivel = 0;
        inicio_Nivel = true;
        this.ventana = ventana;
        this.inicializarPersonajes(nivel);
        this.inicializarActividad(nivel);
        //ANHADIDO CARGAR OBJETO AYUDA
        this.cargar_Objetos_XML(1);
          
    }


    public void capturarAccion(char key) throws IOException, InterruptedException {
        Mapa m = this.gestorMapa.getMapa(nivel);
        lector.interpretaMovimiento(key, p1, p2, m, nivel);
    }

    public int caputarAccionEspecial(char key, int index) throws IOException, InterruptedException{
        /*0-> SE FALLÓ*/
        /*1-> EXITO TOTAL, ACCION P1*/
        /*2-> EXITO TOTAL, ACCION P2*/
        /*3-> EXITO TOTAL, ACCION DUO*/
        /*4-> EXITO PARCIAL*/
        int tipo;
        tipo = lector.interpretaAccionEspecial(key, index, gestorMapa.getMapa(nivel), p1, p2, nivel);
        if (tipo == 0){
            //RESTAR VIDA PERSONAJE
            //p1.setVida(p1.getVida() - 2);
        }else if (tipo == 1 || tipo == 2 || tipo == 3){
            ejecutarAccionEspecial(tipo);
            eventFlag = CAPTURAR_MOVIMIENTO;
        }
        return tipo;
    }
    
    private void ejecutarAccionEspecial(int player) throws IOException, InterruptedException{
        /*PLAYER INDICA QUE JUGADOR MANDÓ LA ACCION*/
        if (nivel == 0) {
            if (player == 1) {
                for (int i = 0; i < 3; i++) {
                    p1.setPosX(p1.getPosX() + 1);
                    this.renderizar();
                    //DELAY
                }
                //ACTIVAR TERRENOS
                List listaTerrenoInactivos = gestorMapa.getMapa(nivel).getListaTerrenoInactivo();
                for (int i = 0; i < listaTerrenoInactivos.size(); i++) {
                    Terreno terreno = (Terreno) listaTerrenoInactivos.get(i);
                    terreno.setActivo(true);
                }
            } else if (player == 2) {
                //NOTHING
            } else if (player == 3) {
                p1.setPosY(p1.getPosY() + 1);
                p2.setPosY(p2.getPosY() - 1);
                this.renderizar();
                //DELAY
                for (int i = 0; i < 2; i++) {
                    p1.setPosX(p1.getPosX() + 1);
                    p2.setPosX(p2.getPosX() + 1);
                    this.renderizar();
                    //DELAY
                }
            }
        } else if (nivel == 1) {
            if (player == 1) {
                int xOrig = p1.getPosX();
                int yOrig = p1.getPosY();
                for (int i = 0; i < 2; i++) {
                    p1.setPosY(p1.getPosY() + 1);
                    this.renderizar();
                    //DELAY
                }
                p1.setPosY(yOrig + 4);
                this.renderizar();
                //DELAY
                //AQUI DESTRUYE ESAS COSAS
                Celda celda1 = gestorMapa.getMapa(nivel).getMapaAt(yOrig + 4, xOrig);
                Celda celda2 = gestorMapa.getMapa(nivel).getMapaAt(yOrig + 5, xOrig);
                celda1.setObj(new Terreno('N', 2));
                celda2.setObj(new Terreno('N', 2));
                this.renderizar();
                //DELAY
                //VUELVE AL ORIGINAL
                p1.setPosX(xOrig);
                p1.setPosY(yOrig);
                //ACTIVAR TERRENOS
                List listaDuo = gestorMapa.getMapa(nivel).getListaTerrenoInactivo();
                for (int i = 0; i < listaDuo.size(); i++) {
                    Terreno terreno = (Terreno) listaDuo.get(i);
                    terreno.setActivo(true);
                }
            } else if (player == 2) {
                //RECORRE TERRITORIO
                int xOrig = p2.getPosX();
                int yOrig = p2.getPosY();
                p2.setPosX(xOrig - 1);
                this.renderizar();
                //DELAY
                /////
                p2.setPosX(xOrig - 2);
                //DESTRUYE LA ARENA
                Celda celda = gestorMapa.getMapa(nivel).getMapaAt(yOrig, xOrig - 1);
                celda.setObj(new Terreno('N', 2));
                this.renderizar();
                //DELAY
            } else if (player == 3) {
                //NOTHING
            }
        } else if (nivel == 2) {
            if (player == 1) {
                for (int i = 0; i < 3; i++) {
                    p1.setPosX(p1.getPosX() + 1);
                    this.renderizar();
                    //DELAY
                }
                //ACTIVAR TERRENOS
                List listaTerrenoInactivos = gestorMapa.getMapa(nivel).getListaTerrenoInactivo();
                for (int i = 0; i < listaTerrenoInactivos.size(); i++) {
                    Terreno terreno = (Terreno) listaTerrenoInactivos.get(i);
                    terreno.setActivo(true);
                }
            } else if (player == 2) {
                //NOTHING
            } else if (player == 3) {
                p1.setPosX(p1.getPosX() + 1);
                p2.setPosX(p2.getPosX() + 1);
                this.renderizar();
                //DELAY
                p1.setPosX(p1.getPosX() + 3);
                p1.setPosY(p1.getPosY() - 1);
                p2.setPosX(p2.getPosX() + 3);
                this.renderizar();
                //DELAY
                p1.setPosY(p1.getPosY() + 1);
                p2.setPosY(p2.getPosY() - 1);
                this.renderizar();
                //DELAY
            }
        } else if (nivel == 3) {//NIVEL CON ENEMIGO
            if (player == 1) {
                //NADA
            } else if (player == 2) {
                //ANIMACION
                int xOrig = p2.getPosX();
                int yOrig = p2.getPosY();
                //1
                p2.setPosY(yOrig - 1);
                p2.setPosX(xOrig - 1);
                this.renderizar();
                //DELAY
                //2
                p2.setPosY(yOrig - 3);
                this.renderizar();
                //DELAY
                //3
                p2.setPosY(yOrig - 4);
                this.renderizar();
                //DELAY
                //DESTRUYE ENEMIGO Y TRIGGERS
                Terreno t = new Terreno('S', 1);
                Mapa m = gestorMapa.getMapa(nivel);
                m.setMapaAt(4, 9, t);
                m.setMapaAt(3, 10, t);
                m.setMapaAt(4, 10, t);
                m.setMapaAt(5, 10, t);
                m.setMapaAt(6, 10, t);
                //4
                p2.setPosX(xOrig);
                p2.setPosY(yOrig);
                this.renderizar();
                //DELAY
            } else if (player == 3) {
                //NOTHING
            }
        }
    }

    public void actualizarInfo() throws IOException, InterruptedException {
        int posX1 = p1.getPosX();
        int posY1 = p1.getPosY();
        int posX2 = p2.getPosX();
        int posY2 = p2.getPosY();

        Mapa mapa = this.gestorMapa.getMapa(nivel);
        Dibujable obj1 = mapa.getMapaAt(posY1, posX1).getObj();
        Dibujable obj2 = mapa.getMapaAt(posY2, posX2).getObj();
        
        /*CAMBIA FLAGS*/
        if (obj1 instanceof Terreno)
            if (((Terreno) obj1).getTipo() == 3 && ((Terreno) obj1).getActivo())
                eventFlag = CAPTURAR_ACCION_ESPECIAL;
        if (obj2 instanceof Terreno)
            if (((Terreno) obj2).getTipo() == 3 && ((Terreno) obj2).getActivo()) 
                eventFlag = CAPTURAR_ACCION_ESPECIAL;
            else if (obj1 instanceof Terreno)
                if (((Terreno) obj1).getTipo() == 4
                        && ((Terreno) obj2).getTipo() == 4
                        && ((Terreno) obj1).getActivo()
                        && ((Terreno) obj2).getActivo()) {
                    eventFlag = CAPTURAR_ACCION_DUO;
                }     

        /*VERIFICA LLEGARON A LA META*/
        if (obj1 instanceof Terreno && obj2 instanceof Terreno) {
            if (((Terreno)  obj1).getTipo() == 6
                    && ((Terreno) obj2).getTipo() == 6) {
                renderizar();
                nivel += 1;
                inicio_Nivel = true;
                //System.out.print(". Presiona ENTER para continuar...");
                //COLOCAR TIMER O UN CAPTURADOR DE KEY
                inicializarPersonajes(nivel);
                inicializarActividad(nivel);
            }
        }
        
        /*VERIFICA SI EL PERSONAJE CAYÓ EN UN TRIGGER ENEMIGO*/
        if (obj1 instanceof Terreno || obj2 instanceof Terreno) {
            Terreno ter = (Terreno) obj1;
            if (ter.getActivo() && ter.getTipo() == 5) {
                //ACTIVE LA VISIBILIDAD DEL ENEMIGO Y QUE LO MUESTRE
                Enemigo e = this.gestorMapa.getEnemigo(nivel);
                e.setElementoGrafico('E');
                //DISMINUIR LA VIDA DEL JUGADOR 1
                this.p1.setVida(this.p1.getVida() - 1);
                //Activar el terreno de accion
                List l = this.gestorMapa.getMapa(nivel).getListaTerrenoInactivo();
                for (int i = 0; i < l.size(); i++) {
                    Terreno t = (Terreno) (l.get(i));
                    t.setActivo(true);
                }
            }
        }
    }

    public void renderizar() throws IOException, InterruptedException {
        Mapa mapa = this.gestorMapa.getMapa(nivel);
        Graphics graph = ventana.pnlGrafico.getGraphics();
        rend.dibujarMapa(graph,mapa);
        rend.dibujarJugadores(graph,p1,p2);
    }

    private boolean finJuego() {
        /*TOPE NIVEL: cantidad de mapas*/
        /*Si ha muerto o terminó todos los niveles*/
        return (p1.getVida() <= 0) || (nivel == gestorMapa.getNumNiveles());
    }
    
    private void inicializarPersonajes(int nivel) {
        /*AQUI SE PUEDE REALIZAR LECTURA DE PERSONAJE Y ENEMIGO*/
        /*SUS DATOS, ETC*/
        if (nivel < 0 || nivel >= gestorMapa.getNumNiveles()) return;
        if (this.p1 == null) this.p1 = new Personaje('A');
        if (this.p2 == null) this.p2 = new Personaje('B');
        if (this.p1.getVida() <= 0) this.p1.setVida(10);
        //Cargar datos de posición y acciones de los personajes
        Cargar_Personajes_XML(nivel);
        //Cargar imágenes de los personajes
        try {
            this.p1.setImagen("./Resources/sprite_cuy.png");
            this.p2.setImagen("./Resources/sprite_perro.png");
        } catch (IOException ex) {
            System.err.println("ERROR: No se pudieron cargar las imagenes de los personajes.");
            System.exit(1);
        }
    }

    private void Cargar_Personajes_XML(int nivel) {
        try {
            File inputFile = new File("./Files/personajes.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("Nivel");
            Node nNode = nList.item(nivel);
            Element eElement = (Element) nNode;
            
            p1.setPosY(Integer.parseInt(eElement.getElementsByTagName("p1PosY").item(0).getTextContent()));
            p1.setPosX(Integer.parseInt(eElement.getElementsByTagName("p1PosX").item(0).getTextContent()));
            p1.setAccionEspecial(eElement.getElementsByTagName("p1AccEsp").item(0).getTextContent(), nivel);
            
            p2.setPosY(Integer.parseInt(eElement.getElementsByTagName("p2PosY").item(0).getTextContent()));
            p2.setPosX(Integer.parseInt(eElement.getElementsByTagName("p2PosX").item(0).getTextContent()));
            p2.setAccionEspecial(eElement.getElementsByTagName("p2AccEsp").item(0).getTextContent(), nivel);
            
            p1.setAccionDuo(eElement.getElementsByTagName("AccDuo").item(0).getTextContent(), nivel);
            p2.setAccionDuo(eElement.getElementsByTagName("AccDuo").item(0).getTextContent(), nivel);
        } catch (FileNotFoundException ex) {
            System.err.println("ERROR: No se ha encontrado el archivo personajes.xml");
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   

    private void inicializarActividad(int nivel) {
        if (nivel < 0 || nivel >= gestorMapa.getNumNiveles());
        Mapa mapa = gestorMapa.getMapa(nivel);
        /*PARCHE 1*/
        this.parcheActividadInicial(nivel);
        this.cargar_Actividad_XML(nivel);
    }

    private void cargar_Actividad_XML(int nivel) {
        Mapa mapa = gestorMapa.getMapa(nivel);        
        try {
            File inputFile = new File("terreno.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("Nivel");
            Node nNode = nList.item(nivel);
            Element eElement = (Element) nNode;
            
            NodeList filas = eElement.getElementsByTagName("fila");
            NodeList columnas = eElement.getElementsByTagName("columna");
            
            for (int i = 0; i < filas.getLength(); i++){
                int fila = Integer.parseInt(filas.item(i).getTextContent());
                int col = Integer.parseInt(columnas.item(i).getTextContent());
                Terreno terreno = ((Terreno) mapa.getMapaAt(fila, col).getObj());
                terreno.setActivo(false);
                mapa.getListaTerrenoInactivo().add(terreno);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }    
    
    private void cargar_Objetos_XML(int nivel){
        Mapa mapa = gestorMapa.getMapa(nivel);
        try {
            File inputFile = new File("objetos.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("ObjetoAyuda");
            
            for (int i = 0; i < nList.getLength(); i++){
                Node nNode = nList.item(i);
                Element eElement = (Element) nNode;
                String elmGrf = eElement.getElementsByTagName("elementoGrafico").item(0).getTextContent();
                char elementoGrafico = elmGrf.charAt(0);
                int fila = Integer.parseInt(eElement.getElementsByTagName("fila").item(0).getTextContent());
                int columna = Integer.parseInt(eElement.getElementsByTagName("columna").item(0).getTextContent());
                Dibujable dib = mapa.getMapaAt(fila, columna).getObj();
                if (dib instanceof Objeto){
                    Objeto obj = (Objeto) dib;
                    obj.setElementoGrafico(elementoGrafico);
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
    
    private void parcheActividadInicial(int nivel){
        Mapa mapa = gestorMapa.getMapa(nivel);
        if (mapa == null) return;
        for (int i = 0; i < 12; i++)
            for(int j = 0; j < 16; j++){
                Celda celda = mapa.getMapaAt(i, j);
                Dibujable dib = celda.getObj();
                if (dib instanceof Terreno)
                    ((Terreno) dib).setActivo(true);
            }
    }
}
