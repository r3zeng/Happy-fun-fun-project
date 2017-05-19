package opengl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public abstract class Shader {
	
	private int program_ID;
	private int vertexShader_ID;
	private int fragmentShader_ID;
	
	private static FloatBuffer matrix = BufferUtils.createFloatBuffer(16);
	
	/**
	 * shader constructors
	 * 
	 * @param vertexShaderFile: string for path to vertex shader
	 * @param fragmentShaderFile: string for path to fragment shader
	 */
	public Shader(String vertexShaderFile, String fragmentShaderFile){
		/* creates shaders */
		vertexShader_ID = loadShader(vertexShaderFile, GL20.GL_VERTEX_SHADER);
		fragmentShader_ID = loadShader(fragmentShaderFile, GL20.GL_FRAGMENT_SHADER);
		
		/* creates program */
		program_ID = GL20.glCreateProgram();
		/* attaches shaders */
		GL20.glAttachShader(program_ID, vertexShader_ID);
		GL20.glAttachShader(program_ID, fragmentShader_ID);
		
		bindAttributes();
		
		/* link program */
		GL20.glLinkProgram(program_ID);
		GL20.glValidateProgram(program_ID);
		getAllUniformLocations();

		
	}
	
	/**
	 * finds the location of an uniform variable
	 * 
	 * @param name: name of the uniform variable
	 * @return integer representing the location of the
	 * uniform variable
	 */
	protected int getUniformLocation(String name){
		return GL20.glGetUniformLocation(program_ID, name);
		
	}
	
	/**
	 * store a float value into an uniform variable
	 * 
	 * @param location: location of the uniform variable
	 * @param value: value to store in uniform variable
	 */
	protected void loadFloat(int location, float value){
		GL20.glUniform1f(location, value);
	}
	
	/**
	 * store a vector value into an uniform variable
	 * 
	 * @param location: location of the uniform variable
	 * @param value: value to store in uniform variable
	 */
	protected void loadVector(int location, Vector3f vector){
		GL20.glUniform3f(location, vector.x, vector.y,
				vector.z);
	}
	
	/**
	 * store a Boolean value into an uniform variable
	 * 
	 * @param location: location of the uniform variable
	 * @param value: value to store in uniform variable
	 */
	protected void loadBoolean(int location, boolean value){
		if(value){
			GL20.glUniform1f(location, 1);
		}
		else{
			GL20.glUniform1f(location, 0);
		}
	}
	
	protected void loadMatrix(int location, Matrix4f Matrix){
		Matrix.store(matrix); 
		/* flip to read mode */
		matrix.flip();
		GL20.glUniformMatrix4(location, false, matrix);
		
	}
	/**
	 * gets the location of all uniform variables
	 */
	protected abstract void getAllUniformLocations();
	
	/**
	 * starts program
	 */
	public void start(){
		GL20.glUseProgram(program_ID);
	}
	
	/**
	 * stop program
	 */
	public void stop(){
		GL20.glUseProgram(0);
	}
	
	/**
	 * deletes shaders 
	 */
	public void delete(){
		stop();
		GL20.glDetachShader(program_ID, vertexShader_ID);
		GL20.glDetachShader(program_ID, fragmentShader_ID);
		GL20.glDeleteShader(vertexShader_ID);
		GL20.glDeleteShader(fragmentShader_ID);
		GL20.glDeleteProgram(program_ID);
	}
	
	/**
	 * binds attributes to a shader
	 */
	protected abstract void bindAttributes();
	
	protected void bindAttribute(int attribute, String varibleName){
		GL20.glBindAttribLocation(program_ID, attribute, varibleName);
	}
	
	/**
	 * used to load in shaders
	 * 
	 * @param file: file containing the shader
	 * @param type: type of the shader 
	 * @return A shader ID
	 */
	private static int loadShader(String file,
			int type) {
		StringBuilder source = new StringBuilder();
		
		/* read in the entire file */
		try{
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			
			while((line = reader.readLine()) != null){
				source.append(line).append("\n");
			}
		}catch(Exception e){
			System.err.println("Error reading shader file: " + file);
		}
		
		/* creates the shader */
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, source);
		GL20.glCompileShader(shaderID);
		
		/* checks if shader complied successfully*/
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS )
				== GL11.GL_FALSE){
			/* Shader compile error */
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader: " + file);
			System.exit(-1);		
		}
		
		return shaderID;
	}
}
