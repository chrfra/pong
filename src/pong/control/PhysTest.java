package pong.control;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.testbed.framework.TestbedTest;

public class PhysTest extends TestbedTest {

	@Override
	public String getTestName() {
		return "Testing Physics";
	}

	@Override
	public void initTest(boolean arg0) {
		setTitle("Couple of Things Test");
		Vec2 gravity = new Vec2(0f, -10.0f);
		// Vec2 gravity = new Vec2()
		// getWorld().setGravity(new Vec2());
		getWorld().setGravity(gravity);

		// for (int i = 0; i < 2; i++) {

		BodyDef bd = new BodyDef();
		Body ground = getWorld().createBody(bd);

		float x1 = -20.0f;
		float y1 = 2.0f * MathUtils.cos(x1 / 10.0f * MathUtils.PI);
		for (int i = 0; i < 80; ++i) {
			float x2 = x1 + 0.5f;
			float y2 = 2.0f * MathUtils.cos(x2 / 10.0f * MathUtils.PI);

			PolygonShape shape = new PolygonShape();
			shape.setAsEdge(new Vec2(x1, y1), new Vec2(x2, y2));
			ground.createFixture(shape, 0.0f);

			x1 = x2;
			y1 = y2;
		}

		// Draw square
		PolygonShape shape = new PolygonShape();
		shape.setAsEdge(new Vec2(-30, -20), new Vec2(30, -20)); // Floor
		ground.createFixture(shape, 0.0f);
		shape.setAsEdge(new Vec2(-30, 20), new Vec2(30, 20)); // Roof
		ground.createFixture(shape, 0.0f);
		shape.setAsEdge(new Vec2(-30, -20), new Vec2(-30, 20)); // Left
		ground.createFixture(shape, 0.0f);
		shape.setAsEdge(new Vec2(30, -20), new Vec2(30, 20)); // right
		ground.createFixture(shape, 0.0f);

		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(1, 1);

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position.set(5, 0);
		bodyDef.angle = (float) (Math.PI / 4);
		bodyDef.allowSleep = false;
		Body body = getWorld().createBody(bodyDef);
		body.createFixture(polygonShape, 5.0f);

		body.applyForce(new Vec2(-10000, 0), new Vec2());

		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC; // dynamic means it is subject to forces
		bodyDef.position.set(1.0f, 10.0f);
		body = getWorld().createBody(bodyDef);
		body.setLinearVelocity(new Vec2(0.0f, -10.0f));
		body.setFixedRotation(true);
		PolygonShape dynamicBox = new PolygonShape();
		dynamicBox.setAsBox(1.0f, 1.0f);
		FixtureDef fixtureDef = new FixtureDef(); // fixture def that we load up with the following info:
		fixtureDef.shape = dynamicBox; // ... its shape is the dynamic box (2x2 rectangle)
		fixtureDef.density = 1.0f; // ... its density is 1 (default is zero)
		fixtureDef.friction = 0.0f; // ... its surface has some friction coefficient
		fixtureDef.restitution = 1.0f;
		body.createFixture(fixtureDef); // bind the dense, friction-laden fixture to the body
		// }

	}

}
