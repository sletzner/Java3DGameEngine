package fr.letzner.graphics;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.util.FPSAnimator;

import fr.letzner.graphics.listener.KeyboardController;
import fr.letzner.graphics.listener.MouseController;
import fr.letzner.graphics.listener.MouseMotionController;
import fr.letzner.graphics.listener.Java3DGameEngineEventListener;
import fr.letzner.graphics.shapes.Impl.Arbre;
import fr.letzner.graphics.shapes.Impl.Paysage;
import fr.letzner.graphics.shapes.Impl.ShapeManager;
import fr.letzner.graphics.utils.GameConstants;

public class Java3DGameEngineGenerator extends GLCanvas {
	private static final long serialVersionUID = 1L;
	
	private static GLCanvas canvas = null;
	private static GLU glu; // for the GL Utility
	private static Point center = null;
	private static GLProfile glp = null;
	
	
	/** The entry main() method to setup the top-level container and animator */
	public static void main(String[] args) {
		
		// Run the GUI codes in the event-dispatching thread for thread safety
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				glp = GLProfile.getDefault();
				
				// Initialisation des models 3D
				ShapeManager.getInstance().setPaysage(new Paysage(GameConstants.PAYSAGE_IMAGE_PATH));
				ShapeManager.getInstance().setArbre(new Arbre(1000,ShapeManager.getInstance().getPaysage().getTableauAltitudes()));
				
				// Canvas de rendu de la scene
				canvas = new Java3DGameEngineGenerator();
				canvas.setPreferredSize(new Dimension(GameConstants.WINDOW_WIDTH,
						GameConstants.WINDOW_HEIGHT));
				
				// Definit le centre
				center = new Point();
				center.setLocation(GameConstants.WINDOW_WIDTH / 2, GameConstants.WINDOW_HEIGHT / 2);
				
				try {
					// Controlleurs
					MouseController mouseListener = new MouseController();
					MouseMotionController mouseMotionListener = new MouseMotionController();
					KeyboardController keyboardController = new KeyboardController(); 
							
					canvas.addKeyListener(keyboardController);
					canvas.addMouseListener(mouseListener);
					canvas.addMouseMotionListener(mouseMotionListener);
				} catch (Exception e1) {
					System.out.println("Error in Controler : " + e1.getMessage());
				}
				
				canvas.setFocusable(true);
				canvas.requestFocusInWindow();
				
				// Animator qui gere la boucle de rendu
				final FPSAnimator animator = new FPSAnimator(canvas, GameConstants.FPS, true);

				// Fenetre
				final JFrame frame = new JFrame(); // Swing's JFrame or AWT's
													// Frame
				frame.getContentPane().add(canvas);
				frame.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						// Arret du programme
						new Thread() {
							@Override
							public void run() {
								if (animator.isStarted())
									animator.stop();
								System.exit(0);
							}
						}.start();
					}
				});
				frame.setTitle(GameConstants.TITLE);
				frame.pack();
				//frame.setUndecorated(true);     // no decoration such as title bar
	            //frame.setExtendedState(Frame.MAXIMIZED_BOTH);  // full screen mode
				frame.setVisible(true);
				Rectangle r = frame.getBounds();
				center = new Point(r.x + r.width / 2, r.y + r.height / 2);
				animator.start();
			}
		});
	}

	

	/** Listener pour les evenements OpenGL */
	public Java3DGameEngineGenerator() {
		this.addGLEventListener(new Java3DGameEngineEventListener());
	}
}
