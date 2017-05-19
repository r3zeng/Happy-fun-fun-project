package opengl;

public class TexturedModel {
	private Model model;
	private ModelTexture texture;
	
	public TexturedModel(Model Model, ModelTexture Texture){
		model = Model;
		texture = Texture;
	}
	
	/**
	 * @return Model of object
	 */
	public Model getModel(){
		return model;
	}
	
	/**
	 * @return Texture of object
	 */
	public ModelTexture getTexture(){
		return texture;
	}
}
