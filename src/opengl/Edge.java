package opengl;

import org.lwjgl.util.vector.Vector3f;

public class Edge {
	
	private Vector3f vertex1;
	private Vector3f vertex2;
	
	public Edge(Vector3f v1, Vector3f v2){
		
		vertex1 = v1;
		vertex2 = v2;
	}
	
	public float getDistance(){
		Vector3f diff = Vector3f.sub(vertex1, vertex2, null);
		return (float) Math.sqrt((diff.x * diff.x) + (diff.y * diff.y) + (diff.z * diff.z));
	}
}
