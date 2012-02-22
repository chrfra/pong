package pong.control;

import java.io.InvalidClassException;
import java.util.Random;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Settings;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import pong.model.Ball;
import pong.model.Const;
import pong.model.GameItem;
import pong.model.Paddle;
import pong.model.Wall;

public class Physics {

	private World world;
	// private PolygonDef groundShapeDef;

	Body body;

	public void create(GameEngine ge) {
		Vec2 gravity = new Vec2(0.0f, 0.0f);
		float width = Const.GAME_WIDTH;
		float height = Const.GAME_HEIGHT;
		boolean doSleep = true;
		world = new World(gravity, true);
		//Makes stuff bounce at lower velocities.
		Settings.velocityThreshold = 0.0f;
		// Adds a collisionlistener to the world that listens for collisions. 
		//This can be used for checking if the ball has hit a wall.
		world.setContactListener(new HitDetection(ge)); 

		// Testbed
//		 TestbedModel model = new TestbedModel(); // create our model
//		
//		 // add tests
//		 TestList.populateModel(model); // populate the provided testbed tests
//		 model.addCategory("My Super Tests"); // add a category
//		 model.addTest(new PhysTest()); // add our test
//		
//		 // add our custom setting "My Range Setting", with a default value of 10, between 0 and 20
//		 model.getSettings().addSetting(new TestbedSetting("My Range Setting",
//		 SettingType.ENGINE, 10, 0, 20));
//		
//		 TestbedPanel panel = new TestPanelJ2D(model); // create our testbed panel
//		
//		 JFrame testbed = new TestbedFrame(model, panel); // put both into our testbed frame etc
//		 testbed.setVisible(true);
//		 testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * 
	 */
	public void destroyBody(Body body){
		world.destroyBody(body);
	}
	
	
	/**
	 * Adds a static wall to the simulation
	 * @param wall
	 */
	public void addWall(Wall wall){
		float x = wall.getxPos();
		float y = wall.getyPos();
		float len = wall.getLength();
		
		// Make a Body for the ground via definition and shape binding that gives it a boundary
		BodyDef groundBodyDef = new BodyDef(); // body definition
		groundBodyDef.position.set(0.0f, 0.0f); // set bodydef position
		Body physWall = world.createBody(groundBodyDef); // create body based on definition
		
		//Add the wall to the body's userdata
		physWall.setUserData(wall);
		
		PolygonShape shape = new PolygonShape();
		
		if(wall.isStanding()){
			shape.setAsEdge(new Vec2(x, -len/2), new Vec2(x, len/2));
			physWall.createFixture(shape, 0.0f);
		}else{
			shape.setAsEdge(new Vec2(len/2, y), new Vec2(-len/2, y));
			physWall.createFixture(shape, 0.0f);
		}
		
	}
	
	
	private Body addBall(GameItem item) throws InvalidClassException {
		// Make another Body that is dynamic, and will be subject to forces.
		Ball ball;
		float x, y, r;
		if (item.getType().equals("BALL")) {
			ball = (Ball) item;
			x = ball.getxPos();
			y = ball.getyPos();
			r = ball.getRadius();
		} else {
			throw new InvalidClassException(
					"Wrong class to be added to physicsengine");
		}

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC; // dynamic means it is subject to forces
		bodyDef.position.set(x, y);
		body = world.createBody(bodyDef);
		CircleShape dynamicBall = new CircleShape();
		dynamicBall.m_type = ShapeType.CIRCLE;
		//Sets size to radius r
		dynamicBall.m_radius = r;
		FixtureDef fixtureDef = new FixtureDef(); // fixture def that we load up with the following info:
		fixtureDef.shape = dynamicBall; // ... its shape is the dynamic box (2x2 rectangle)
		fixtureDef.density = 1.0f; // ... its density is 1 (default is zero)
		fixtureDef.restitution = 1.0f;
		fixtureDef.friction = 0.0f; // ... its surface has some friction coefficient
		body.createFixture(fixtureDef); // bind the dense, friction-laden fixture to the body
		body.setUserData(ball);
		return body;
	}

	private Body addBox(GameItem item) throws InvalidClassException {
		Paddle pad;
		float x, y, w, h;

		if (item.getType().equals("PADDLE")) {
			pad = (Paddle) item;
			x = pad.getxPos();
			y = pad.getyPos();
			w = pad.getWidth();
			h = pad.getHeight();
		} else {
			throw new InvalidClassException(
					"Wrong class to be added to physicsengine");
		}

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC; // dynamic means it is subject to forces
		bodyDef.position.set(x, y);
		body = world.createBody(bodyDef);
		PolygonShape box = new PolygonShape();
		// Set size. Takes the half-width and half-height
		box.setAsBox(w/2, h/2);
		FixtureDef fixtureDef = new FixtureDef(); // fixture def that we load up with the following info:
		fixtureDef.shape = box; // ... its shape is the dynamic box
		fixtureDef.density = 20.0f; // ... its density is 1 (default is zero)
		fixtureDef.friction = 0.3f; // ... its surface has some friction coefficient
		fixtureDef.restitution = 0.0f; // ... set bouncy bouncy
		body.createFixture(fixtureDef); // bind the dense, friction-laden fixture to the body
		body.setFixedRotation(true);
		body.setUserData(pad);
		return body;

	}

	public Body addObject(GameItem item) {
		try {
			if (item.getType().equals("PADDLE")) {
				return addBox(item);
			} else if (item.getType().equals("BALL")) {
				Random rnd = new Random();
				boolean positiveY = rnd.nextBoolean(), positiveX = rnd.nextBoolean();
				Body ball = addBall(item);
				//Set random starting velocity
				if (positiveX) {
					if (positiveY) {
						ball.setLinearVelocity(new Vec2(rnd.nextFloat()+0.5f, rnd.nextFloat()+0.5f));
					} else {
						ball.setLinearVelocity(new Vec2(rnd.nextFloat()+0.5f, -rnd.nextFloat()-0.5f));
					}
				} else {
					if (positiveY) {
						ball.setLinearVelocity(new Vec2(-rnd.nextFloat()-0.5f, rnd.nextFloat()+0.5f));
					} else {
						ball.setLinearVelocity(new Vec2(-rnd.nextFloat()-0.5f, -rnd.nextFloat()-0.5f));
					}
				}
				return ball;
			}
		} catch (InvalidClassException e) {
			e.printStackTrace();
		}

		return null;

	}

	public void update() {
		// Simulate the world
		//
		float timeStep = 1.0f / 60.f;
		int velocityIterations = 6;
		int positionIterations = 2;
		world.step(timeStep, velocityIterations, positionIterations);

	}
}
