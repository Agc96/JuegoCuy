package View;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Ventana extends JFrame implements KeyListener{
    
    protected Juego juego;
    public JPanel pnlGrafico;
    public JPanel pnlTexto;
    public JPanel container;
    private static int index = 0;
    
    
    public Ventana(GraphicsConfiguration gc) throws IOException, InterruptedException{
        super(gc);
        initComponents();
        addKeyListener(this);
        setResizable(false);
        setVisible(true);
        this.createBufferStrategy(2);
        this.setIgnoreRepaint(true);
        juego = new Juego(this);
        this.mostrarMenu();
    }
    
    private void initComponents(){    
        this.setBounds(50, 10, 1224, 768+28);
        //setSize(1224,768+38);
        setTitle("Y de donde vengo yo?");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowListener(){
            public void windowActivated(WindowEvent e) {}
            public void windowClosed(WindowEvent e) {}
            public void windowClosing(WindowEvent e){
                int opcion = JOptionPane.showConfirmDialog(null,
                        "Esta seguro de querer salir?",
                        "Y de donde vengo yo?", JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) System.exit(0);
            }
            public void windowDeactivated(WindowEvent e) {}
            public void windowDeiconified(WindowEvent e){}
            public void windowIconified(WindowEvent e){}
            public void windowOpened(WindowEvent e){}
        });
        
     
        
        /*INICIALIZAR PANEL GRAFICO*/
        pnlGrafico = new JPanel();
        pnlGrafico.setBounds(0, 0, 1024, 768);
        pnlGrafico.setSize(1024,768);
        pnlGrafico.setBackground(Color.red);
        pnlGrafico.setIgnoreRepaint(true);
        pnlGrafico.setDoubleBuffered(true);
        pnlGrafico.setFocusable(false);
        
        /* INICIALIZAR PANEL TEXTO */
        pnlTexto = new JPanel();
        pnlTexto.setBounds(1024, 0, 200, 768);
        pnlTexto.setBackground(Color.green);
        pnlTexto.setIgnoreRepaint(true);
        pnlTexto.setDoubleBuffered(true);
        pnlTexto.setFocusable(false);
        
        
        /*INCIALIZAR CONTAINER*/   
        container = new JPanel();
        container.setLayout(null);
        container.setBackground(Color.yellow);
        container.setIgnoreRepaint(true);
        container.setDoubleBuffered(true);
        container.setFocusable(false);
        setContentPane(this.container);
        
        container.add(pnlGrafico);
        container.add(pnlTexto);
    }
    
    public void mostrarMenu(){
        pnlGrafico.getGraphics().clearRect(0, 0, pnlGrafico.getWidth(), pnlGrafico.getHeight());
        pnlTexto.getGraphics().clearRect(0, 0, pnlTexto.getWidth(), pnlTexto.getHeight());
        pnlTexto.getGraphics().drawString("MENÚ PRINCIPAL", 35, 20);
        pnlTexto.getGraphics().drawString("--------------------------\n", 35, 25);
        juego.rend.pnlTexto_mostrarLista(pnlTexto.getGraphics(), juego.lstMenu, 45, 15);        
    }
    
    @Override
    //#MOD
    public void keyPressed(KeyEvent evt) {
        char keyChar = evt.getKeyChar();
        //Opción para salir del juego
        if (keyChar == Event.ESCAPE) {
            int opcion = JOptionPane.showConfirmDialog(null,
                    "Esta seguro de querer salir?", "Y de donde vengo yo?",
                    JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) System.exit(0);
        }
        //#MOD
        //PARA MOSTRAR LAS TRANSICIONES POR DONDE IRA EL PERSONA
        //SE USA LA TECLA M
        if (Juego.eventFlag >= Juego.CAPTURAR_MOVIMIENTO && Juego.eventFlag <= Juego.CAPTURAR_ACCION_DUO){
            if (keyChar =='m' || keyChar =='M'){
                try {
                    juego.MostrarAccionesPantalla();
                } catch (IOException ex) {
                    Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
                }
                return;
            }
        }
        if (keyChar == 'g' || keyChar == 'G'){
            JOptionPane.showMessageDialog(null, "Partida guardada!");
            return;
        }
        
        // MENU INICIAL
        if (Juego.eventFlag == Juego.MENU_JUEGO) {
            if (keyChar == '1') {
                try {
                    pnlTexto.getGraphics().clearRect(0, 0, pnlTexto.getWidth(), pnlTexto.getHeight());
                    juego.renderizar();                
                } catch (IOException | InterruptedException ex){
                    System.out.println("Error renderizar");
                }
                
                pnlTexto.getGraphics().clearRect(0, 0, pnlTexto.getWidth(), pnlTexto.getHeight());
                pnlTexto.getGraphics().drawString("INSTRUCCIONES", 43, 20);
                pnlTexto.getGraphics().drawString("--------------------------\n", 42, 25);                
                juego.rend.pnlTexto_mostrarLista(pnlTexto.getGraphics(), juego.lstInstrucciones, 60, 30);                    
                    
                Juego.eventFlag = Juego.MOSTRAR_INSTRUCCIONES;
            } else if (keyChar == '2') {
                //CARGAR PARTIDAD
                JOptionPane.showMessageDialog(null, "Partida cargada!");
            }
        }
        
        else if (Juego.eventFlag == Juego.MOSTRAR_INSTRUCCIONES) {
            if (keyChar == Event.ENTER) {
                try {
                    pnlTexto.getGraphics().clearRect(0, 0, pnlTexto.getWidth(), pnlTexto.getHeight());
                    juego.renderizar();                
                    Juego.eventFlag = Juego.CAPTURAR_MOVIMIENTO;
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }                
        
        //CAPTURA MOVIMIENTO - USAR INTERPRETE DE COMANDO
        else if (Juego.eventFlag == Juego.CAPTURAR_MOVIMIENTO){
            try {
                juego.capturarAccion(keyChar);
            } catch (IOException | InterruptedException ex){
                System.out.println("Error captura movimiento");
            }
            /*PINTAR PANTALLA - USAR JUEGO.RENDERIZADOR
            try {
                //juego.renderizar();
                juego.dibujarMovJugador();
            } catch (IOException | InterruptedException ex){
                System.out.println("Error renderizar");
            }*/
        } else if (Juego.eventFlag == Juego.CAPTURAR_ACCION_ESPECIAL ||
                   Juego.eventFlag == Juego.CAPTURAR_ACCION_DUO){
            try {
                int tipo;
                tipo = juego.caputarAccionEspecial(keyChar, index);
                if (tipo >= 0 && tipo <= 3)
                    index = 0;
                else if (tipo == 4)
                    index++;
            } catch (IOException | InterruptedException ex){
                System.out.println("Error captura accion");
            }
        }
        try {
            //ACTUALIZAR INFO - USAR JUEGO.ACTUALIZARINFO
            if (Juego.eventFlag >= Juego.CAPTURAR_MOVIMIENTO)
                juego.actualizarInfo();
        } catch (IOException | InterruptedException ex){
                System.out.println("Error en actualizar info");
        }   
    }

    @Override
    public void keyReleased(KeyEvent evt) {
    }

    @Override
    public void keyTyped(KeyEvent evt) {
    }
}

