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
	private int UNIFORM_LightingPosition1;
	private int UNIFORM_LightingPosition2;
	private int UNIFORM_LightingPosition3;
	private int UNIFORM_LightingPosition4;
	private int UNIFORM_LightingPosition5;
	private int UNIFORM_LightingPosition6;
	private int UNIFORM_LightingPosition7;
	private int UNIFORM_LightingPosition8;
	
	/* location of the lighting color Uniform */
	private int UNIFORM_LightingColor1;
	private int UNIFORM_LightingColor2;
	private int UNIFORM_LightingColor3;
	private int UNIFORM_LightingColor4;
	private int UNIFORM_LightingColor5;
	private int UNIFORM_LightingColor6;
	private int UNIFORM_LightingColor7;
	private int UNIFORM_LightingColor8;

	public StaticShader(){
		super(VERTEX_SHADER_FILE, FRAGMENT_SHADER_FILE);
	}

	@Override
	protected void bindAttributes() {
		/* bind attribute 0 to position in vertex shader */
		super.bindAttribute(0, "position");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocations() {
		UNIFORM_Transformation = 
				super.getUniformLocation("transformationMatrix");
		UNIFORM_Projection = 
				super.getUniformLocation("projectionMatrix");
		UNIFORM_View =
				super.getUniformLocation("viewMatrix");
		UNIFORM_LightingPosition1 =
				super.getUniformLocation("lightPosition1");
		UNIFORM_LightingPosition2 =
				super.getUniformLocation("lightPosition2");
		UNIFORM_LightingPosition3 =
				super.getUniformLocation("lightPosition3");
		UNIFORM_LightingPosition4 =
				super.getUniformLocation("lightPosition4");
		UNIFORM_LightingPosition5 =
				super.getUniformLocation("lightPosition5");
		UNIFORM_LightingPosition6 =
				super.getUniformLocation("lightPosition6");
		UNIFORM_LightingPosition7 =
				super.getUniformLocation("lightPosition7");
		UNIFORM_LightingPosition8 =
				super.getUniformLocation("lightPosition8");
		UNIFORM_LightingColor1 =
				super.getUniformLocation("lightColor1");
		UNIFORM_LightingColor2 =
				super.getUniformLocation("lightColor2");
		UNIFORM_LightingColor3 =
				super.getUniformLocation("lightColor3");
		UNIFORM_LightingColor4 =
				super.getUniformLocation("lightColor4");
		UNIFORM_LightingColor5 =
				super.getUniformLocation("lightColor5");
		UNIFORM_LightingColor6 =
				super.getUniformLocation("lightColor6");
		UNIFORM_LightingColor7 =
				super.getUniformLocation("lightColor7");
		UNIFORM_LightingColor8 =
				super.getUniformLocation("lightColor8");
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
	public void loadLightingPosition(Lighting[] light){
		super.loadVector(UNIFORM_LightingPosition1, light[0].getPosition());
		super.loadVector(UNIFORM_LightingColor1, light[0].getColor());
		super.loadVector(UNIFORM_LightingPosition2, light[1].getPosition());
		super.loadVector(UNIFORM_LightingColor2, light[1].getColor());
		super.loadVector(UNIFORM_LightingPosition3, light[2].getPosition());
		super.loadVector(UNIFORM_LightingColor3, light[2].getColor());
		super.loadVector(UNIFORM_LightingPosition4, light[3].getPosition());
		super.loadVector(UNIFORM_LightingColor4, light[3].getColor());
		super.loadVector(UNIFORM_LightingPosition5, light[4].getPosition());
		super.loadVector(UNIFORM_LightingColor5, light[4].getColor());
		super.loadVector(UNIFORM_LightingPosition6, light[5].getPosition());
		super.loadVector(UNIFORM_LightingColor6, light[5].getColor());
		super.loadVector(UNIFORM_LightingPosition7, light[6].getPosition());
		super.loadVector(UNIFORM_LightingColor7, light[6].getColor());
		super.loadVector(UNIFORM_LightingPosition8, light[7].getPosition());
		super.loadVector(UNIFORM_LightingColor8, light[7].getColor());
	}
	

	
}
