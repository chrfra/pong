import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;


public class kmListener implements KeyListener, MouseInputListener{
	int mouseSens = 100; //mouse sensitivity
	JOGLQuad q;
	public kmListener(JOGLQuad q){
		this.q=q;
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
		q.setCenterY(-((float)arg0.getYOnScreen() / mouseSens)+offset);
		q.setCenterX(((float)arg0.getXOnScreen() / mouseSens)-offset-1);
		//r.delay(500);	fun effect to make game harder if using a robot object
	}

	public void keyPressed(KeyEvent e) {
		//close program on escape button
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			JOGLQuad.exit();
			//exit fullscreen on F12 button
		}else if (e.getKeyCode() == KeyEvent.VK_F12) {
			//frame.setUndecorated(false);
		}
		if (e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
			q.setRotateRight(q.getRotateRight()-5);
		}
		if (e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
			q.setRotateRight(q.getRotateRight()+5);
		}
		if (e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
			q.setRotateUp(q.getRotateUp()+5);
		}
		if (e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
			q.setRotateUp(q.getRotateUp()-5);
		}
	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {
	}

}