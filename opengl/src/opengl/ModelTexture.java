package opengl;

public class ModelTexture {
	
	private int texture_ID; 
	private float shineDamper;
	private float reflectivity;
	
	public ModelTexture(int Texture_ID, float ShineDamper, float Reflectivity){
		texture_ID = Texture_ID;
		shineDamper = ShineDamper;
		reflectivity = Reflectivity;
	}
	
	/**
	 * @return reflectivity value of the texture
	 */
	public float getReflectivity(){
		return reflectivity;
	}
	
	/**
	 * @return shine Damper value of the texture
	 */
	public float getShineDamper(){
		return shineDamper;
	}
	
	/**
	 * @return ID of the texture
	 */
	public int getID(){
		return texture_ID;
	}
}
