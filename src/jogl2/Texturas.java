/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogl2;

import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Librerias para las texturas
import java.io.File;
//import com.sun.opengl.util.texture.Texture;
//import com.sun.opengl.util.texture.TextureIO;
import java.io.IOException;
import javax.media.opengl.GL2;
import javax.media.opengl.awt.GLCanvas;

public class Texturas extends JFrame implements KeyListener{
    //Variables para utilizar funciones de opengl
    static GL gl;
    static GLU glu;
    static GLUT glut;
    static GLCanvas canvas;
    
    //Variables para la rotacion
    private static float rotarX=0;
    private static float rotarY=0;
    private static float rotarZ=0;
    
    //Variables para la traslacion
    private static float trasladaX=0;
    private static float trasladaY=0;
    private static float trasladaZ=0;
    
    //Variables para las texturas
    private Texture cara1, cara2,cara3,cara4, cara5,cara6;
    
    //Constructor de la clase
    public Texturas(){
        setSize(700, 600);
        setTitle("Cubo 3D con Texturas");
        setResizable(false);
        //Instancia a la clase GraphicListener
        GraphicListener listener = new GraphicListener();
        canvas = new GLCanvas();
        gl = canvas.getGL();
        glu = new GLU();
        glut = new GLUT();
        
        //A�adimos el oyente de eventos para renderizar
        //el codigo de display
        canvas.addGLEventListener(listener);    
        getContentPane().add(canvas);
        
        //Intanciamos Animator para las animaciones
        Animator animator = new Animator(canvas);
        animator.start();
        addKeyListener(this);
        
    }

        //Main de la clase
    public static void main(String args[]){
        Texturas t = new Texturas();
        t.setVisible(true);
        t.setDefaultCloseOperation(EXIT_ON_CLOSE);
        t.setLocationRelativeTo(null);
    }
    
    public class GraphicListener implements GLEventListener{

        public void display(GLAutoDrawable arg0) {
            final GL2 gl = arg0.getGL().getGL2();
            gl.glClear(GL.GL_COLOR_BUFFER_BIT|GL.GL_DEPTH_BUFFER_BIT);                      
            gl.glMatrixMode(gl.GL_PROJECTION);
            gl.glLoadIdentity();
            
            //Establecemos la perspectiva 
            //Grado, Cuadrado, La distancia minima, la distancia Maxima
            glu.gluPerspective(50, 2, 0.1, 30);
            gl.glMatrixMode(gl.GL_MODELVIEW);
            gl.glDepthFunc(GL.GL_LEQUAL); //Comprueba la profundidad
            gl.glEnable(GL.GL_DEPTH_TEST); // Dibuja pixel si la coordenada Z es mas cercana al punto de vizualizacion
            gl.glClearDepth(1.0); //Inicializa posiciones en 1.0
            
            //Aplicaremos las texturas a los objetos que creemos
            //habilitando el pegado, modulado o blending
            gl.glTexEnvi(gl.GL_TEXTURE_ENV, gl.GL_TEXTURE_ENV_MODE, gl.GL_DECAL);
            gl.glLoadIdentity();
            
            //Creamos la camara y aplicamos las transformaciones para esta                        
            glu.gluLookAt(0.1 + trasladaX, .1 + trasladaY, .5 + trasladaZ, 0.5 + trasladaX,0, -5.5 + trasladaZ, 0.0, 1.0, 0.0);
            //Establecemos un tama�o de punto para los ejes
            gl.glPointSize(10f);
            
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
           
            gl.glPolygonMode(GL.GL_FRONT_AND_BACK, gl.GL_FILL);
            
            //Lo primero sera crear un objeto 
            //crearemos una plataforma en el eje x 
            //Cara 1 Azul
            cara1.enable(gl);//habilitamos
            cara1.bind(gl);//pegamos
            gl.glRotatef(rotarX, 1f, 1f, 0f);
            gl.glRotatef(rotarY, 0f, 1f, 1f);            
            gl.glColor3f(.3f, .8f, .9f);
            gl.glBegin(gl.GL_QUADS);                                            
                
                gl.glTexCoord2f(1, 1);                
                gl.glVertex3f(0, 0, 0);     
                
                gl.glTexCoord2f(0, 1);
                gl.glVertex3f(0.2f, 0f, 0);
                
                gl.glTexCoord2f(0, 0);
                gl.glVertex3f(.2f, .2f, 0);
                
                gl.glTexCoord2f(1, 0);
                gl.glVertex3f(0f, .2f, 0);                                                                                                                                                                                                                                    
            gl.glEnd();
            cara1.disable(gl);//desabilitar
                        
            cara2.enable(gl);
            cara2.bind(gl);
            //Cara 2  Verde
            gl.glColor3f(.1f, .8f, .1f);
            gl.glBegin(gl.GL_QUADS);
                gl.glTexCoord2f(1, 1);
                gl.glVertex3f(.2f, 0, 0);
                
                gl.glTexCoord2f(0, 1);
                gl.glVertex3f(.2f, 0f, -.2f);
                
                gl.glTexCoord2f(0, 0);
                gl.glVertex3f(.2f, .2f, -.2f);
                
                gl.glTexCoord2f(1, 0);
                gl.glVertex3f(.2f, .2f,0f);                                
            gl.glEnd();
            cara2.disable(gl);
            
            cara3.enable(gl);
            cara3.bind(gl);
            //Cara 3  Amarillo       
            gl.glColor3f(.8f, .8f, .1f);
            gl.glBegin(gl.GL_QUADS);                
                gl.glTexCoord2f(1, 1);
                gl.glVertex3f(.2f, 0, -.2f);
                
                gl.glTexCoord2f(0, 1);
                gl.glVertex3f(0f, 0f, -.2f);
                
                gl.glTexCoord2f(0, 0);
                gl.glVertex3f(0f, .2f,-.2f);                
                
                gl.glTexCoord2f(1, 0);
                gl.glVertex3f(.2f, .2f, -.2f);
            gl.glEnd();
            cara3.disable(gl);
                        
            //Cara 4  ROJO
            cara4.enable(gl);
            cara4.bind(gl);
            gl.glColor3f(.9f, .1f, .1f);
            gl.glBegin(gl.GL_QUADS);                
                gl.glTexCoord2f(0, 0);
                gl.glVertex3f(.0f, 0, 0f);                               
                                
                gl.glTexCoord2f(1, 0);
                gl.glVertex3f(0f, .2f,0f);                
                                
                gl.glTexCoord2f(1, 1);
                gl.glVertex3f(0f, .2f, -.2f);                                                
                
                gl.glTexCoord2f(0, 1);
                gl.glVertex3f(0f, 0f, -.2f);
            gl.glEnd();
            cara4.disable(gl);
            
            //Cara 5  Verde Fuerte
            cara5.enable(gl);
            cara5.bind(gl);
            gl.glColor3f(.1f, .4f, .1f);
            gl.glBegin(gl.GL_QUADS);
                gl.glTexCoord2f(1, 1);
                gl.glVertex3f(.2f, 0f, 0f);
                
                gl.glTexCoord2f(0, 1);
                gl.glVertex3f(.2f, 0f, -.2f);
                
                gl.glTexCoord2f(0, 0);
                gl.glVertex3f(0f, 0f,-.2f);
                
                gl.glTexCoord2f(1, 0);
                gl.glVertex3f(.0f, 0, 0f);                                                
            gl.glEnd();
            cara5.disable(gl);
            
            //Cara 6  morado
            cara6.enable(gl);
            cara6.bind(gl);
            gl.glColor3f(.3f, .1f, .3f);
            gl.glBegin(gl.GL_QUADS);
                gl.glTexCoord2f(1, 0);
                gl.glVertex3f(.2f, .2f, 0f);
                
                gl.glTexCoord2f(0, 0);
                gl.glVertex3f(.2f, .2f, -.2f);
                
                gl.glTexCoord2f(0, 1);
                gl.glVertex3f(0f, .2f, -.2f);
                                
                gl.glTexCoord2f(1, 1);
                gl.glVertex3f(.0f, .2f, 0f);                                
                
            gl.glEnd();
            cara6.disable(gl);                                
            
            gl.glFlush();            
        }

        public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2){
        }

        public void init(GLAutoDrawable arg0){             
            //Establecemos la ruta de la textura y la variable que tomara dicha textura
            try {
            //Se indica la localizacion de la figura                
            cara1 = TextureIO.newTexture(new File("/home/mau/images/img/stone.png"), true);            
            cara2 = TextureIO.newTexture(new File("/home/mau/images/img/stone.png"), true);            
            cara3 = TextureIO.newTexture(new File("/home/mau/images/img/stone.png"), true);       
            cara4 = TextureIO.newTexture(new File("/home/mau/images/img/stone.png"), true);       
            cara5 = TextureIO.newTexture(new File("/home/mau/images/img/stone.png"), true);       
            cara6 = TextureIO.newTexture(new File("/home/mau/images/img/stone.png"), true);       
            } catch (IOException e) {
                System.err.print("No se puede cargar textura" + e);
                System.exit(1);
            }
        }

        public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4){                      
        }//reshape..

        @Override
        public void dispose(GLAutoDrawable drawable) {
            
        }
    
    }
          
    //Creacion del metodo que detecta las teclas pulsadas     
    public void keyPressed(KeyEvent arg0){
       if(arg0.getKeyCode()==KeyEvent.VK_ESCAPE){
            rotarX=0;
            rotarY=0;
            rotarZ=0;
            trasladaX=0;
            trasladaY=0;
            trasladaZ=0;
        }
        if(arg0.getKeyCode()==KeyEvent.VK_X){
            rotarX+=1.0f;
            System.out.println("El valor de X en la rotacion: "+rotarX);
        }

        if(arg0.getKeyCode()==KeyEvent.VK_A){
            rotarX-=1.0f;
            System.out.println("El valor de X en la rotacion: "+rotarX);
        }

        if(arg0.getKeyCode()==KeyEvent.VK_Y){
            rotarY+=1.0f;
            System.out.println("El valor de Y en la rotacion: "+rotarY);
        }

        if(arg0.getKeyCode()==KeyEvent.VK_B){
            rotarY-=1.0f;
            System.out.println("El valor de Y en la rotacion: "+rotarY);
        }

        if(arg0.getKeyCode()==KeyEvent.VK_Z){
            rotarZ+=1.0f;
            System.out.println("El valor de Z en la rotacion: "+rotarZ);
        }

        if(arg0.getKeyCode()==KeyEvent.VK_C){
            rotarZ-=1.0f;
            System.out.println("El valor de Z en la rotacion: "+rotarZ);
        }

        //Camara
        if(arg0.getKeyCode()==KeyEvent.VK_RIGHT){
            trasladaX+=.10f;
            System.out.println("Posicion de la camara en X: "+trasladaX);
        }

          if(arg0.getKeyCode()==KeyEvent.VK_LEFT){
            trasladaX-=.10f;
            System.out.println("Posicion de la camara en X: "+trasladaX);
        }

         if(arg0.getKeyCode()==KeyEvent.VK_UP){
            trasladaY+=.10f;
            System.out.println("Posicion de la camara en Y: "+trasladaY);
        }

          if(arg0.getKeyCode()==KeyEvent.VK_DOWN){
            trasladaY-=.10f;
            System.out.println("Posicion de la camara en Y: "+trasladaY);
          }

        if(arg0.getKeyCode()==KeyEvent.VK_1){
            trasladaZ+=.1;
            System.out.println("Posicion de la camara en Z: "+trasladaZ);
          }

        if(arg0.getKeyCode()==KeyEvent.VK_2){
            trasladaZ-=.10f;
            System.out.println("Posicion de la camara en Z: "+trasladaZ);
          }
        if(arg0.getKeyCode()==KeyEvent.VK_3){
            rotarY+=1.0;
            System.out.println("rotacion de la camara en Y: "+rotarY);
        }

        if(arg0.getKeyCode()==KeyEvent.VK_4){
            rotarY-=1.0;
            System.out.println("rotacion de la camara en Y: "+rotarY);
        }

    }//fin keyPressed

    public void keyReleased(KeyEvent arg0){}
    public void keyTyped(KeyEvent arg0){}

    
}
