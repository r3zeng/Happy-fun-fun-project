package opengl;

import org.lwjgl.util.vector.Vector3f;

public class Face{
	
	private int vertex1_index;
	private int vertex2_index;
	private int vertex3_index;
	private Vector3f Surface_Normal;
	
	public Face( int v1, int v2, int v3, Vector3f sfn){
		vertex1_index = v1;
		vertex2_index = v2;
		vertex3_index = v3;
		Surface_Normal = sfn;
	}
	
	/**
	 * determines if this face is adjacent (shares an edge with another)
	 * 
	 * @param other
	 * @return true or false
	 */
	public boolean AdjacentFace(Face other){
		return sharedVertice(other) == 2;
	}
	
	private int sharedVertice(Face other){
		int sharedVerts = 0;
		
		if(vertex1_index == other.getVertex1() || vertex1_index == other.getVertex2()
				|| vertex1_index == other.getVertex3()){
			sharedVerts++;
		}
		if(vertex2_index == other.getVertex1() || vertex2_index == other.getVertex2()
				|| vertex2_index == other.getVertex3()){
			sharedVerts++;
		}
		if(vertex3_index == other.getVertex1() || vertex3_index == other.getVertex2()
				|| vertex3_index == other.getVertex3()){
			sharedVerts++;
		}
		
		return sharedVerts;
	}
	
	public int getVertex1(){
		return vertex1_index;
	}
	
	public int getVertex2(){
		return vertex2_index;
	}
	
	public int getVertex3(){
		return vertex3_index;
	}
	
	public Vector3f getSurfaceNormal(){
		return Surface_Normal;
	}

	
}