package pong.view;

import static pong.model.Const.*;

public class Camera {
	// Contains the position of the camera. (x,y,z)
	private static float[] position = new float[3];

	// Contains the point which the camera is looking at. (x,y,z)
	private static float[] lookPoint = new float[3];

	// Contains the Up vector for the camera. (This will probably never be changed)
	private static float[] upVector = new float[3];

	public Camera() {

		// Set standard startinglocation for the Camera
		position[0] = CAMERA_POSITION_X;
		position[1] = CAMERA_POSITION_Y;
		position[2] = CAMERA_POSITION_Z;

		// Set standard point to look at
		lookPoint[0] = CAMERA_LOOK_AT_X;
		lookPoint[1] = CAMERA_LOOK_AT_Y;
		lookPoint[2] = CAMERA_LOOK_AT_Z;

		// Set standard Up vector.
		upVector[0] = 0.0f;
		upVector[1] = 1.0f;
		upVector[2] = 0.0f;
	}

	/** Makes a smooth increment or decrement of zPos of the camera. */
	public static void smoothZoom(float zTarget) {
		float startPos = position[2];
		float dist;
		float distTraveled = 0;
		float acc = 0.00001f;
		float speed = 0;
		boolean moveInward;
		dist = Math.abs(startPos-zTarget);
		if (zTarget < startPos) {
			moveInward = true;
		} else {
			moveInward = false;
		}

		try {
			while (distTraveled <= dist) {
				
				// Increment or decrement zPos based on if you move inwards (zPos decreasing) or outwards (zPos increasing)
				if (moveInward) {
					position[2] -= speed;
					distTraveled = startPos - position[2];
				}else{
					position[2] += speed;
					distTraveled = position[2] - startPos;
				}
				
				// Accceleration (first 1/3 of the distance)
				if(distTraveled <= (dist / 3)) {
					speed = speed + acc;
				}
				// Constant speed (from 1/3 to 2/3 of the distance)
				else if(distTraveled <= 2 * (dist / 3)) {
					//Do nothing
				}
				
				// Deaccelerate speed (from 2/3 to the end)
				else{
					speed = speed - acc;

					//The camera has stopped. This is here to stop the camera when zooming out.
					if(speed <= 0)
						break;
				}
				Thread.sleep(1);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// The position is very(!) close to target but not exact.
		// Sets the position to be exactly the target Z.
		position[2] = zTarget;
	}

	public static float[] getPosition() {
		return position;
	}

	public float[] getLookPoint() {
		return lookPoint;
	}

}
