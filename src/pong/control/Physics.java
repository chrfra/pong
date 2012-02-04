package pong.control;

import java.util.ArrayList;

import javax.swing.JFrame;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.shapes.PolygonShape;
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

public class Physics {
	public int targetFPS = 40;
//	public int timeStep = (1000 / targetFPS);
	public int iterations = 5;

	private ArrayList<Body> bodies;
	private int count = 0;

	private AABB worldAABB;
	private World world;
	private BodyDef groundBodyDef;
//	private PolygonDef groundShapeDef;
	
	private Vec2 position;
    private float angle;
    Body body;

	public void create() {
		Vec2 gravity = new Vec2(0.0f, -10.0f);
	    boolean doSleep = true;
	    world = new World(gravity, true);
	    world.setGravity(gravity);

	    // Make a Body for the ground via definition and shape binding that gives it a boundary
	    BodyDef groundBodyDef = new BodyDef(); // body definition
	    groundBodyDef.position.set(0.0f, -10.0f); // set bodydef position
	    Body groundBody = world.createBody(groundBodyDef); // create body based on definition
	    PolygonShape groundBox = new PolygonShape(); // make a shape representing ground
	    groundBox.setAsBox(500.0f, 100.0f); // shape is a rect: 100 wide, 20 high
	    groundBody.createFixture(groundBox, 0.0f); // bind shape to ground body
	    
	    
	    
	    //Testbed
	    TestbedModel model = new TestbedModel();         // create our model

	    // add tests
	    TestList.populateModel(model);                   // populate the provided testbed tests
	    model.addCategory("My Super Tests");             // add a category
	    model.addTest(new PhysTest());                // add our test

	    // add our custom setting "My Range Setting", with a default value of 10, between 0 and 20
	    model.getSettings().addSetting(new TestbedSetting("My Range Setting", SettingType.ENGINE, 10, 0, 20));

	    TestbedPanel panel = new TestPanelJ2D(model);    // create our testbed panel

	    JFrame testbed = new TestbedFrame(model, panel); // put both into our testbed frame
	    // etc
	    testbed.setVisible(true);
	    testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void addBall() {
		// Make another Body that is dynamic, and will be subject to forces.
	    //
	    BodyDef bodyDef = new BodyDef();
	    bodyDef.type = BodyType.DYNAMIC; // dynamic means it is subject to forces
	    bodyDef.position.set(0.0f, 4.0f);
	    body = world.createBody(bodyDef);
	    PolygonShape dynamicBox = new PolygonShape();
	    dynamicBox.setAsBox(1.0f, 1.0f);
	    FixtureDef fixtureDef = new FixtureDef(); // fixture def that we load up with the following info:
	    fixtureDef.shape = dynamicBox; // ... its shape is the dynamic box (2x2 rectangle)
	    fixtureDef.density = 1.0f; // ... its density is 1 (default is zero)
	    fixtureDef.friction = 0.3f; // ... its surface has some friction coefficient
	    body.createFixture(fixtureDef); // bind the dense, friction-laden fixture to the body
	}

	public void update() {
	    // Simulate the world
	    //
	    float timeStep = 1.0f / 60.f;
	    int velocityIterations = 6;
	    int positionIterations = 2;
//	    for (int i = 0; i < 60; ++i) {
	      world.step(timeStep, velocityIterations, positionIterations);

	      Vec2 position = body.getPosition();
	      float angle = body.getAngle();
	      System.out.printf("%4.2f %4.2f %4.2f\n", position.x, position.y, angle);
//	    }

	    System.out.println("Done.");
    }
}
