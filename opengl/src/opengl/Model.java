package opengl;

public class Model {
	
	/* ID for VAO used by opengl*/
	private int VAO_ID;
	/* total number of vertexes */
	private int vertexCount;
	
	private Mesh mesh;
	
	/** 
	 * updates the model's VBO to
	 * 
	 */
	public void updateModelFromMesh(){
		
	}
	
	/**
	 * Default constructor
	 * 
	 * @param VAOID: VAO Id used by opengl
	 * @param VertexCount: total number of vertices
	 */
	public Model(int VAOID, int VertexCount){
		VAO_ID = VAOID;
		vertexCount = VertexCount;
		mesh = null;
	}
	
	/**
	 * Default constructor
	 * 
	 * @param VAOID: VAO Id used by opengl
	 * @param VertexCount: total number of vertices
	 */
	public Model(int VAOID, int VertexCount, Mesh m){
		VAO_ID = VAOID;
		vertexCount = VertexCount;
		mesh = m;
	}
	
	/**
	 * @return vertex count of the model
	 */
	public int getVertexCount(){
		return vertexCount;
	}
	
	/**
	 * @return VAO_ID of model
	 */
	public int getVAO_ID(){
		return VAO_ID;
	}
}
