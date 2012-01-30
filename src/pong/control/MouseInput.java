package pong.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;

import pong.view.GraphicsEngine;


public class MouseInput implements KeyListener, MouseInputListener{
	int mouseSens = 100; //mouse sensitivity
	GraphicsEngine q;
	public MouseInput(GraphicsEngine graphicsEngine){
		this.q=graphicsEngine;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println(arg0.getXOnScreen());
		float offset =4; //used to alleviate the consequences of the cursor being too far away from the quad at startup
		//q.moveY(-((float)arg0.getYOnScreen() / mouseSens)+offset);
	//	q.moveX(((float)arg0.getXOnScreen() / mouseSens)-offset-1);
		//r.delay(500);	fun effect to make game harder if using a robot object
	}

	public void keyPressed(KeyEvent e) {
		//close program on escape button
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			q.exit();
			//exit fullscreen on F12 button
		}
	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {
	}

}