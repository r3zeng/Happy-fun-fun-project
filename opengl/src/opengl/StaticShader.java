package opengl;

import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends Shader{

	private static final String VERTEX_SHADER_FILE = "src/shaders/StaticVertexShader.txt";
	private static final String FRAGMENT_SHADER_FILE = "src/shaders/StaticFragmentShader.txt";
	
	/* location of the Transformation Uniform */
	private int UNIFORM_Transformation;
	/* location of the projection Uniform */
	private int UNIFORM_Projection;
	/* location of the view Uniform */
	private int UNIFORM_View;
	/* location of the lighting position Uniform */
	private int UNIFORM_LightingPosition;
	/* location of the lighting color Uniform */
	private int UNIFORM_LightingColor;
	/* location of the shine Damper Uniform */
	private int UNIFORM_ShineDamper;
	/* location of the reflectivity Uniform */
	private int UNIFORM_Reflectivity;
	
	public StaticShader(){
		super(VERTEX_SHADER_FILE, FRAGMENT_SHADER_FILE);
	}

	@Override
	protected void bindAttributes() {
		/* bind attribute 0 to position in vertex shader */
		super.bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocations() {
		UNIFORM_Transformation = 
				super.getUniformLocation("transformationMatrix");
		UNIFORM_Projection = 
				super.getUniformLocation("projectionMatrix");
		UNIFORM_View =
				super.getUniformLocation("viewMatrix");
		UNIFORM_LightingPosition =
				super.getUniformLocation("lightPosition");
		UNIFORM_LightingColor =
				super.getUniformLocation("lightColor");
		UNIFORM_ShineDamper =
				super.getUniformLocation("shineDamper");
		UNIFORM_Reflectivity =
				super.getUniformLocation("reflectivity");
	}
	
	/**
	 * loads the reflectivity and shine damper value of a texture into
	 * unidorm variables 
	 * @param Mt: modelTexture being used
	 */
	public void loadShineAndReflectivity(ModelTexture Mt){
		super.loadFloat(UNIFORM_ShineDamper, Mt.getShineDamper());
		super.loadFloat(UNIFORM_Reflectivity, Mt.getReflectivity());
	}

	/**
	 * store a matrix into the Uniform transformation matrix
	 * 
	 * @param matrix: matrix being stored
	 */
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(UNIFORM_Transformation, matrix);
	}
	
	/**
	 * store a matrix into the Uniform projection matrix
	 * 
	 * @param matrix: matrix being stored
	 */
	public void loadProjectionMatrix(Matrix4f projection){
		super.loadMatrix(UNIFORM_Projection, projection);
	}
	
	/**
	 * stores a matrix into the Uniform view matrix
	 * 
	 * @param camera: matrix being stored
	 */
	public void loadViewMatrix(Camera camera){
		super.loadMatrix(UNIFORM_View, Helper.createViewMatrix(camera));
	}
	
	/**
	 * stores the lighting object's position and color into Uniforms
	 * 
	 * @param light: the lighting object
	 */
	public void loadLightingPosition(Lighting light){
		super.loadVector(UNIFORM_LightingPosition, light.getPosition());
		super.loadVector(UNIFORM_LightingColor, light.getColor());
	}
	

	
}
