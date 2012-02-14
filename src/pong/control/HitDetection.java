package pong.control;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;

import pong.model.GameItem;

public class HitDetection implements ContactListener {

	
	
	@Override
	public void beginContact(Contact arg0) {
		Body b1 = arg0.getFixtureA().getBody();
		Body b2 = arg0.getFixtureB().getBody();
		if(b1.getUserData() instanceof GameItem && b2.getUserData() instanceof GameItem ){
			GameItem item1 = (GameItem)b1.getUserData();
			GameItem item2 = (GameItem)b2.getUserData();
		}
		
		
		System.out.println("Contact!");
		
		
	}

	@Override
	public void endContact(Contact arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		// TODO Auto-generated method stub
		
	}

}
