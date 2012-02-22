package pong.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;


public class MouseInput implements KeyListener, MouseInputListener{
	private float xPos, yPos, dx,dy;
	//updates the moveable item below every time the mouse is moved 
	//(perhaps faster performance can be achieved if only fetching the mouseX and mouseY when the item is drawn)?
	private GameEngine ge;
	
	public MouseInput(GameEngine ge){
		this.ge = ge;
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
		//arg0.getY() returns the coordinate of the cursor where (x,y) = (0,0) in the top left corner of the object that this listener is registered to, in this case, the glDrawable (our window).
		//Whereas arg0.getYOnScreen() has (x,y) = (0,0) in the top left corner of the SCREEN (= useless for moving objects on the canvas)
		yPos = (-(float)arg0.getY());
		xPos = ((float)arg0.getX());
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

	public void keyPressed(KeyEvent e) {
/*		float offset =40; //used to alleviate the consequences of the cursor being too far away from the quad at startup
		//close program on escape button
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			q.exit();
			//exit fullscreen on F12 button
		}
		else if (e.getKeyCode() == KeyEvent.VK_UP) {			
			q.moveY(q.getPaddle().getyPos()+1);
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) {			
			q.moveY(q.getPaddle().getyPos()-1);
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT) {			
			q.moveX(q.getPaddle().getxPos()-1);
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {			
			q.moveX(q.getPaddle().getxPos()+1);
		}*/
	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {
	}
	public float getxPos() {
		return xPos;
	}
	public float getyPos() {
		return yPos;
	}
}