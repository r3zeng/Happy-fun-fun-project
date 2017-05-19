package opengl;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

public class EntityRender {
	
	private Matrix4f projectionMatrix;
	private StaticShader shader;
	
	/**
	 * Default constructor
	 */
	public EntityRender(StaticShader Shader){
		
		/* saves the shader */
		shader = Shader;
		
		projectionMatrix = Helper.createProjectionMatrix();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	/**
	 * renders all entities in the world
	 * 
	 * @param entities: hashmap mapping textures to objects to use that
	 * texture
	 */
	public void render(Map<Model,List<Entity>> entities){
		
		/* loads all textures */
		for(Model Tm:entities.keySet()){
			prepareModel(Tm);
			/* get all entities that use the textured model */
			List<Entity> batch = entities.get(Tm);
			/* render all entities in batch */
			for(Entity entity:batch){
				prepareInstance(entity);
				
				/* renders the entity */
				GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getVertexCount(),
						GL11.GL_UNSIGNED_INT, 0);
			}
			/* unbinds texture after all entities that use the
			 * texture have been rendered */
			unbindModel();
		}
	}
	
	/**
	 * helper method for render.  binds the VAO's for a specified texturedModel
	 * and load in uniform value
	 * 
	 * @param Tm: textured model being used
	 */
	private void prepareModel(Model model){
		
		/* bind model */
		GL30.glBindVertexArray(model.getVAO_ID());
		/*enable attributes */
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
	}
	
	/**
	 * helper method for render. UNbind the VAO 
	 */
	private void unbindModel(){
		/* disable attribute list */
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		/* unbinds VAO */
		GL30.glBindVertexArray(0);
	}
	
	/**
	 * helper method for render.  Applies the transformation matrix to each entity
	 * 
	 * @param entity: entity being rendered
	 */
	private void prepareInstance(Entity entity){
		/* stores transformation of entity into a uniform variable */
		Matrix4f transformationMatrix = Helper.createTransformationMatrix(entity.getPostion(), entity.getRotationX(), 
				entity.getRotationY(), entity.getRotationZ(), entity.getScale());
		shader.loadTransformationMatrix(transformationMatrix);


	}
	
	/**
	 * renders an normal model
	 * 
	 * @param model: model to render
	 */
	public void render(Model model){
		/* bind model */
		GL30.glBindVertexArray(model.getVAO_ID());
		/*enable attribute list 0 */
		GL20.glEnableVertexAttribArray(0);
		/* tells openGL to render */
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(),
				GL11.GL_UNSIGNED_INT, 0);
		/* disable attribute list */
		GL20.glDisableVertexAttribArray(0);
		/* unbinds VAO */
		GL30.glBindVertexArray(0);
		
	}
}
