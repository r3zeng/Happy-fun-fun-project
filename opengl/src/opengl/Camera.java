package opengl;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	private Vector3f position = new Vector3f(0, 0, 0);
	
	/* up, down angle */
	private float pitch;
	
	/* left, right angle */
	private float yaw;
	
	/* rotation */
	private float roll;
	
	
	private int m = 1;
	public Camera(){
		
	}
	
	/**
	 * moves the camera if WASD keys are pressed
	 */
	public void move(){
		if(Keyboard.isKeyDown(Keyboard.KEY_G)){
			m++;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			position.z -= 0.05f * m * Math.cos(Math.toRadians(yaw));
			position.x += 0.05f * m * Math.sin(Math.toRadians(yaw));
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			position.z += 0.05f * m * Math.cos(Math.toRadians(yaw));
			position.x -= 0.05f * m * Math.sin(Math.toRadians(yaw));
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			position.x += 0.05f * m * Math.cos(Math.toRadians(yaw));
			position.z += 0.05f * m * Math.sin(Math.toRadians(yaw));
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			position.x -= 0.05f * m * Math.cos(Math.toRadians(yaw));
			position.z -= 0.05f * m * Math.sin(Math.toRadians(yaw));
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
			position.y += m * 0.05f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			position.y -= m * 0.05f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			yaw += 1f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			yaw -= 1f;
		}
	}
	
	/**
	 * @return: position of the camera
	 */
	public Vector3f getPosition(){
		return position;
	}
	
	/**
	 * @return: pitch of the Camera
	 */
	public float getPitch(){
		return pitch;
	}
	
	/**
	 * @return: yaw of the Camera
	 */
	public float getYaw(){
		return yaw;
	}
	
	/**
	 * @return: roll of the Camera
	 */
	public float getRoll(){
		return roll;
	}
}
