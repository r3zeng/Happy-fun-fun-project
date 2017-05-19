package opengl;

import org.lwjgl.util.vector.Vector3f;

public class Lighting {

	private Vector3f position;
	private Vector3f color;
	
	public Lighting( Vector3f Position, Vector3f Color ){
		position = Position;
		color = Color;
		
	}
	
	/**
	 * @return the position vector of the light source
	 */
	public Vector3f getPosition(){
		return position;
	}
	
	/**
	 * @return the coloring/intensity vector of a light source
	 */
	public Vector3f getColor(){
		return color;
	}
}
