package pong.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;
import pong.model.Const;
import pong.model.MoveableItem;


public class ControlsInput implements KeyListener, MouseInputListener{
	private float xPos;
	private float yPos;
	//updates the moveable item below every time the mouse is moved 
	//(perhaps faster performance can be achieved if only fetching the mouseX and mouseY when the item is drawn)?
	private MoveableItem item;
	
	public ControlsInput(MoveableItem item){
		this.item = item;
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println(arg0.getXOnScreen());
		float offset =-100; //used to alleviate the consequences of the cursor being too far away from the quad at startup
		// offset was previously 4
		yPos = (-((float)arg0.getYOnScreen() / Const.MOUSE_SENSE))+Const.MOUSE_OFFSET;
		xPos = (((float)arg0.getXOnScreen() / Const.MOUSE_SENSE))-Const.MOUSE_OFFSET;
		//move item
		item.moveItem(this);
	}
	
	public float getxPos() {
		return xPos;
	}

	public float getyPos() {
		return yPos;
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

}