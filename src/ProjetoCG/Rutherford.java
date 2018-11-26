package ProjetoCG;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author Vitor
 *
 * 
 */


import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

/**
 *
 * @author Glauco
 */
public class Rutherford 
        implements GLEventListener {

    GLU glu = new GLU();
    GLUT glut = new GLUT();
    
    public static void main(String args[])
    {
        new Rutherford();
    }
    private double g = 0;
    private double g2;
    private boolean desenhar = false;
    
    
    public Rutherford()
    {
        GLJPanel canvas = new GLJPanel();
        canvas.addGLEventListener(this);
        
        JFrame frame = new JFrame("Exemplo01");
        frame.setSize(700, 700);
        frame.getContentPane().add(canvas);
        frame.setVisible(true);
      
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new Thread(new Runnable() {
                    public void run() {
                        System.exit(0);
                    }
                }).start();
            }
        });

    
    }
    
    
    @Override
    public void init(GLAutoDrawable glAuto) {
        Animator a = new Animator(glAuto);
        a.start();
        GL gl = glAuto.getGL();
        gl.glClearColor(0.4f, 0.4f, 0.4f, 0.4f);
        gl.glEnable(GL.GL_DEPTH_TEST);
    }

    @Override
    public void display(GLAutoDrawable glAuto) {

        GL2 gl = glAuto.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT |
                   GL.GL_DEPTH_BUFFER_BIT
        );
        
        gl.glLoadIdentity();
        gl.glTranslated(0,0,-60);
                
        gl.glRotated(g, 0, 1, 0);
        
//        if(desenhar) {
//            desenhaOrbitaCarbono(gl, glut);
//            desenhaNucleoCarbono(gl, glut, g);
//        }
        
        desenhaOrbitaCarbono(gl, glut);
        desenhaNucleoCarbono(gl, glut, g);
        
        g = g + 0.5;
        g2 = g2 + 2;
    }

    public void reshape(GLAutoDrawable gLAutoDrawable, int x, int y, int w, int h) {
  
        GL2 gl = gLAutoDrawable.getGL().getGL2(); 
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(60,1,1,300);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslated(0,0,-10);
    }


    public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
      
    }

    @Override
    public void dispose(GLAutoDrawable glad) {
        
    }

    private void desenhaNucleoCarbono(GL2 gl, GLUT glut, double g) {
       gl.glPushMatrix();     
       gl.glTranslated(0, 0, -2.5);
       
       for(int i = 0; i < 2; i++)
       {
        gl.glPushMatrix();
            //gl.glRotated(g, 0, 1, 1);
            
            gl.glTranslated(-2.5, -2.5, 0);
            glut.glutSolidSphere(2.5, 20, 20);
            
            gl.glTranslated(0, 5, 0);
            glut.glutSolidSphere(2.5, 20, 20);
            
            gl.glTranslated(5, 0, 0);
            glut.glutSolidSphere(2.5, 20, 20);
            
            gl.glTranslated(0, -5, 0);
            glut.glutSolidSphere(2.5, 20, 20);
            
            gl.glTranslated(-2.5, -5, 0);
            glut.glutSolidSphere(2.5, 20, 20);
            
            gl.glTranslated(0, 15, 0);
            glut.glutSolidSphere(2.5, 20, 20);
            
        gl.glPopMatrix();
        
        gl.glTranslated(0, 0, 5);
       }
       gl.glPopMatrix();
    }

    private void desenhaOrbitaCarbono(GL2 gl, GLUT glut) {
        gl.glPushMatrix();
            gl.glRotated(45, 0, 0, 1);
            gl.glRotated(g2, 1, 0, 0);
            gl.glTranslated(0, 25, 0);
            glut.glutSolidSphere(1.1, 20, 20);
        gl.glPopMatrix();
        
        gl.glPushMatrix();
            gl.glRotated(-135, 0, 0, 1);
            gl.glRotated(g2, 1, 0, 0);
            gl.glTranslated(0, 25, 0);
            glut.glutSolidSphere(1.1, 20, 20);
        gl.glPopMatrix();
        
        gl.glPushMatrix();
            gl.glRotated(-45, 0, 0, 1);
            gl.glRotated(g2, 1, 0, 0);
            gl.glTranslated(0, 25, 0);
            glut.glutSolidSphere(1.1, 20, 20);
        gl.glPopMatrix();
        
        gl.glPushMatrix();
            gl.glRotated(135, 0, 0, 1);
            gl.glRotated(g2, 1, 0, 0);
            gl.glTranslated(0, 25, 0);
            glut.glutSolidSphere(1.1, 20, 20);
        gl.glPopMatrix();
        
        gl.glPushMatrix();
            gl.glRotated(45, 0, 0, 1);
            gl.glRotated(90, 1, 0, 0);
            gl.glRotated(g2, 1, 0, 0);
            gl.glTranslated(0, 25, 0);
            glut.glutSolidSphere(1.1, 20, 20);
        gl.glPopMatrix();
        
        gl.glPushMatrix();
            gl.glRotated(45, 0, 0, 1);
            gl.glRotated(-90, 1, 0, 0);
            gl.glRotated(g2, 1, 0, 0);
            gl.glTranslated(0, 25, 0);
            glut.glutSolidSphere(1.1, 20, 20);
        gl.glPopMatrix();
        
        gl.glRotated(90, 1, 0, 0);
        gl.glRotated(45, 0, 1, 0);
        glut.glutWireTorus(0, 25, 30, 30);
        gl.glRotated(45, 0, 1, 0);
        //glut.glutWireTorus(0, 25, 30, 30);
        gl.glRotated(45, 0, 1, 0);
        glut.glutWireTorus(0, 25, 30, 30);
    }
    
    public void keyTyped(KeyEvent e) {

    }
    
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            desenhar = true;
            
        } 
    }
}











