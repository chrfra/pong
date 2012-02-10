package pong.control;

import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JFrame;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.testbed.framework.TestList;
import org.jbox2d.testbed.framework.TestbedFrame;
import org.jbox2d.testbed.framework.TestbedModel;
import org.jbox2d.testbed.framework.TestbedPanel;
import org.jbox2d.testbed.framework.TestbedSetting;
import org.jbox2d.testbed.framework.TestbedSetting.SettingType;
import org.jbox2d.testbed.framework.j2d.TestPanelJ2D;

import pong.model.Ball;
import pong.model.Const;
import pong.model.GameItem;
import pong.model.Paddle;

public class Physics {
	public int targetFPS = 40;
	// public int timeStep = (1000 / targetFPS);
	public int iterations = 5;

	private World world;
	// private PolygonDef groundShapeDef;

	Body body;

	public void create() {
		Vec2 gravity = new Vec2(0.0f, 0.0f);
		float width = Const.GAME_WIDTH;
		float height = Const.GAME_HEIGHT;
		boolean doSleep = true;
		world = new World(gravity, true);

		// Make a Body for the ground via definition and shape binding that gives it a boundary
		BodyDef groundBodyDef = new BodyDef(); // body definition
		groundBodyDef.position.set(0.0f, 0.0f); // set bodydef position
		Body ground = world.createBody(groundBodyDef); // create body based on definition
		
		PolygonShape shape = new PolygonShape();
		shape.setAsEdge(new Vec2(-width/2, -height/2), new Vec2(width/2, -height/2)); // Floor
		ground.createFixture(shape, 0.0f);
		shape.setAsEdge(new Vec2(-width/2, height/2), new Vec2(width/2, height/2)); // Roof
		ground.createFixture(shape, 0.0f);
		shape.setAsEdge(new Vec2(-width/2, -height/2), new Vec2(-width/2, height/2)); // Left
		ground.createFixture(shape, 0.0f);
		shape.setAsEdge(new Vec2(width/2, -height/2), new Vec2(width/2, height/2)); // right
		ground.createFixture(shape, 0.0f);

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
		// dynamicBall.m_p = new Vec2(2.0f, 3.0f);
		dynamicBall.m_radius = r;
		FixtureDef fixtureDef = new FixtureDef(); // fixture def that we load up with the following info:
		fixtureDef.shape = dynamicBall; // ... its shape is the dynamic box (2x2 rectangle)
		fixtureDef.density = 1.0f; // ... its density is 1 (default is zero)
		fixtureDef.restitution = 1.0f;
		fixtureDef.friction = 0.0f; // ... its surface has some friction coefficient
		body.createFixture(fixtureDef); // bind the dense, friction-laden fixture to the body
//		body.setLinearVelocity(new Vec2(0.5f, 0.5f));
		body.applyForce(new Vec2(-100, -100), new Vec2());
		body.setLinearDamping(0.5f);
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
		// Set size.
		box.setAsBox(w, h);
		FixtureDef fixtureDef = new FixtureDef(); // fixture def that we load up with the following info:
		fixtureDef.shape = box; // ... its shape is the dynamic box
		fixtureDef.density = 20.0f; // ... its density is 1 (default is zero)
		fixtureDef.friction = 0.3f; // ... its surface has some friction coefficient
		fixtureDef.restitution = 0.0f; // ... set bouncy bouncy
		body.createFixture(fixtureDef); // bind the dense, friction-laden fixture to the body
		body.setFixedRotation(true);
		return body;

	}

	public Body addObject(GameItem item) {
		try {
			if (item.getType().equals("PADDLE")) {
				return addBox(item);
			} else if (item.getType().equals("BALL")) {
				return addBall(item);
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
