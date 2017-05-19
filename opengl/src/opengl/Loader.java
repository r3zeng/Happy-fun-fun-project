package opengl;

import java.io.FileInputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Loader {
	
	private List<Integer> VAOs = new ArrayList<Integer>();
	private List<Integer> VBOs = new ArrayList<Integer>();
	private List<Integer> TEXTURES = new ArrayList<Integer>();
	
	public Model loadToVAO(float[] positions, float[] textureCoords
			, float[] normals, int[] indices, Mesh mesh){
			
		
		/* creates ID */
		int VAO_ID = createVAO();
		
		/* bind Indices VBO */
		bindIndicesBuffer(indices);
		
		/* stores positional data in attribute 0*/
		storeDataInAttributeList(0, 3 ,positions);
		/* stores texture coordinates */
		storeDataInAttributeList(1, 2 ,textureCoords);
		/* store normal vectors */
		storeDataInAttributeList(2, 3 , normals);
		
		/* Unbind VAO */
		unbindVAO();
		
		/* returns data as a model */ 
		return new Model(VAO_ID, indices.length, mesh);
	}
	
	/**
	 * loads a texture and returns a texture_ID
	 * 	  
	 * @param filName: file containing the texture
	 * @param type: type of texture loaded
	 * @return ID to the loaded texture
	 */
	public int loadTexture(String fileName){
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", 
					new FileInputStream("res/" + fileName + ".png"));
		} catch (Exception e) {
			System.err.println("Error loading Texture: " +
					fileName);
		}
		int texture_ID = texture.getTextureID();
		TEXTURES.add(texture_ID);
		return texture_ID;
	}
	
	/**
	 * creates a VAO object
	 * 
	 * @return Id of the recently created Vertex Array
	 */
	private int createVAO() {
	
		int VAO_ID = GL30.glGenVertexArrays();
		VAOs.add(VAO_ID);
		GL30.glBindVertexArray(VAO_ID);
		
		return VAO_ID;
	}
	
	/**
	 * stores VBO data into a VAO
	 * 
	 * @param AttributeNumber: attribute number to store VBO into
	 * @param data: VBO data
	 */
	private void storeDataInAttributeList(int AttributeNumber, int DataSize, float[]
			data){
		int VBOID = GL15.glGenBuffers();
		VBOs.add(VBOID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VBOID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		
		/* specify type and usage of data */
		GL15.glBufferData( GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		/* add VBO into VAO */
		GL20.glVertexAttribPointer(AttributeNumber, DataSize,
				GL11.GL_FLOAT, false, 0, 0);
		/* unbinds buffer */
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * unbinds the current Vertex array
	 */
	private void unbindVAO(){
		GL30.glBindVertexArray(0);
	}
	
	/**
	 * stores data as a FloatBuffer in a VBO.  Converts a float array into 
	 * float buffer
	 * 
	 * @param data: float array to convert into FloatBuffer
	 */
	private FloatBuffer storeDataInFloatBuffer( float[] data ){
		
		/* creates the buffer */
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		/* adds data to the buffer */
		buffer.put(data);
		/* flips buffer from write to read mode */
		buffer.flip();
		return buffer;
		
	}
	
	/** 
	 * creates VBO for the indices of the vertex to be rendered.  The binds
	 * it to the VAO
	 * @param indices: an array of integers correspoing to numbering of 
	 * vertices
	 */
	private void bindIndicesBuffer(int[] indices){
		int VBO_ID = GL15.glGenBuffers();
		VBOs.add(VBO_ID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, VBO_ID);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer,
				GL15.GL_STATIC_DRAW);
		
	}
	
	/**
	 * stores data as a IntBuffer in a VBO.  Converts an int array into 
	 * int buffer
	 * 
	 * @param data: int array to convert into FloatBuffer
	 */
	private IntBuffer storeDataInIntBuffer(int[] data){
		
		/* creates a buffer */
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		/* adds data to buffer */
		buffer.put(data);
		/* switch from write to read mode*/
		buffer.flip();
		
		return buffer;
	}
	
	/**
	 * removes all creates VAO and VBO data
	 */
	public void cleanUp(){
		/* delete VAOs */
		for(int i = 0; i < VAOs.size(); i++){
			GL30.glDeleteVertexArrays(VAOs.get(i));
		}
		/* delete VBOs */
		for(int i = 0; i < VBOs.size(); i++){
			GL15.glDeleteBuffers(VBOs.get(i));
		}
		/* delete textures */
		for(int i = 0; i < TEXTURES.size(); i++){
			GL11.glDeleteTextures(TEXTURES.get(i));
		}
	}
}
