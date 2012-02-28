package pong.control;

import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import static pong.model.Const.*;
import pong.model.Ball;
import pong.model.GameItem;
import pong.model.Player;

public class ComputerAI {

	private int difficulty = DEFAULT_AI_DIFFICULTY;
	private float mostClose = 9999;
	private Vec2 ballvector = null;
	private Vec2 paddlevector = null;
	private Body ballBody = null;

	public ComputerAI(int difficulty){
		this.difficulty = difficulty;
	}

	public void MoveAI(Player player2, List<GameItem> items, Ball mainBall){
		Body paddleBody = player2.getPaddle().getBody();

		ballBody = findClosestBall(paddleBody, items);

		if(ballBody == null){
			ballBody = mainBall.getBody();
		}

		ballvector = ballBody.getPosition();
		paddlevector = paddleBody.getPosition();
		
		//System.out.println("Closest balls Ypos: " + ballvector.y);

		if( ballvector.y > 0){

			// NUDGE THE BALL IF IT IS CLOSE TO THE PADDLE --- THIS DOES NOT WORK YET
			if( ballvector.y < paddlevector.y && ballvector.y > 28 ){
				paddleBody.setLinearVelocity(new Vec2( 0f , -AI_NUDGE_FORCE));
			}

			//			else if( paddlevector.x == ballvector.x ){
			//				// SET FORCE TO ZERO - PADDLEPOSITION IS GOOD
			//				paddleBody.setLinearVelocity(new Vec2( 0f , 0f));
			//			} // paddle is to the right of the ball
			else if( paddlevector.x > ballvector.x - DEFAULT_PADDLE_WIDTH / 2){
				paddleBody.setLinearVelocity(new Vec2( -DEFAULT_AI_SPEED*difficulty , 0f));
			}
			else if( paddlevector.x < ballvector.x + DEFAULT_PADDLE_WIDTH / 2){
				paddleBody.setLinearVelocity(new Vec2( DEFAULT_AI_SPEED*difficulty , 0f));
			}
		} // END IF BALL IS NOT ON AI's SIDE

		// If on the other players area, then move slowly towards where the ball is
		else
		{
			if(paddlevector.x > ballvector.x ){
				paddleBody.setLinearVelocity(new Vec2( -difficulty*2 , 0f));
			}
			else if(paddlevector.x < ballvector.x ){
				paddleBody.setLinearVelocity(new Vec2( difficulty*2 , 0f));
			}
		}
	}

	public Body findClosestBall(Body paddleBody, List<GameItem> items) {

		mostClose = 9999;
		Body ballbody = null;
		float paddlePosY = paddleBody.getPosition().y;
		for(GameItem item : items){
			// Must be a ball
			if( item instanceof Ball && item.getBody().getPosition().y > 0){
				float itemYpos = item.getBody().getPosition().y;
				// Is this item closer to the AI's paddle then then another item?
				float isItemClose = paddlePosY-itemYpos;
//				System.out.println("Value from isItemClose: " + isItemClose);
				if( isItemClose < mostClose ){
					mostClose = paddlePosY-itemYpos;
					ballbody = item.getBody();
//					System.out.println("Found a better match!");
				}
			}
		}
		return ballbody;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

}
