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
import java.awt.Toolkit;
import java.awt.event.*;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.glu.GLU;
import javax.swing.*;

/**
 *
 * @author Glauco
 */
public class Rutherford implements GLEventListener, KeyListener{

    GLU glu = new GLU();
    GLUT glut = new GLUT();
    
    public static void main(String args[])
    {
        new Rutherford();
    }
    private double g = 0;
    private double g2;
    private boolean desenhar = false;
    private float[] pos = {0, 0, 100, 0};
    private boolean right;
    private boolean left;
    
    Toolkit tk = Toolkit.getDefaultToolkit();
    int xSize = (int) tk.getScreenSize().getWidth();
    int ySize = (int) tk.getScreenSize().getHeight();
    
    public Rutherford()
    {
        GLJPanel canvas = new GLJPanel();
        canvas.addGLEventListener(this);
        
        
        
        JFrame frame = new JFrame("Exemplo01");
        frame.setSize(xSize, ySize);
        frame.getContentPane().add(canvas);
        frame.setVisible(true);
        frame.addKeyListener(this);
        
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
        GL2 gl = glAuto.getGL().getGL2();
        gl.glClearColor(0.2f, 0.2f, 0.2f, 0.2f);
        glut = new GLUT();
        
        //Habilita o teste de profundidade
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT1);
        
        
        float luzEspecular[] = {1,1,1,1};
        float luzDifusa[]  ={1f,1f,1f,1.0f};
        float luzAmbiente[]  ={0.1f,0.1f,0.1f,1.0f};
        
        gl.glLightfv(GL2.GL_LIGHT1, 
                     GL2.GL_DIFFUSE, 
                     luzDifusa,0); 

        gl.glLightfv(GL2.GL_LIGHT1, 
                     GL2.GL_AMBIENT,
                     luzAmbiente,0); 
        
        
        gl.glLightfv(GL2.GL_LIGHT1, 
                     GL2.GL_SPECULAR,
                     luzEspecular,0); 
    }

    @Override
    public void display(GLAutoDrawable glAuto) {
        
        
        
        GL2 gl = glAuto.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT |
                   GL.GL_DEPTH_BUFFER_BIT
        );
        gl.glLoadIdentity();
        gl.glLightfv(GL2.GL_LIGHT1,
                     GL2.GL_POSITION,
                     pos,
                     0);
        
        gl.glPushMatrix();
        gl.glTranslated(0,0,-60);
        
        if(right)
            g = g + 4;
        if(left)
            g = g - 4;
        
        gl.glRotated(g, 0, 1, 0);
        
        float matDifusa[]  ={1f,0f,0f,1.0f};
        float materialAmbiente[] ={0.25f,0,0,1};
        float materialEspecular[] ={1,1,1,1};
        float brilho = 40;
        
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,
                        GL2.GL_DIFFUSE,
                        matDifusa,
                        0);
       
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,
                        GL2.GL_AMBIENT,
                        materialAmbiente,
                        0);
        
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK,
                        GL2.GL_SPECULAR,
                        materialEspecular,
                        0);
        
        gl.glMaterialf(GL.GL_FRONT_AND_BACK,
                        GL2.GL_SHININESS,
                        brilho
                        );
        
        desenhaOrbitaCarbono(gl, glut);
        desenhaNucleoCarbono(gl, glut, g);

        
        gl.glPopMatrix();
        
        gl.glDisable(GL2.GL_LIGHTING);
        gl.glDisable(GL2.GL_TEXTURE_2D);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        glu.gluOrtho2D(0.0, 1920, 0.0, 1080);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        gl.glRasterPos2i(xSize / 2 - 50, ySize + 110);
        glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, "MODELO ATOMICO DE RUTHERFORD");
        gl.glRasterPos2i(xSize / 2 + 130, ySize + 60);
        glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, "CARBONO");
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glPopMatrix();
        gl.glEnable(GL.GL_TEXTURE_2D);      
        gl.glEnable(GL2.GL_LIGHTING);
        
        g2 = g2 + 2;
    }

    public void reshape(GLAutoDrawable gLAutoDrawable, int x, int y, int w, int h) {
  
        GL2 gl = gLAutoDrawable.getGL().getGL2(); 
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(60,(float) w / h,1,300);
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
        float matDifusa2[]  = {0f ,0f ,1f ,1.0f};
        float matDifusa3[]  = {1f , 1f, 0f, 1f}; //amarelo
        gl.glPushMatrix(); 
       
            gl.glMaterialfv(GL.GL_FRONT_AND_BACK,
                        GL2.GL_DIFFUSE,
                        matDifusa2,
                        0);
            
            gl.glRotated(g2, 1, 1, 1);
            gl.glTranslated(0, 0, -2.5);
            for(int i = 0; i < 2; i++)
            {
                 gl.glPushMatrix();
                     
                  gl.glMaterialfv(GL.GL_FRONT_AND_BACK,
                        GL2.GL_DIFFUSE,
                        matDifusa2,
                        0);
                    gl.glTranslated(-2.5, -2.5, 0);
                    glut.glutSolidSphere(2.5, 20, 20);

                    gl.glTranslated(0, 5, 0);
                    glut.glutSolidSphere(2.5, 20, 20);
                    
                    gl.glTranslated(5, 0, 0);
                    glut.glutSolidSphere(2.5, 20, 20);
                    
                       gl.glMaterialfv(GL.GL_FRONT_AND_BACK,
                        GL2.GL_DIFFUSE,
                        matDifusa3,
                        0);
                    gl.glTranslated(0, -5, 0);
                    glut.glutSolidSphere(2.5, 20, 20);

                 gl.glPopMatrix();

                 gl.glTranslated(0, 0, 5);
            }
            
            gl.glMaterialfv(GL.GL_FRONT_AND_BACK,
                        GL2.GL_DIFFUSE,
                        matDifusa3,
                        0);
            
            gl.glPushMatrix();
                 gl.glTranslated(0, 0, -2);
                 glut.glutSolidSphere(2.5, 20, 20);
            gl.glPopMatrix();

            gl.glTranslated(0, 0, -13);
            glut.glutSolidSphere(2.5, 20, 20);

            gl.glTranslated(5.5, 0, 6);
            glut.glutSolidSphere(2.5, 20, 20);

            gl.glTranslated(-11, 0, 0);
            glut.glutSolidSphere(2.5, 20, 20);
       
       gl.glPopMatrix();
    }
    
     private void desenhaNeutronCarbono(GL2 gl, GLUT glut, double g) {
       gl.glPushMatrix();     
       gl.glTranslated(0, 0, -2.5);
       
        for(int i = 0; i < 2; i++)
        {
          gl.glPushMatrix();
            
            gl.glTranslated(-2.5, -2.5, 0);
            glut.glutSolidSphere(2.5, 20, 20);
            
            gl.glTranslated(-5, 5, 0);
            glut.glutSolidSphere(2.5, 20, 20);
            
            gl.glTranslated(0, -10, 0);
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

    private void drawText(int x, int y, String text)
    {
        
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            right = true;
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
            left = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            right = false;
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
            left = false;
    }
}











