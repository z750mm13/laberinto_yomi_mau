/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogl2;

import java.awt.DisplayMode;

import java.io.File;
import java.io.IOException;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;

import javax.swing.JFrame;

import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.media.opengl.GL;

public class CubeTexture implements GLEventListener, KeyListener {

    public CubeTexture() {
        addKeyListener(this);
    }

    public static DisplayMode dm, dm_old;
    private GLU glu = new GLU();
    private float ztra, yrot, xtra, xrot;
    private Texture stoneBrickTexture, chiseledStoneBrickTexture;
    private final float SCALE = 0.3f;
    private int timer = 0;
    private float traslate = 0f;
    private boolean arriba = true;
    @Override
    public void display(GLAutoDrawable drawable) {

        // TODO Auto-generated method stub
        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity(); // Reset The View
        
        gl.glRotatef(yrot, 0, 1.0f, 0f);
        gl.glTranslatef(-2f, -2f, -5.0f);
        
        glu.gluLookAt(0.5 + xtra, 0, 5.5 + ztra,0.5+xtra, 0,2.5+ztra,0.0, 1.0, 0.0);
        gl.glLineWidth(5);
        
        gl.glTranslatef(0, 0, xtra);
        gl.glRotatef(80, 1.0f, 0f, 0f);
        //gl.glRotatef(zrot, 0, 0, 1f);
        //xrot += 0.1;
        showRules(gl);
        gl.glScaled(SCALE, SCALE, SCALE);
        
        /**
         * Creacion del laberinto
         */
        gl.glPushMatrix();
        makeMureOfStoneBrick(gl, 10, 3, stoneBrickTexture);
        
        gl.glTranslatef(4f, -6f, -18f);
        gl.glRotatef(90, 0f, 1f, 0f);
        makeMureOfStoneBrick(gl, 2, 3, stoneBrickTexture);
        
        gl.glTranslatef(-10f, -6f, -2f);
        gl.glRotatef(180, 0f, 1f, 0f);
        makeMureOfStoneBrick(gl, 3, 3, stoneBrickTexture);
        
        gl.glTranslatef(8f, -6f, -2f);
        makeMureOfStoneBrick(gl, 5, 3, stoneBrickTexture);
        
        gl.glTranslatef(0f, -6f, -10f);
        makeMureOfStoneBrick(gl, 2, 3, stoneBrickTexture);
        
        gl.glTranslatef(-8f, -6f, 0f);
        makeMureOfStoneBrick(gl, 2, 3, stoneBrickTexture);
        
        if ((timer)%180==0) arriba = !arriba;
        
        if(arriba) traslate += 0.019f;
        else traslate -= 0.019f;

        gl.glTranslatef(-6f, -6f-traslate, 0f);
        makeMureOfStoneBrick(gl, 2, 2, stoneBrickTexture);
        gl.glTranslatef(0f, traslate, 0f);
        
        gl.glTranslatef(-4f, -4f, 6f);
        makeMureOfStoneBrick(gl, 5, 3, stoneBrickTexture);

        gl.glTranslatef(2f, -6f, -2f);
        makeMureOfStoneBrick(gl, 1, 3, stoneBrickTexture);
        
        gl.glTranslatef(2f, -6f, 6f);
        makeMureOfStoneBrick(gl, 4, 3, stoneBrickTexture);
        
        gl.glTranslatef(2f, -6f, -2f);
        makeMureOfStoneBrick(gl, 1, 3, stoneBrickTexture);
        
        gl.glTranslatef(0f, -6f, -4f);
        makeMureOfStoneBrick(gl, 1, 3, stoneBrickTexture);
        
        gl.glTranslatef(2f, -6f, -4f);
        makeMureOfStoneBrick(gl, 1, 3, stoneBrickTexture);
        
        gl.glTranslatef(6f, -6f, 10f);
        makeMureOfStoneBrick(gl, 6, 3, stoneBrickTexture);
        
        gl.glTranslatef(-2f, -6f, -6f);
        gl.glRotatef(90, 0f, 1f, 0f);
        makeMureOfStoneBrick(gl, 2, 3, stoneBrickTexture);
        
        gl.glTranslatef(8f, -6f, 6f);
        makeMureOfStoneBrick(gl, 10, 3, stoneBrickTexture);
        
        /**
         * Piso del laberinto
         */
        gl.glTranslatef(2f, -8f, 2f);
        gl.glRotatef(90, 0f, 0f, 1f);
        makeMureOfStoneBrick(gl, 12, 12, chiseledStoneBrickTexture);
        gl.glPopMatrix();
        
        //Puerta
        int i = 90;
        if(timer < 90) i = timer;
        gl.glRotatef(270-i, 0f, 1f, 0f);

        makeMureOfStoneBrick(gl, 2, 3, stoneBrickTexture);
        
        timer += 1;
        System.out.println("timer: " + timer);
    }

    private void makeMureOfStoneBrick(GL2 gl, int length, int height, Texture stoneBrickTexture) {
        length--;
        for (int j = 0; j < height; j++){
            makeStoneBrickCube(gl, stoneBrickTexture);
            for (int i = 0; i < length; i++) {
                gl.glTranslatef(0f, 0f, -2f);
                makeStoneBrickCube(gl, stoneBrickTexture);
            }
            gl.glTranslatef(0f, 2f, (2*length));
        }
    }
    
    private void makeStoneBrickCube(GL2 gl, Texture stoneBrickTexture){
        stoneBrickTexture.enable(gl);//habilitamos
        stoneBrickTexture.bind(gl);//pegamos
        makeCube(gl);
        stoneBrickTexture.disable(gl);//desabilitar
    }
    
    private void makeCube(GL2 gl) {
        gl.glBegin(GL2.GL_QUADS);

        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);

        // Back Face
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);

        // Top Face
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);

        // Bottom Face
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);

        // Right face
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, 1.0f);

        // Left Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);

        gl.glEnd();
        gl.glFlush();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        // method body
    }

    @Override
    public void init(GLAutoDrawable drawable) {

        final GL2 gl = drawable.getGL().getGL2();

        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glClearColor(0f, 0f, 0f, 0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glDepthFunc(GL2.GL_LEQUAL);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
        
        loadTextures();
    }
    
    private void loadTextures() {
        try {
            stoneBrickTexture = TextureIO.newTexture(new File("/home/mau/images/img/stone.png"), true);
            chiseledStoneBrickTexture = TextureIO.newTexture(new File("/home/mau/images/img/chiseledStoneBrick.png"), true);
        } catch (IOException e) {
            System.err.print("No se puede cargar textura" + e);
            System.exit(1);
        }
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

        // TODO Auto-generated method stub
        final GL2 gl = drawable.getGL().getGL2();
        if (height <= 0) {
            height = 1;
        }

        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
       if(arg0.getKeyCode()==KeyEvent.VK_ESCAPE){
            yrot=0;
            ztra=0;
            xtra=0;
        }

        if(arg0.getKeyCode()==KeyEvent.VK_RIGHT){
            yrot+=1.0f;
            System.out.println("El valor de Z en la rotacion: "+yrot);
        }

        if(arg0.getKeyCode()==KeyEvent.VK_LEFT){
            yrot-=1.0f;
            System.out.println("El valor de Z en la rotacion: "+yrot);
        }

         if(arg0.getKeyCode()==KeyEvent.VK_UP){
            ztra-=.10f*Math.sin(yrot);//y
            xtra-=.10f*Math.cos(yrot);
            System.out.println("Posicion de la camara en Y: "+ztra);
        }

          if(arg0.getKeyCode()==KeyEvent.VK_DOWN){
            ztra+=.10f*Math.sin(yrot);//y
            xtra+=.10f*Math.cos(yrot);
            System.out.println("Posicion de la camara en Y: "+ztra);
          }
    }

    public static void main(String[] args) {

        // TODO Auto-generated method stub
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        CubeTexture r = new CubeTexture();

        glcanvas.addGLEventListener(r);
        glcanvas.setSize(400, 400);

        final JFrame frame = new JFrame(" Textured Cube");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        frame.addKeyListener(r);
        final FPSAnimator animator = new FPSAnimator(glcanvas, 300, true);

        animator.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void showRules(GL2 gl) {
        //Creamos los ejes del plano x,y,z
            // EJE X  rojo
            gl.glBegin(GL.GL_LINES);
            gl.glColor3f(1f, 0f, 0f);
            gl.glVertex3f(0, 0, 0);
            gl.glVertex3f(4, 0, 0);
            gl.glEnd();

            //EJE Y verde
            gl.glBegin(GL.GL_LINES);
            gl.glColor3f(0f, 1f, 0f);
            gl.glVertex3f(0, 0, 0);
            gl.glVertex3f(0, 4, 0);
            gl.glEnd();

            //EJE Z Azul
            gl.glBegin(GL.GL_LINES);
            gl.glColor3f(0f,0f, 1f);
            gl.glVertex3f(0, 0, 0);
            gl.glVertex3f(0, 0, 4);
            gl.glEnd();
            gl.glColor3f(1f,1f, 1f);
            
            gl.glPolygonMode(GL.GL_FRONT_AND_BACK, gl.GL_FILL);
    }
}
