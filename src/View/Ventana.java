/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

/**
 *
 * @author Godievski
 */


import static View.Renderizador.MAX_SIZE;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Ventana extends JFrame implements KeyListener{
    
    private Juego juego;
    public JPanel pnlGrafico;
    public JPanel pnlTexto;
    public JPanel container;
    private static int index = 0;
    
    public Ventana() throws IOException, InterruptedException{
        initComponents();
        addKeyListener(this);
        setResizable(false);
        setVisible(true);
        juego = new Juego(this);
        juego.renderizar();
    }
    
    private void initComponents(){    
        setSize(1224,768);
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
    
    @Override
    public void keyPressed(KeyEvent evt) {
        char keyChar = evt.getKeyChar();
        //Opción para salir del juego
        if (keyChar == Event.ESCAPE) {
            int opcion = JOptionPane.showConfirmDialog(null,
                    "Esta seguro de querer salir?", "Y de donde vengo yo?",
                    JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) System.exit(0);
        }
        //CAPTURA MOVIMIENTO - USAR INTERPRETE DE COMANDO
        if (Juego.eventFlag == Juego.CAPTURAR_MOVIMIENTO){
            try {
                juego.capturarAccion(keyChar);
            } catch (IOException | InterruptedException ex){
                System.out.println("Error captura movimiento");
            }
            //PINTAR PANTALLA - USAR JUEGO.RENDERIZADOR
            try {
                juego.renderizar();
            } catch (IOException | InterruptedException ex){
                System.out.println("Error renderizar");
            }
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
            juego.actualizarInfo();
        } catch (IOException | InterruptedException ex){
                System.out.println("Error en actualizar info");
        }   
        System.out.println("Key: " + keyChar + " Index: " + index);
    }

    @Override
    public void keyReleased(KeyEvent evt) {
    }

    @Override
    public void keyTyped(KeyEvent evt) {
    }
}

