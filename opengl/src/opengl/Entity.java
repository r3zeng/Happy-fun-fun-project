package opengl;

import org.lwjgl.util.vector.Vector3f;

public class Entity {
	
	private TexturedModel model;
	private Vector3f position;
	private float rx;
	private float ry;
	private float rz;
	private float scale;
	
	public void Scale(float s){
		scale = s * scale;
	}
	
	public Entity(TexturedModel Model, Vector3f Position, float RX,
			float RY, float RZ, float Scale){
		model = Model;
		position = Position;
		rx = RX;
		ry = RY;
		rz = RZ;
		scale = Scale;
	}
	
	/**
	 * translates the position of an object
	 * 
	 * @param dx: amount of x translation
	 * @param dy: amount of y translation
	 * @param dz: amount of z translation
	 */
	public void translate(float dx, float dy, float dz){
		position.x += dx;
		position.y += dy;
		position.z += dz;
	}
	
	/**
	 * rotate the object by a specified amount.  Note: in degrees
	 * 
	 * @param dx: amount of rotation on x axis
	 * @param dy: amount of rotation on y axis
	 * @param dz: amount of rotation on z axis
	 */
	public void rotate(float dx, float dy, float dz){
		rx += dx;
		ry += dy;
		rz += dz;
	}
	
	/**
	 * @return: the textured model of the entity
	 */
	public TexturedModel getTexturedModel(){
		return model;
	}
	
	/**
	 * @return: position vector of the entity
	 */
	public Vector3f getPostion(){
		return position;
	}
	
	/**
	 * @return: the x rotation of the entity
	 */
	public float getRotationX(){
		return rx;
	}
	
	/**
	 * @return: the y rotation of the entity
	 */
	public float getRotationY(){
		return ry;
	}
	
	/**
	 * @return: the z rotation of the entity
	 */
	public float getRotationZ(){
		return rz;
	}
	
	/**
	 * @return: the scale of the entity 
	 */
	public float getScale(){
		return scale;
	}
}
